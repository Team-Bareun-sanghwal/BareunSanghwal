package life.bareun.diary.habit.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import life.bareun.diary.habit.dto.HabitTrackerCreateDto;
import life.bareun.diary.habit.dto.MemberHabitDto;
import life.bareun.diary.habit.dto.request.HabitCreateReqDto;
import life.bareun.diary.habit.dto.request.HabitDeleteReqDto;
import life.bareun.diary.habit.dto.response.MemberHabitResDto;
import life.bareun.diary.habit.entity.Habit;
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
            .orElseThrow(() -> new HabitException(
                HabitErrorCode.NOT_FOUND_HABIT));
        Member member = memberRepository.findById(1L)
            .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_MEMBER));
        // 오늘 연, 월, 일 가져오기
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
            for (LocalDate nowDay = startDay; !nowDay.isAfter(lastDay);
                nowDay = nowDay.plusDays(habitCreateReqDto.period())) {
                habitTrackerService.createHabitTrackerByPeriod(
                    HabitTrackerCreateDto.builder().member(member).memberHabit(memberHabit)
                        .amount(habitCreateReqDto.period()).targetDay(nowDay)
                        .build());
            }
        } else {
            MemberHabit memberHabit = memberHabitRepository.save(
                MemberHabit.builder().member(member).habit(habit).alias(habitCreateReqDto.alias())
                    .icon(habitCreateReqDto.icon()).isDeleted(false).maintainWay(
                        MaintainWay.DAY).maintainAmount(habitCreateReqDto.dayOfWeek()).build());

            // 요일 방식으로 트래커 목록 생성
            long plusDay = 0L;
            while (LocalDate.now().plusDays(plusDay).getDayOfWeek().getValue()
                != habitCreateReqDto.dayOfWeek()) {
                plusDay++;
            }

            // 일주일씩 증가시키며 생성
            LocalDate startDay = LocalDate.now().plusDays(plusDay);
            for (LocalDate nowDay = startDay; !nowDay.isAfter(lastDay);
                nowDay = nowDay.plusDays(7L)) {
                habitTrackerService.createHabitTrackerByDay(
                    HabitTrackerCreateDto.builder().member(member).memberHabit(memberHabit)
                        .amount(habitCreateReqDto.dayOfWeek()).targetDay(nowDay)
                        .build());
            }
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
}
