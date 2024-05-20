package life.bareun.diary.habit.dto.request;

import java.util.List;
import lombok.Builder;

@Builder
public record HabitCreateReqDto(

    Long habitId,
    String alias,
    String icon,
    List<Integer> dayOfWeek,
    Integer period

) { }
