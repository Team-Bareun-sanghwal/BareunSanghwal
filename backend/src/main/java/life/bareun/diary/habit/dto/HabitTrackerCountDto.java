package life.bareun.diary.habit.dto;

import java.time.LocalDate;
import life.bareun.diary.member.entity.Member;
import lombok.Builder;

@Builder
public record HabitTrackerCountDto(

    Member member,

    LocalDate date

) {

}
