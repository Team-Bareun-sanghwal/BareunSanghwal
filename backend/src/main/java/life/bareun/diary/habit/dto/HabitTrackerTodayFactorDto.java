package life.bareun.diary.habit.dto;

import lombok.Builder;

@Builder
public record HabitTrackerTodayFactorDto(

    Long memberId,

    int createdYear,

    int createdMonth,

    int createdDay

) { }
