package life.bareun.diary.streak.dto.response;

import java.util.List;
import life.bareun.diary.streak.dto.StreakInfoByDayDto;
import lombok.Builder;

@Builder
public record HabitStreakResDto(
    double achieveProportion,
    int dayOfWeekFirst,
    List<StreakInfoByDayDto> dayInfo
) {

}
