package life.bareun.diary.habit.repository;

import java.util.List;
import life.bareun.diary.habit.entity.HabitDay;
import life.bareun.diary.habit.entity.MemberHabit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitDayRepository extends JpaRepository<HabitDay, Long>,
    HabitDayRepositoryCustom {
    void deleteAllByMemberHabit(MemberHabit memberHabit);

    List<HabitDay> findAllByMemberHabit(MemberHabit memberHabit);

}
