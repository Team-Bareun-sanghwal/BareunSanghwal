package life.bareun.diary.streak.service;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.habit.repository.HabitTrackerRepository;
import life.bareun.diary.streak.entity.HabitDailyStreak;
import life.bareun.diary.streak.entity.HabitTotalStreak;
import life.bareun.diary.streak.entity.embed.AchieveType;
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

        createHabitDailyStreak(memberHabit, LocalDate.now());
        createHabitDailyStreak(memberHabit, LocalDate.now().plusDays(1));
    }

    @Override
    public void createHabitDailyStreak(MemberHabit memberHabit, LocalDate date) {
        LocalDate today = LocalDate.now();
        AtomicReference<AchieveType> achieveTypeToday = new AtomicReference<>(AchieveType.NOT_EXISTED);
        AtomicInteger currentStreakToday = new AtomicInteger(0);

        habitTrackerRepository.findByMemberHabitAndCreatedYearAndCreatedMonthAndCreatedDay(
            memberHabit,
            today.getYear(),
            today.getMonth().getValue(),
            today.getDayOfMonth()
        ).ifPresent(habitTracker -> {
            achieveTypeToday.set(AchieveType.NOT_ACHIEVE);
        });

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
                .createdDate(today)
                .build()
        );
    }
}
