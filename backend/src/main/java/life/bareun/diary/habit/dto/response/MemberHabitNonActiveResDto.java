package life.bareun.diary.habit.dto.response;

import java.util.List;
import life.bareun.diary.habit.dto.MemberHabitNonActiveDto;
import lombok.Builder;

@Builder
public record MemberHabitNonActiveResDto(

    List<MemberHabitNonActiveDto> memberHabitList

) { }
