package life.bareun.diary.recap.dto.response;

import java.util.List;
import life.bareun.diary.recap.dto.RecapHabitRateDto;
import life.bareun.diary.recap.dto.RecapMemberHabitRateDto;
import lombok.Builder;

@Builder
public record RecapDetailResDto(

    String mostSucceededHabit,

    String mostSucceededMemberHabit,

    Double averageRateByMemberHabit,

    List<RecapMemberHabitRateDto> rateByMemberHabitList,

    List<RecapHabitRateDto> rateByHabitList,

    String mostSubmitTime,

    int collectedStar,

    String myKeyWord,

    String image

) { }
