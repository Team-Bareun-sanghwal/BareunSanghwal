package life.bareun.diary.habit.dto.request;

import lombok.Builder;

@Builder
public record HabitTrackerModifyDto(

    Long habitTrackerId,

    String content,

    String image

) { }
