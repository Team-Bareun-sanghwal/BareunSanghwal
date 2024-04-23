package life.bareun.diary.habit.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import life.bareun.diary.habit.dto.HabitTrackerCreateDto;
import life.bareun.diary.habit.dto.HabitTrackerLastDto;
import life.bareun.diary.habit.dto.MemberHabitDto;
import life.bareun.diary.habit.dto.request.HabitCreateReqDto;
import life.bareun.diary.habit.dto.request.HabitDeleteReqDto;
import life.bareun.diary.habit.dto.response.MemberHabitResDto;
import life.bareun.diary.habit.entity.Habit;
import life.bareun.diary.habit.entity.HabitTracker;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.habit.entity.embed.MaintainWay;
import life.bareun.diary.habit.exception.HabitErrorCode;
import life.bareun.diary.habit.exception.HabitException;
import life.bareun.diary.habit.repository.HabitRepository;
import life.bareun.diary.habit.repository.MemberHabitRepository;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;

    private final MemberHabitRepository memberHabitRepository;

    private final MemberRepository memberRepository;

    private final HabitTrackerService habitTrackerService;

    @Override
    // 사용자가 해빗을 생성
    public void createMemberHabit(HabitCreateReqDto habitCreateReqDto) {
        // 멤버, 해빗 가져오기
        Habit habit = habitRepository.findById(habitCreateReqDto.habitId())
            .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_HABIT));
        Member member = memberRepository.findById(1L)
            .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_MEMBER));
        // 달의 마지막 연, 월, 일 가져오기
        LocalDate lastDay = YearMonth.of(LocalDate.now().getYear(), LocalDate.now().getMonth())
            .atEndOfMonth();
        // 만약 말일이라면 아무것도 안함
        // 말일이 아니라면 말일까지 증가시키면서 맞는 조건에만 생성
        if (habitCreateReqDto.dayOfWeek() == null) {
            MemberHabit memberHabit = memberHabitRepository.save(
                MemberHabit.builder().member(member).habit(habit).alias(habitCreateReqDto.alias())
                    .icon(habitCreateReqDto.icon()).isDeleted(false).maintainWay(
                        MaintainWay.PERIOD).maintainAmount(habitCreateReqDto.period()).build());
            // 주기 방식으로 트래커 목록 생성
            LocalDate startDay = LocalDate.now().plusDays(habitCreateReqDto.period());
            createHabitTrackerByPeriod(startDay, lastDay, member, memberHabit,
                habitCreateReqDto.period());
        } else {
            MemberHabit memberHabit = memberHabitRepository.save(
                MemberHabit.builder().member(member).habit(habit).alias(habitCreateReqDto.alias())
                    .icon(habitCreateReqDto.icon()).isDeleted(false).maintainWay(
                        MaintainWay.DAY).maintainAmount(habitCreateReqDto.dayOfWeek()).build());

            // 요일 방식으로 트래커 목록 생성
            long plusDay = 1L;
            while (LocalDate.now().plusDays(plusDay).getDayOfWeek().getValue()
                != habitCreateReqDto.dayOfWeek()) {
                plusDay++;
            }

            LocalDate startDay = LocalDate.now().plusDays(plusDay);
            // 일주일씩 증가시키며 생성
            createHabitTrackerByDay(startDay, lastDay, member, memberHabit,
                habitCreateReqDto.dayOfWeek());
        }
    }

    @Override
    // 사용자 해빗을 삭제
    public void deleteMemberHabit(HabitDeleteReqDto habitDeleteReqDto) {
        // 만약 모두 삭제한다고 하면 이전 기록들까지 전부 삭제
        if (Boolean.TRUE.equals(habitDeleteReqDto.isDeleteAll())) {
            habitTrackerService.deleteAllHabitTracker(habitDeleteReqDto.memberHabitId());
            memberHabitRepository.deleteById(habitDeleteReqDto.memberHabitId());
            // 아니라면 이전 것들은 유지
        } else {
            habitTrackerService.deleteAfterHabitTracker(habitDeleteReqDto.memberHabitId());
            memberHabitRepository.modifyStatus(habitDeleteReqDto.memberHabitId());
        }
    }

    @Override
    // 이번 달에 한 번이라도 유지한 적이 있는 사용자 해빗 가져오기
    public MemberHabitResDto findAllMonthMemberHabit(String monthValue) {
        // security logic이 완성되면 변경
        Member member = memberRepository.findById(1L)
            .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_MEMBER));
        YearMonth yearMonth = YearMonth.of(Integer.parseInt(monthValue.substring(0, 4)),
            Integer.parseInt(monthValue.substring(5)));
        LocalDateTime startDateTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDateTime = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        List<MemberHabit> memberHabitList = memberHabitRepository
            .findAllByMemberAndCreatedDatetimeBetween(member, startDateTime, endDateTime);

        List<MemberHabitDto> memberHabitDtoList = new ArrayList<>();
        for (MemberHabit memberHabit : memberHabitList) {
            if (Boolean.TRUE.equals(habitTrackerService
                .existsByMemberHabitAndSucceededTimeIsNotNull(memberHabit))) {
                memberHabitDtoList.add(
                    MemberHabitDto.builder().memberHabitId(memberHabit.getId())
                        .alias(memberHabit.getAlias()).icon(memberHabit.getIcon()).build());
            }
        }
        return MemberHabitResDto.builder().memberHabitDtoList(memberHabitDtoList).build();
    }

    @Override
    // 활성화된 해빗 유지하기
    public void connectHabitList() {
        // 이번 달의 마지막 날
        LocalDate nowMonth = LocalDate.now();
        int lastDayOfNowMonth = nowMonth.lengthOfMonth();

        // 다음 달 첫 일
        LocalDate nextMonth = nowMonth.plusMonths(1L).withDayOfMonth(1);

        // 다음 달의 마지막 일
        LocalDate lastDay = YearMonth.of(nextMonth.getYear(), nextMonth.getMonth()).atEndOfMonth();

        // 이번 달에 유지중인 memberHabit을 모두 가져오기
        List<MemberHabit> memberHabitList = memberHabitRepository.findAllByIsDeleted(false);
        for (MemberHabit memberHabit : memberHabitList) {
            // 만약 유지 방법이 요일이라면
            if (memberHabit.getMaintainWay() == MaintainWay.DAY) {
                // 요일 방식으로 트래커 목록 생성
                long plusDay = 1L;
                while (nextMonth.plusDays(plusDay).getDayOfWeek().getValue()
                    != memberHabit.getMaintainAmount()) {
                    plusDay++;
                }
                // 일주일씩 증가시키며 생성
                LocalDate startDay = nextMonth.plusDays(plusDay);
                createHabitTrackerByDay(startDay, lastDay, memberHabit.getMember(), memberHabit,
                    memberHabit.getMaintainAmount());
            } else {
                // 만약 유지 방법이 주기라면
                // 가장 마지막 생성된 해빗 트래커 가져오기
                HabitTracker habitTracker = habitTrackerService.findLastHabitTracker(
                    HabitTrackerLastDto.builder().memberHabit(memberHabit).build());

                // 주기를 더한 날이 말일보다 더 크게 될 때까지 증가
                int startDay = habitTracker.getCreatedDay();
                while (startDay < lastDayOfNowMonth) {
                    startDay += memberHabit.getMaintainAmount();
                }
                // 말일을 뺴서 다음 달의 해당 해빗의 첫 해빗 트래커 날짜를 구함
                startDay -= lastDayOfNowMonth;
                LocalDate startDate = LocalDate.of(nowMonth.getYear(),
                    nextMonth.getMonth().getValue(), startDay);
                // 주기 방식으로 생성
                createHabitTrackerByPeriod(startDate, lastDay, memberHabit.getMember(), memberHabit,
                    memberHabit.getMaintainAmount());
            }
        }
    }

    private void createHabitTrackerByPeriod(LocalDate startDay, LocalDate lastDay, Member member,
        MemberHabit memberHabit, int period) {
        for (LocalDate nowDay = startDay; !nowDay.isAfter(lastDay);
            nowDay = nowDay.plusDays(period)) {
            habitTrackerService.createHabitTrackerByPeriod(
                HabitTrackerCreateDto.builder().member(member).memberHabit(memberHabit)
                    .amount(period).targetDay(nowDay)
                    .build());
        }
    }

    private void createHabitTrackerByDay(LocalDate startDay, LocalDate lastDay, Member member,
        MemberHabit memberHabit, int dayOfWeek) {
        for (LocalDate nowDay = startDay; !nowDay.isAfter(lastDay);
            nowDay = nowDay.plusDays(7L)) {
            habitTrackerService.createHabitTrackerByDay(
                HabitTrackerCreateDto.builder().member(member).memberHabit(memberHabit)
                    .amount(dayOfWeek).targetDay(nowDay)
                    .build());
        }
    }
}
