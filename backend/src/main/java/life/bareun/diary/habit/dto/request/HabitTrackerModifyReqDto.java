package life.bareun.diary.habit.dto.request;

import lombok.Builder;

@Builder
public record HabitTrackerModifyReqDto(

    Long habitTrackerId,

    String content

) { }
