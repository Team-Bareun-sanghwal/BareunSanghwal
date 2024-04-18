package life.bareun.diary.habit.repository;

import life.bareun.diary.habit.entity.HabitTracker;
import life.bareun.diary.habit.entity.MemberHabit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitTrackerRepository extends JpaRepository<HabitTracker, Long>,
    HabitTrackerRepositoryCustom {

    void deleteAllByMemberHabit(MemberHabit memberHabit);
}
