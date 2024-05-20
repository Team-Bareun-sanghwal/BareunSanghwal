package life.bareun.diary.recap.dto;

import lombok.Builder;

@Builder
public record RecapHabitRateDto(

    String name,

    int ratio

) { }
