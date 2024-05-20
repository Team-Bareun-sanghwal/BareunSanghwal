package life.bareun.diary.recap.dto;

import life.bareun.diary.habit.entity.MemberHabit;
import lombok.Builder;

@Builder
public record RecapMemberHabitDto(

    MemberHabit memberHabit,

    Long habitTrackerCount

) { }
