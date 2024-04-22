package life.bareun.diary.habit.dto;

import lombok.Builder;

@Builder
public record MemberHabitDto(
    String alias,

    Long memberHabitId,

    String icon

) { }
