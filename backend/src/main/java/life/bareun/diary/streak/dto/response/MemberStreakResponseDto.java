package life.bareun.diary.streak.dto.response;

import lombok.Builder;

@Builder
public record MemberStreakResponseDto(int currentTrackerCount, int totalTrackerCount, int currentStreak) {

}