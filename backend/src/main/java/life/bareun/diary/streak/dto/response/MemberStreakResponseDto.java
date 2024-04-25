package life.bareun.diary.streak.dto.response;

import lombok.Builder;

@Builder
public record MemberStreakResponseDto(int totalStreakCount, int achieveStreakCount, int starCount,
                                      int longestStreakCount) {

}