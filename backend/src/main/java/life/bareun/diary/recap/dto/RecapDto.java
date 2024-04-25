package life.bareun.diary.recap.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record RecapDto(

    Long recapId,

    String image,

    LocalDate period

) { }
