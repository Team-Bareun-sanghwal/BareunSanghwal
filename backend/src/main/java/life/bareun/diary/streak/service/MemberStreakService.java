package life.bareun.diary.streak.service;

import java.time.LocalDate;
import java.util.Optional;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.streak.entity.MemberDailyStreak;
import life.bareun.diary.streak.entity.MemberTotalStreak;
import life.bareun.diary.streak.entity.embed.AchieveType;

public interface MemberStreakService {

    void createInitialMemberStreak(Member member);

    void createMemberDailyStreak(Member member, AchieveType achieveType, LocalDate date);

    void modifyMemberTotalStreakTotalField(int trackerCount);

    void modifyMemberTotalStreakAchieveField(boolean streakFlag, boolean starFlag);

    MemberTotalStreak findMemberTotalStreak();

    Optional<MemberDailyStreak> findMemberDailyStreak(LocalDate date);

    void modifyMemberDailyStreak();
}
