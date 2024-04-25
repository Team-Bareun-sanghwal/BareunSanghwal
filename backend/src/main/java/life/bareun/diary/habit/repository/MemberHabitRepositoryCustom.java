package life.bareun.diary.habit.repository;

import life.bareun.diary.habit.dto.MemberHabitModifyDto;

public interface MemberHabitRepositoryCustom {
    void modifyStatus(MemberHabitModifyDto memberHabitModifyDto);
}
