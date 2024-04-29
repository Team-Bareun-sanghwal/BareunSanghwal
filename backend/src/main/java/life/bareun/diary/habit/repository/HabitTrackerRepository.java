package life.bareun.diary.habit.repository;

import java.util.List;
import life.bareun.diary.habit.entity.HabitTracker;
import life.bareun.diary.habit.entity.MemberHabit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitTrackerRepository extends JpaRepository<HabitTracker, Long>,
    HabitTrackerRepositoryCustom {

    List<HabitTracker> findAllByMemberHabit(MemberHabit memberHabit);

    int countByDay(int day);

    Boolean existsByMemberHabitAndSucceededTimeIsNotNull(MemberHabit memberHabit);

}
