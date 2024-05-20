package life.bareun.diary.streak.service;

import java.time.LocalDate;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.streak.dto.response.MonthStreakResDto;
import life.bareun.diary.streak.entity.HabitDailyStreak;

public interface HabitStreakService {

    void createInitialHabitStreak(MemberHabit memberHabit);

    void createHabitDailyStreak(MemberHabit memberHabit, LocalDate date);

    HabitDailyStreak achieveHabitStreak(MemberHabit memberHabit, LocalDate date);

    void deleteHabitTotalStreak(MemberHabit memberHabit);

    void deleteHabitDailyStreak(MemberHabit memberHabit);

    MonthStreakResDto getHabitDailyStreakResDtoByMemberHabitId(Long memberHabitId, LocalDate firstDayOfMonth,
        LocalDate lastDayOfMonth);

}
