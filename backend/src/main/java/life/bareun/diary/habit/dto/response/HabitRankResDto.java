package life.bareun.diary.habit.dto.response;

import java.util.List;
import life.bareun.diary.habit.dto.HabitRecommendDto;
import lombok.Builder;

@Builder
public record HabitRankResDto(

    List<HabitRecommendDto> habitList

) { }
