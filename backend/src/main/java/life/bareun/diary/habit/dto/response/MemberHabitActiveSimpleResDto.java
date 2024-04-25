package life.bareun.diary.habit.dto.response;

import java.util.List;
import life.bareun.diary.habit.dto.MemberHabitActiveSimpleDto;
import lombok.Builder;

@Builder
public record MemberHabitActiveSimpleResDto(

    List<MemberHabitActiveSimpleDto> memberHabitList

) { }
