package life.bareun.diary.recap.dto.request;

import java.util.List;
import life.bareun.diary.recap.dto.RecapDto;
import lombok.Builder;

@Builder
public record RecapListResDto(

    List<RecapDto> recapList

) { }
