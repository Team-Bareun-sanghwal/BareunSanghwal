package life.bareun.diary.streak.service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import life.bareun.diary.global.auth.util.AuthUtil;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.habit.repository.HabitTrackerRepository;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.streak.dto.MonthStreakInfoDto;
import life.bareun.diary.streak.dto.response.MonthStreakResDto;
import life.bareun.diary.streak.entity.HabitDailyStreak;
import life.bareun.diary.streak.entity.HabitTotalStreak;
import life.bareun.diary.streak.entity.embed.AchieveType;
import life.bareun.diary.streak.exception.HabitDailyStreakErrorCode;
import life.bareun.diary.streak.exception.HabitTotalStreakErrorCode;
import life.bareun.diary.streak.exception.StreakException;
import life.bareun.diary.streak.repository.HabitDailyStreakRepository;
import life.bareun.diary.streak.repository.HabitTotalStreakRepository;
import life.bareun.diary.streak.repository.MemberTotalStreakRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class HabitStreakServiceImpl implements HabitStreakService {

    private final HabitTotalStreakRepository habitTotalStreakRepository;
    private final HabitDailyStreakRepository habitDailyStreakRepository;
    private final MemberTotalStreakRepository memberTotalStreakRepository;
    private final MemberRepository memberRepository;
    private final HabitTrackerRepository habitTrackerRepository;

    @Override
    public void createInitialHabitStreak(MemberHabit memberHabit) {
        habitTotalStreakRepository.save(
            HabitTotalStreak.builder()
                .memberHabit(memberHabit)
                .build()
        );

        /*
        초기 해빗 생성 시에 오늘 자의 해빗 데일리 스트릭을 생성.
         */
        createHabitDailyStreak(memberHabit, LocalDate.now());
    }

    @Override
    public void createHabitDailyStreak(MemberHabit memberHabit, LocalDate date) {
        AtomicReference<AchieveType> achieveTypeToday = new AtomicReference<>(AchieveType.NOT_EXISTED);
        AtomicInteger currentStreakToday = new AtomicInteger(0);

        // 해당 날짜에 이미 해빗 데일리 스트릭이 존재하면 익셉션.
        habitDailyStreakRepository.findByMemberHabitAndCreatedDate(memberHabit, date)
            .ifPresent(habitDailyStreak -> {
                throw new StreakException(HabitDailyStreakErrorCode.ALREADY_EXISTED_HABIT_DAILY_STREAK);
            });

        // 해당 날짜에 해빗 트래커가 존재하면 AchieveType을 해빗 트래커가 존재하지만 아직 달성하지 않음으로 설정.
        habitTrackerRepository.findByMemberHabitAndCreatedYearAndCreatedMonthAndCreatedDay(
            memberHabit,
            date.getYear(),
            date.getMonth().getValue(),
            date.getDayOfMonth()
        ).ifPresent(habitTracker -> {
            achieveTypeToday.set(AchieveType.NOT_ACHIEVE);
        });

        /*
        하루 전의 해빗 일일 스트릭을 참조하여 AchieveType이 NOT_ACHIEVE가 아니면
        현재 연속 스트릭을 이어붙인다.
         */
        habitDailyStreakRepository.findByMemberHabitAndCreatedDate(
            memberHabit,
            date.minusDays(1)
        ).ifPresent(habitDailyStreakYesterday -> {
            if (!habitDailyStreakYesterday.getAchieveType().equals(AchieveType.NOT_ACHIEVE)) {
                currentStreakToday.set(habitDailyStreakYesterday.getCurrentStreak());
            }
        });

        habitDailyStreakRepository.save(
            HabitDailyStreak.builder()
                .memberHabit(memberHabit)
                .achieveType(achieveTypeToday.get())
                .currentStreak(currentStreakToday.get())
                .createdDate(date)
                .build()
        );

        /*
        해빗 데일리 스트릭 생성 시 해당 날짜에 해빗 트래커가 존재하면
        해빗 토탈 스트릭의 토탈 트래커 개수도 증가.
        멤버 데일리 스트릭이 존재하면 전체 트래커 수도 증가.
        멤버 토탈 스트릭이 전체 트래커 수를 증가하고 멤버 데일리 스트릭에 아직 어떤 트래커도 없으면 전체 스트릭 수도 증가.
         */
        if (achieveTypeToday.get().equals(AchieveType.NOT_ACHIEVE)) {
            habitTotalStreakRepository.findByMemberHabit(memberHabit)
                .ifPresent(habitTotalStreak -> {
                    habitTotalStreak.modifyTotalTrackerCount(habitTotalStreak.getTotalTrackerCount() + 1);
                });
        }
    }

    @Override
    public HabitDailyStreak achieveHabitStreak(MemberHabit memberHabit) {
        // TODO: 전영빈 / 구체적인 날짜의 지정이 필요하다면 파라메터로 바꿔주자.
        LocalDate today = LocalDate.now();

        HabitDailyStreak habitDailyStreakToday = habitDailyStreakRepository
            .findByMemberHabitAndCreatedDate(memberHabit, today)
            .orElseThrow(() -> new StreakException(HabitDailyStreakErrorCode.NOT_FOUND_HABIT_DAILY_STREAK));

        if (habitDailyStreakToday.getAchieveType().equals(AchieveType.NOT_EXISTED)) {
            throw new StreakException(HabitDailyStreakErrorCode.NOT_EXISTED_HABIT_TRACKER);
        } else if (habitDailyStreakToday.getAchieveType().equals(AchieveType.ACHIEVE)) {
            throw new StreakException(HabitDailyStreakErrorCode.ALREADY_ACHIEVE_DAILY_STREAM);
        }

        habitDailyStreakToday.modifyCurrentStreak(habitDailyStreakToday.getCurrentStreak() + 1);
        habitDailyStreakToday.modifyAchieveType(AchieveType.ACHIEVE);

        // 해빗 토탈 스트릭의 달성 값을 수정하면서 최장 스트릭 변동 가능 시 함께 수정.
        habitTotalStreakRepository.findByMemberHabit(memberHabit)
            .ifPresentOrElse(habitTotalStreak -> {
                habitTotalStreak.modifyAchieveTrackerCount(habitTotalStreak.getAchieveTrackerCount() + 1);
                habitTotalStreak.modifyLongestStreak(Math.max(
                    habitTotalStreak.getLongestStreak(),
                    habitDailyStreakToday.getCurrentStreak()
                ));
            }, () -> {
                throw new StreakException(HabitTotalStreakErrorCode.NOT_FOUND_HABIT_TOTAL_STREAK);
            });

        return habitDailyStreakToday;
    }

    @Override
    public void deleteHabitTotalStreak(MemberHabit memberHabit) {
        habitTotalStreakRepository.deleteByMemberHabit(memberHabit);
    }

    @Override
    public void deleteHabitDailyStreak(MemberHabit memberHabit) {
        habitDailyStreakRepository.deleteAllByMemberHabit(memberHabit);
    }

    @Override
    public MonthStreakResDto getHabitDailyStreakResDtoByMemberHabitId(Long memberHabitId,
        LocalDate firstDayOfMonth,
        LocalDate lastDayOfMonth) {
        List<MonthStreakInfoDto> streakDayInfoList = habitDailyStreakRepository
            .findStreakDayInfoByMemberHabitId(memberHabitId, firstDayOfMonth, lastDayOfMonth);

        int achieveCount = 0;
        int totalCount = 0;
        for (MonthStreakInfoDto dto : streakDayInfoList) {
            achieveCount += dto.achieveCount();
            totalCount += dto.totalCount();
        }

        return MonthStreakResDto.builder()
            .achieveProportion(getProportion(achieveCount, totalCount))
            .dayOfWeekFirst(firstDayOfMonth.getDayOfWeek().getValue() - 1)
            .dayInfo(habitDailyStreakRepository.findStreakDayInfoByMemberHabitId(memberHabitId, firstDayOfMonth,
                lastDayOfMonth))
            .build();
    }

    private Member getCurrentMember() {
        return memberRepository.findById(AuthUtil.getMemberIdFromAuthentication())
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }

    private double getProportion(int achieveCount, int totalCount) {
        return Math.round((double) achieveCount / (double) totalCount * 100.0);
    }
}
