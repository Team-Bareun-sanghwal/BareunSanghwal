package life.bareun.diary.streak.dto.request;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record StreakRecoveryReqDto(
    LocalDate date
) {

}
