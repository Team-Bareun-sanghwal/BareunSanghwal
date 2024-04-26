package life.bareun.diary.recap.dto;

import lombok.Builder;

@Builder
public record MessageDto(

    String role,

    String content

) { }
