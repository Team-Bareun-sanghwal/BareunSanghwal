package life.bareun.diary.habit.dto.request;

import lombok.Builder;

@Builder
public record HabitDeleteReqDto(

    Long memberHabitId,

    Boolean isDeleteAll

) { }
