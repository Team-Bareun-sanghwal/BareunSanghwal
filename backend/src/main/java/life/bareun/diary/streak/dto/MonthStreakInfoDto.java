package life.bareun.diary.streak.dto;

import life.bareun.diary.streak.entity.embed.AchieveType;
import lombok.Builder;

@Builder
public record MonthStreakInfoDto(
    Integer dayNumber,
    AchieveType achieveType,
    Integer achieveCount,
    Integer totalCount
) {

}
