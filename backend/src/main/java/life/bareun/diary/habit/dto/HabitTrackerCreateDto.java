package life.bareun.diary.habit.dto;

import java.time.LocalDate;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.member.entity.Member;
import lombok.Builder;

@Builder
public record HabitTrackerCreateDto(

    Member member,
    
    MemberHabit memberHabit,

    LocalDate targetDay,

    int amount

) { }
