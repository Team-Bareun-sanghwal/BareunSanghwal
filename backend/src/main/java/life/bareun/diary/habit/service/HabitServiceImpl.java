package life.bareun.diary.habit.service;

import java.time.LocalDate;
import java.time.YearMonth;
import life.bareun.diary.habit.dto.HabitTrackerCreateDto;
import life.bareun.diary.habit.dto.request.HabitCreateReqDto;
import life.bareun.diary.habit.dto.request.HabitDeleteReqDto;
import life.bareun.diary.habit.entity.Habit;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.habit.entity.embed.MaintainWay;
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
        Habit habit = habitRepository.findById(habitCreateReqDto.habitId()).orElseThrow();
        Member member = memberRepository.findById(1L).orElseThrow();
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
    public void deleteMemberHabit(HabitDeleteReqDto habitDeleteReqDto) {
        if (Boolean.TRUE.equals(habitDeleteReqDto.isDeleteAll())) {
            habitTrackerService.deleteAllHabitTracker(habitDeleteReqDto.memberHabitId());
            memberHabitRepository.deleteById(habitDeleteReqDto.memberHabitId());
        } else {
            habitTrackerService.deleteAfterHabitTracker(habitDeleteReqDto.memberHabitId());
            memberHabitRepository.modifyStatus(habitDeleteReqDto.memberHabitId());
        }
    }
}
