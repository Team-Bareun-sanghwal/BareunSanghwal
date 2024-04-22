package life.bareun.diary.habit.dto.response;

import java.util.List;
import life.bareun.diary.habit.dto.MemberHabitDto;
import lombok.Builder;

@Builder
public record MemberHabitResDto(
    List<MemberHabitDto> memberHabitDtoList

) { }
