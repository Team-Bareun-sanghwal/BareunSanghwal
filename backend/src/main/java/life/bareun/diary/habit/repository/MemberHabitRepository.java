package life.bareun.diary.habit.repository;

import life.bareun.diary.habit.entity.MemberHabit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberHabitRepository extends JpaRepository<MemberHabit, Long>,
    MemberHabitRepositoryCustom {

}
