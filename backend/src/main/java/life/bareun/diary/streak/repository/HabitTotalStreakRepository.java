package life.bareun.diary.streak.repository;

import life.bareun.diary.streak.entity.HabitTotalStreak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitTotalStreakRepository extends JpaRepository<HabitTotalStreak, Long> {

}
