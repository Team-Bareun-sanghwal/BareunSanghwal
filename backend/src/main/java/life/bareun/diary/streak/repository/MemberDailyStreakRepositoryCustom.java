package life.bareun.diary.streak.repository;

import java.time.LocalDate;
import java.util.List;
import life.bareun.diary.streak.dto.MonthStreakInfoDto;

public interface MemberDailyStreakRepositoryCustom {

    List<MonthStreakInfoDto> findStreakDayInfoByMemberId(Long memberId, LocalDate firstDayOfMonth,
        LocalDate lastDayOfMonth);
}
