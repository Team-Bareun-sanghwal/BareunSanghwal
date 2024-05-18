package life.bareun.diary.habit.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberHabitRepository extends JpaRepository<MemberHabit, Long>,
    MemberHabitRepositoryCustom {

    List<MemberHabit> findAllByMemberAndCreatedDatetimeBefore(Member member, LocalDateTime endOfMonth);

    List<MemberHabit> findAllByIsDeleted(Boolean isDeleted);

    List<MemberHabit> findAllByIsDeletedAndMember_OrderByCreatedDatetimeDesc(Boolean isDeleted, Member member);

    Optional<MemberHabit> findByMember(Member member);
}
