package life.bareun.diary.streak.service;

import java.time.LocalDate;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.streak.dto.response.HabitStreakResDto;
import life.bareun.diary.streak.entity.HabitDailyStreak;

public interface HabitStreakService {

    void createInitialHabitStreak(MemberHabit memberHabit);

    void createHabitDailyStreak(MemberHabit memberHabit, LocalDate date);

    HabitDailyStreak achieveHabitStreak(MemberHabit memberHabit);

    void deleteHabitTotalStreak(MemberHabit memberHabit);

    void deleteHabitDailyStreak(MemberHabit memberHabit);

    HabitStreakResDto getHabitStreakResDtoByMemberHabitId(Long memberHabitId, LocalDate firstDayOfMonth,
        LocalDate lastDayOfMonth);

    HabitStreakResDto getHabitStreakResDtoByMemberId(Long memberId, LocalDate firstDayOfMonth,
        LocalDate lastDayOfMonth);
}
