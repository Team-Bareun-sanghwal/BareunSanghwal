package life.bareun.diary.habit.repository;

import java.util.List;
import life.bareun.diary.habit.entity.MemberHabit;

public interface HabitDayRepositoryCustom {
    List<Integer> findAllDayByMemberHabit(MemberHabit memberHabit);
}
