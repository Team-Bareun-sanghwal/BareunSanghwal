package life.bareun.diary.streak.repository;

import java.time.LocalDate;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.streak.entity.MemberDailyStreak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDailyStreakRepository extends JpaRepository<MemberDailyStreak, Long> {

    Integer countByIsStaredAndMemberAndCreatedDateBetween(boolean isStared, Member member,
        LocalDate startDate, LocalDate endDate);
}
