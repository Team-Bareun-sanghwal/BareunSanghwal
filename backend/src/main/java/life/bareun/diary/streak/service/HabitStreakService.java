package life.bareun.diary.streak.service;

import java.time.LocalDate;
import life.bareun.diary.habit.entity.MemberHabit;

public interface HabitStreakService {

    void createInitialHabitStreak(MemberHabit memberHabit);

    void createHabitDailyStreak(MemberHabit memberHabit, LocalDate date);
    
    void modifyHabitDailyStreak(MemberHabit memberHabit);

    void deleteHabitDailyStreak(MemberHabit memberHabit);
}
