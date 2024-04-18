package life.bareun.diary.habit.dto.request;

import lombok.Builder;

@Builder
public record HabitCreateReqDto(

    Long habitId,
    String alias,
    String icon,
    Integer dayOfWeek,

    Integer period

) { }
