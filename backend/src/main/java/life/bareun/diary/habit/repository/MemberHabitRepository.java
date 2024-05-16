package life.bareun.diary.habit.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberHabitRepository extends JpaRepository<MemberHabit, Long>,
    MemberHabitRepositoryCustom {

    List<MemberHabit> findAllByMemberAndCreatedDatetimeBetween(Member member,
        LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<MemberHabit> findAllByIsDeleted(Boolean isDeleted);

    List<MemberHabit> findAllByIsDeletedAndMember(Boolean isDeleted, Member member);

    List<MemberHabit> findAllByMemberAndCreatedDatetimeBeforeAndSucceededDatetimeAfter(
        Member member,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime);

    Optional<MemberHabit> findByMember(Member member);
}
