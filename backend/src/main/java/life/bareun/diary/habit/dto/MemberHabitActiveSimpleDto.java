package life.bareun.diary.habit.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record MemberHabitActiveSimpleDto(

    Long memberHabitId,

    String name,

    String alias,

    List<Integer> dayList

) { }
