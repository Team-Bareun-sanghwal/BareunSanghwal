package life.bareun.diary.habit.repository;

import java.util.List;
import life.bareun.diary.habit.dto.MemberHabitModifyDto;
import life.bareun.diary.member.dto.MemberHabitListElementDto;

public interface MemberHabitRepositoryCustom {

    void modifyStatus(MemberHabitModifyDto memberHabitModifyDto);

    List<MemberHabitListElementDto> findAllByMemberIdOrderByCreatedDatetime(Long id);
}
