package life.bareun.diary.habit.dto;

import life.bareun.diary.habit.entity.MemberHabit;
import lombok.Builder;

@Builder
public record HabitTrackerDeleteDto(

    MemberHabit memberHabit,

    int year,

    int month,

    int day

) { }
