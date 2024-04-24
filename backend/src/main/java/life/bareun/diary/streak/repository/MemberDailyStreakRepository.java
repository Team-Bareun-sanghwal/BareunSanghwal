package life.bareun.diary.streak.repository;

import java.time.LocalDate;
import java.util.Optional;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.streak.entity.MemberDailyStreak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDailyStreakRepository extends JpaRepository<MemberDailyStreak, Long> {

    Optional<MemberDailyStreak> findByMemberAndCreatedDate(Member member, LocalDate localDate);
}