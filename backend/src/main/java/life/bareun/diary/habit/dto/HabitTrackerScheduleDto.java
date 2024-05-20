package life.bareun.diary.habit.dto;

import java.time.LocalDate;
import life.bareun.diary.habit.entity.MemberHabit;
import lombok.Builder;

@Builder
public record HabitTrackerScheduleDto(
    MemberHabit memberHabit,

    LocalDate date
) {

}
