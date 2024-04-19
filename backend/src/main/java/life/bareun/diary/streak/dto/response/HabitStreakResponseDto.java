package life.bareun.diary.streak.dto.response;

import java.util.List;
import life.bareun.diary.streak.dto.MemberHabitDto;
import life.bareun.diary.streak.dto.StreakInfoByDayDto;
import lombok.Builder;

@Builder
public record HabitStreakResponseDto(double achieveProportion, int dayOfWeekFirst, List<MemberHabitDto> memberHabitList,
                                     List<StreakInfoByDayDto> dayInfo) {

}
