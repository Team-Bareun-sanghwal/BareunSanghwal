package life.bareun.diary.habit.repository;

import life.bareun.diary.habit.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<Habit, Long> {

}
