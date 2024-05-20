package life.bareun.diary.habit.dto.response;

import java.util.List;
import life.bareun.diary.habit.dto.MemberHabitActiveDto;
import lombok.Builder;

@Builder
public record MemberHabitActiveResDto(

    List<MemberHabitActiveDto> memberHabitList


) { }
