package life.bareun.diary.habit.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record HabitTrackerDetailResDto(

    Long habitTrackerId,

    String alias,

    String content,

    String image,

    int day,

    LocalDate createdAt,

    LocalDateTime succeededTime

) { }
