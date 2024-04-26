package life.bareun.diary.recap.dto;

import lombok.Builder;

@Builder
public record RecapMonthDto(

    int year,

    int month

) { }
