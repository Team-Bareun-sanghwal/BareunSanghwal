package life.bareun.diary.streak.dto.response;

import lombok.Builder;

@Builder
public record StreakRecoveryInfoResDto(
    int changedCurrentStreak,
    int changedLongestStreak
) {

}
