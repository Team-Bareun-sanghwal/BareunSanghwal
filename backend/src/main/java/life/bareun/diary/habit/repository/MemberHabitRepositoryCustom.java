package life.bareun.diary.habit.repository;

import java.util.List;
import life.bareun.diary.member.dto.MemberHabitListElementDto;
import life.bareun.diary.habit.dto.MemberHabitModifyDto;

public interface MemberHabitRepositoryCustom {
    void modifyStatus(MemberHabitModifyDto memberHabitModifyDto);

    List<MemberHabitListElementDto> findAllByMemberIdOrderByCreatedDatetime(Long id);
}
