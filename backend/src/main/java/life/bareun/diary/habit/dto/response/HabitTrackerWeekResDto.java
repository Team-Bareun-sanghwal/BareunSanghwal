package life.bareun.diary.habit.dto.response;

import lombok.Builder;

@Builder
public record HabitTrackerWeekResDto(

    int monday,

    int tuesday,

    int wednesday,

    int thursday,

    int friday,

    int saturday,

    int sunday

) { }
