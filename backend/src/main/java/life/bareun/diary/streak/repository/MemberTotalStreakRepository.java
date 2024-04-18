package life.bareun.diary.streak.repository;

import life.bareun.diary.streak.entity.MemberTotalStreak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTotalStreakRepository extends JpaRepository<MemberTotalStreak, Long> {

}
