package life.bareun.diary.member.dto;

import java.time.LocalDateTime;

public record MemberHabitsDto(
    String name,
    String alias,
    Long memberHabitId,
    String icon,
    LocalDateTime createdAt
) {

}