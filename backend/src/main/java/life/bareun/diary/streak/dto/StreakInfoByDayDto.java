package life.bareun.diary.streak.dto;

import lombok.Builder;

@Builder
public record StreakInfoByDayDto(
    Integer dayNumber,
    Long achieveCount,
    Long totalCount) {

}
