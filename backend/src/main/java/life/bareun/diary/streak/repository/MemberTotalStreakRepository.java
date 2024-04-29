package life.bareun.diary.streak.repository;

import java.util.Optional;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.streak.entity.MemberTotalStreak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTotalStreakRepository extends JpaRepository<MemberTotalStreak, Long> {

    Optional<MemberTotalStreak> findByMember(Member member);

}
