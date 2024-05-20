package life.bareun.diary.habit.dto;

import lombok.Builder;

@Builder
public record HabitRecommendDto(

    Long habitId,

    String name

) { }
