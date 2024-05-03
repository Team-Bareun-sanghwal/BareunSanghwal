package life.bareun.diary.global.elastic.dto;

import java.time.LocalDateTime;

public record ElasticDto(

    Long habitId,

    LocalDateTime localDateTime

) { }
