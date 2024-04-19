package life.bareun.diary.streak.repository;

import life.bareun.diary.streak.entity.MemberDailyStreak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDailyStreakRepository extends JpaRepository<MemberDailyStreak, Long> {

}
