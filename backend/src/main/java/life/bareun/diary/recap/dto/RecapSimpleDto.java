package life.bareun.diary.recap.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record RecapSimpleDto(

    Long recapId,

    String image,

    LocalDate period

) {}
