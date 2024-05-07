package life.bareun.diary.streak.repository;

import java.time.LocalDate;
import java.util.Optional;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.streak.entity.HabitDailyStreak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitDailyStreakRepository extends JpaRepository<HabitDailyStreak, Long>,
    HabitDailyStreakRepositoryCustom {

    Optional<HabitDailyStreak> findByMemberHabitAndCreatedDate(MemberHabit memberHabit, LocalDate date);

    // HabitDailyStreak findByMemberHabitAndCreatedDate(MemberHabit memberHabit, LocalDate localDate);

}
