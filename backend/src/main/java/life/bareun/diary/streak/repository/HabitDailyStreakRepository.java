package life.bareun.diary.streak.repository;

import life.bareun.diary.streak.entity.HabitDailyStreak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitDailyStreakRepository extends JpaRepository<HabitDailyStreak, Long> {

}