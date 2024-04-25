package life.bareun.diary.habit.dto;

import lombok.Builder;

@Builder
public record HabitMatchDto(

    String habitName,

    Long habitId

) { }
