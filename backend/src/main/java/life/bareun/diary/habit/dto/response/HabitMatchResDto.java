package life.bareun.diary.habit.dto.response;

import java.util.List;
import life.bareun.diary.habit.dto.HabitMatchDto;
import lombok.Builder;

@Builder
public record HabitMatchResDto(

    List<HabitMatchDto> habitList

) { }
