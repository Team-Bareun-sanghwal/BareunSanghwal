package life.bareun.diary.habit.repository;

import java.time.LocalDateTime;
import java.util.List;
import life.bareun.diary.habit.entity.HabitTracker;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitTrackerRepository extends JpaRepository<HabitTracker, Long>,
    HabitTrackerRepositoryCustom {

    List<HabitTracker> findAllByMemberHabit(MemberHabit memberHabit);

    int countByDay(int day);

    Boolean existsByMemberHabitAndSucceededTimeIsNotNull(MemberHabit memberHabit);

    int countByMemberHabit(MemberHabit memberHabit);

    List<HabitTracker> findAllByMemberHabitAndContentIsNotNullAndSucceededTimeBetween(
        MemberHabit memberHabit, LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<HabitTracker> findAllByMemberAndSucceededTimeIsNotNullAndSucceededTimeBetween(
        Member member, LocalDateTime startDateTime, LocalDateTime endDateTime);

}
