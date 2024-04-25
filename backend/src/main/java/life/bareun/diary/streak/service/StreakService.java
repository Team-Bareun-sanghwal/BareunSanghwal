package life.bareun.diary.streak.service;

import life.bareun.diary.member.entity.Member;
import life.bareun.diary.streak.dto.response.MemberStreakResDto;
import life.bareun.diary.streak.entity.MemberDailyStreak;
import life.bareun.diary.streak.entity.MemberTotalStreak;
import life.bareun.diary.streak.entity.embed.AchieveType;

public interface StreakService {

    void createInitialMemberStreak(Member member);

    void modifyMemberTotalStreakTotalField(int trackerCount);

    void modifyMemberTotalStreakAchieveField(boolean streakFlag, boolean starFlag);

    MemberTotalStreak findMemberTotalStreak();

    MemberStreakResDto getMemberStreakResDto();

    void createMemberDailyStreak(Member member, int trackerCount, AchieveType achieveType);

    MemberDailyStreak findMemberDailyStreak();

    void modifyMemberDailyStreak();
}
