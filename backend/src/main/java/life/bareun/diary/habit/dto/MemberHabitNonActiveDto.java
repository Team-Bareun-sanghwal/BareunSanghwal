package life.bareun.diary.habit.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record MemberHabitNonActiveDto(

    String name,

    String alias,

    Long memberHabitId,

    String icon,

    LocalDateTime createdAt,

    LocalDateTime succeededTime

) { }
