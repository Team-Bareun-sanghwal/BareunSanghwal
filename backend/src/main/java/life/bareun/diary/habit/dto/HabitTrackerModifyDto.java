package life.bareun.diary.habit.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record HabitTrackerModifyDto(

    Long habitTrackerId,

    String content,

    String image,

    LocalDateTime succeededTime

) {

}
