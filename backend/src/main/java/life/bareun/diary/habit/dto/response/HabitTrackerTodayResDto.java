package life.bareun.diary.habit.dto.response;

import java.util.List;
import life.bareun.diary.habit.dto.HabitTrackerTodayDto;
import lombok.Builder;

@Builder
public record HabitTrackerTodayResDto(

    List<HabitTrackerTodayDto> habitTrackerTodayDtoList

) { }
