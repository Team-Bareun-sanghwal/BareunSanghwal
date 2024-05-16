package life.bareun.diary.streak.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.streak.entity.MemberDailyStreak;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDailyStreakRepository extends JpaRepository<MemberDailyStreak, Long>,
    MemberDailyStreakRepositoryCustom {

    Optional<MemberDailyStreak> findByMemberAndCreatedDate(Member member, LocalDate localDate);

    int countByIsStaredAndMemberAndCreatedDateBetween(Boolean isStared, Member member,
        LocalDate startDate, LocalDate endDate);

    List<MemberDailyStreak> findByMemberAndCreatedDateBetweenOrderByCreatedDate(Member member, LocalDate startDate,
        LocalDate endDate);
    
    List<MemberDailyStreak> findByMemberAndCreatedDateBeforeOrderByCreatedDateDesc(Member member, LocalDate date);
}
