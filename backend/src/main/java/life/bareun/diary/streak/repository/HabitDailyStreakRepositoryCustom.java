package life.bareun.diary.streak.repository;

import java.time.LocalDate;
import java.util.List;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.streak.dto.StreakInfoByDayDto;
import life.bareun.diary.streak.entity.HabitDailyStreak;

public interface HabitDailyStreakRepositoryCustom {

    List<StreakInfoByDayDto> findStreakDayInfoByMemberHabitId(Long memberHabitId, LocalDate firstDayOfMonth,
        LocalDate lastDayOfMonth);

    List<StreakInfoByDayDto> findStreakDayInfoByMemberId(Long memberId, LocalDate firstDayOfMonth,
        LocalDate lastDayOfMonth);

    List<HabitDailyStreak> findAllHabitDailyStreakByMemberHabitIdBetweenStartDateAndEndDate(
        MemberHabit memberHabit, LocalDate startDate, LocalDate endDate);
}
