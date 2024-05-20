package life.bareun.diary.streak.repository;

import java.time.LocalDate;
import java.util.List;
import life.bareun.diary.streak.dto.MonthStreakInfoDto;

public interface HabitDailyStreakRepositoryCustom {

    List<MonthStreakInfoDto> findStreakDayInfoByMemberHabitId(Long memberHabitId, LocalDate firstDayOfMonth,
        LocalDate lastDayOfMonth);
    
}
