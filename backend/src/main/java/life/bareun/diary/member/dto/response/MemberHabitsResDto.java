package life.bareun.diary.member.dto.response;

import java.util.List;
import life.bareun.diary.member.dto.MemberHabitsDto;

public record MemberHabitsResDto(
    List<MemberHabitsDto> habitList
) {

}
