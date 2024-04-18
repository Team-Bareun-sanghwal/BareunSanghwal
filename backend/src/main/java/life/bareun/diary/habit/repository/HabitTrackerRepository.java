package life.bareun.diary.habit.repository;

import life.bareun.diary.habit.entity.HabitTracker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitTrackerRepository extends JpaRepository<HabitTracker, Long> {

}
