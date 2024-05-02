package life.bareun.diary.streak.repository;

import java.time.LocalDate;
import java.util.List;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.streak.dto.StreakInfoByDayDto;
import life.bareun.diary.streak.entity.HabitDailyStreak;

public interface HabitDailyStreakRepositoryCustom {

    List<StreakInfoByDayDto> findStreakDayInfoByMemberHabitId(LocalDate firstDayOfMonth, LocalDate lastDayOfMonth,
        Long memberHabitId);

    List<StreakInfoByDayDto> findStreakDayInfoByMemberId(LocalDate firstDayOfMonth, LocalDate lastDayOfMonth,
        Long memberId);
    
    List<HabitDailyStreak> findAllHabitDailyStreakByMemberHabitIdBetweenStartDateAndEndDate(
        MemberHabit memberHabit, LocalDate startDate, LocalDate endDate);
}
