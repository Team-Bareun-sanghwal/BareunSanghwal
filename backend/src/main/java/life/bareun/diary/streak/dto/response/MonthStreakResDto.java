package life.bareun.diary.streak.dto.response;

import java.util.List;
import life.bareun.diary.streak.dto.MonthStreakInfoDto;
import lombok.Builder;

@Builder
public record MonthStreakResDto(
    double achieveProportion,
    int dayOfWeekFirst,
    List<MonthStreakInfoDto> dayInfo
) {

}
