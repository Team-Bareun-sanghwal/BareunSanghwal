package life.bareun.diary.streak.repository;

import java.util.Optional;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.streak.entity.HabitTotalStreak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitTotalStreakRepository extends JpaRepository<HabitTotalStreak, Long> {

    Optional<HabitTotalStreak> findByMemberHabit(MemberHabit memberHabit);

    void deleteByMemberHabit(MemberHabit memberHabit);
}
