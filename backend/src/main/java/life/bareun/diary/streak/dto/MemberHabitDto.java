package life.bareun.diary.streak.dto;

import lombok.Builder;

@Builder
public record MemberHabitDto(int memberHabitId, String alias, String icon) {

}
