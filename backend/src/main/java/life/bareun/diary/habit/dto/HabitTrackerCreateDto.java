package life.bareun.diary.habit.dto;

import java.time.LocalDate;
import life.bareun.diary.habit.entity.MemberHabit;
import lombok.Builder;

@Builder
public record HabitTrackerCreateDto(

    MemberHabit memberHabit,

    LocalDate targetDay,

    int amount

) { }
