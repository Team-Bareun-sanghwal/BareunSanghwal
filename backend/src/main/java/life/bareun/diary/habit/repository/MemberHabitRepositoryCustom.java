package life.bareun.diary.habit.repository;

import java.util.List;
import life.bareun.diary.member.dto.MemberHabitsDto;
import life.bareun.diary.habit.dto.MemberHabitModifyDto;

public interface MemberHabitRepositoryCustom {
    void modifyStatus(MemberHabitModifyDto memberHabitModifyDto);

    List<MemberHabitsDto> findAllByMemberIdOrderByCreatedDatetime(Long id);
}
