package life.bareun.diary.recap.dto;

import lombok.Builder;

@Builder
public record RecapMemberHabitRateDto(

    String name,

    int missCount,

    int actionCount,

    int ratio

) { }
