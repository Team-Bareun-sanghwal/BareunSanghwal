package life.bareun.diary.recap.dto.request;

import java.util.List;
import life.bareun.diary.recap.dto.MessageDto;
import lombok.Builder;
import lombok.Singular;

@Builder
public record GptReqDto(

    String model,

    @Singular("message") List<MessageDto> messages,

    int max_tokens,

    double temperature

) {

}
