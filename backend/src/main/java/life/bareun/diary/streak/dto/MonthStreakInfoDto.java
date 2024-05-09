package life.bareun.diary.streak.dto;

import lombok.Builder;

@Builder
public record MonthStreakInfoDto(
    Integer dayNumber,
    Integer achieveCount,
    Integer totalCount
) {

}
