package life.bareun.diary.member.dto;

import java.util.List;
import life.bareun.diary.habit.dto.HabitTrackerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberHabitTrackerDto {

    private Integer year;
    private List<HabitTrackerDto> habitTrackerList;
}
