package life.bareun.diary.streak.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.streak.entity.HabitDailyStreak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitDailyStreakRepository extends JpaRepository<HabitDailyStreak, Long>,
    HabitDailyStreakRepositoryCustom {

    Optional<HabitDailyStreak> findByMemberHabitAndCreatedDate(MemberHabit memberHabit, LocalDate date);

    void deleteAllByMemberHabit(MemberHabit memberHabit);

    List<HabitDailyStreak> findByMemberHabitAndCreatedDateBeforeOrderByCreatedDateDesc(MemberHabit memberHabit,
        LocalDate date);
}
