package life.bareun.diary.habit.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record HabitTrackerTodayDto(

    String name,

    String alias,

    Long memberHabitId,

    Long habitTrackerId,

    String icon,

    LocalDateTime succeededTime,

    int day

) { }
