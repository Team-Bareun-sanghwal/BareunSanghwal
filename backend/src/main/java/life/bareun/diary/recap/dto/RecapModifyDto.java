package life.bareun.diary.recap.dto;

import life.bareun.diary.recap.entity.Recap;
import life.bareun.diary.recap.entity.embed.Occasion;
import lombok.Builder;

@Builder
public record RecapModifyDto(

    Recap recap,

    int wholeStreak,

    String maxHabitImage,

    String mostFrequencyWord,

    Occasion mostFrequencyTime

) { }
