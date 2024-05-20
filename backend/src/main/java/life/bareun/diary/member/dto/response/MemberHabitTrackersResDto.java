package life.bareun.diary.member.dto.response;

import java.util.List;
import life.bareun.diary.member.dto.MemberHabitTrackerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberHabitTrackersResDto {

    List<MemberHabitTrackerDto> habitTrackerGroupList;
    List<Integer> yearList;
}
