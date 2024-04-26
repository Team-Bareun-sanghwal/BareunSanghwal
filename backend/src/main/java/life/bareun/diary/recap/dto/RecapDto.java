package life.bareun.diary.recap.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record RecapDto(

    int year,

    List<RecapSimpleDto> recapList

) { }
