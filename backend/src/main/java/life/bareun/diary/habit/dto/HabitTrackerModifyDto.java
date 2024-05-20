package life.bareun.diary.habit.dto;

import lombok.Builder;

@Builder
public record HabitTrackerModifyDto(

    Long habitTrackerId,

    String content,

    String image

) { }
