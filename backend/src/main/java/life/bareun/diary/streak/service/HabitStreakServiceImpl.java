package life.bareun.diary.streak.service;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.habit.repository.HabitTrackerRepository;
import life.bareun.diary.streak.entity.HabitDailyStreak;
import life.bareun.diary.streak.entity.HabitTotalStreak;
import life.bareun.diary.streak.entity.embed.AchieveType;
import life.bareun.diary.streak.exception.HabitDailyStreakErrorCode;
import life.bareun.diary.streak.exception.StreakException;
import life.bareun.diary.streak.repository.HabitDailyStreakRepository;
import life.bareun.diary.streak.repository.HabitTotalStreakRepository;
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
    private final HabitTrackerRepository habitTrackerRepository;

    @Override
    public void createInitialHabitStreak(MemberHabit memberHabit) {
        habitTotalStreakRepository.save(
            HabitTotalStreak.builder()
                .memberHabit(memberHabit)
                .build()
        );

        // 초기 해빗 생성 시에 오늘 자와 내일 자 해빗 데일리 스트릭을 생성.
        // 해빗 데일리 스트릭을 생성하는 시점이 스케줄러의 작동 시점의 이후일 수 있으므로.
        createHabitDailyStreak(memberHabit, LocalDate.now());
        createHabitDailyStreak(memberHabit, LocalDate.now().plusDays(1));
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

        // 하루 전의 해빗 일일 스트릭을 참조하여 AchieveType이 NOT_ACHIEVE가 아니면
        // 현재 연속 스트릭을 이어붙인다.
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

        // 해빗 데일리 스트릭 생성 시 해당 날짜에 해빗 트래커가 존재하면
        // 해빗 토탈 스트릭의 토탈 트래커 개수도 증가.
        if (achieveTypeToday.get().equals(AchieveType.NOT_ACHIEVE)) {
            habitTotalStreakRepository.findByMemberHabit(memberHabit)
                .ifPresent(habitTotalStreak -> {
                    habitTotalStreak.modifyTotalTrackerCount(habitTotalStreak.getTotalTrackerCount() + 1);
                });
        }
    }

    @Override
    public void modifyHabitDailyStreak(MemberHabit memberHabit) {
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
            .ifPresent(habitTotalStreak -> {
                habitTotalStreak.modifyAchieveTrackerCount(habitTotalStreak.getAchieveTrackerCount() + 1);
                habitTotalStreak.modifyLongestStreak(Math.max(
                    habitTotalStreak.getLongestStreak(),
                    habitDailyStreakToday.getCurrentStreak()
                ));
            });

        // 다음 날의 해빗 데일리 스트릭이 존재하면 currentStreak 값을 함께 수정.
        habitDailyStreakRepository.findByMemberHabitAndCreatedDate(memberHabit, today.plusDays(1))
            .ifPresent(habitDailyStreakTomorrow -> {
                habitDailyStreakTomorrow.modifyCurrentStreak(habitDailyStreakToday.getCurrentStreak());
            });
    }
}
