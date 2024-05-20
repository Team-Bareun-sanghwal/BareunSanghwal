package life.bareun.diary.habit.repository;

import java.util.List;
import life.bareun.diary.habit.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findByNameContaining(String habitName);
}
