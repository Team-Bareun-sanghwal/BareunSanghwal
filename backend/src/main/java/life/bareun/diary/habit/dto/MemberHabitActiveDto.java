package life.bareun.diary.habit.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record MemberHabitActiveDto(

    String name,

    String alias,

    Long memberHabitId,

    String icon,

    LocalDateTime createdAt,

    Long habitTrackerId,

    int currentStreak,

    List<Integer> dayList

) { }
