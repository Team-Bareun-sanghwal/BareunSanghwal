package life.bareun.diary.member.dto.response;

import java.util.List;
import life.bareun.diary.member.dto.MemberHabitListElementDto;

public record MemberHabitListResDto(
    List<MemberHabitListElementDto> habitList
) {

}
