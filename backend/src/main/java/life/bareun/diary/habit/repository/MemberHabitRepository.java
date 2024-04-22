package life.bareun.diary.habit.repository;

import java.time.LocalDateTime;
import java.util.List;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberHabitRepository extends JpaRepository<MemberHabit, Long>,
    MemberHabitRepositoryCustom {

    List<MemberHabit> findAllByMemberAndCreatedDatetimeBetween(Member member,
        LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<MemberHabit> findAllByIsDeleted(boolean isDeleted);
}
