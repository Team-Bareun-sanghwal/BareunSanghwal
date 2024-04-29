package life.bareun.diary.streak.service;

import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.streak.dto.response.HabitStreakResDto;
import life.bareun.diary.streak.dto.response.MemberStreakResDto;
import life.bareun.diary.streak.entity.MemberDailyStreak;
import life.bareun.diary.streak.entity.MemberTotalStreak;
import life.bareun.diary.streak.entity.embed.AchieveType;

public interface StreakService {

    MemberStreakResDto getMemberStreakResDto();

    HabitStreakResDto getHabitStreakResDtoByMemberHabitId(String dateString, Long memberHabitId);

    HabitStreakResDto getHabitStreakResDtoByMember(String dateString);

    void createInitialMemberStreak(Member member);

    void modifyMemberTotalStreakTotalField(int trackerCount);

    void modifyMemberTotalStreakAchieveField(boolean streakFlag, boolean starFlag);

    MemberTotalStreak findMemberTotalStreak();

    void createMemberDailyStreak(Member member, int trackerCount, AchieveType achieveType);

    MemberDailyStreak findMemberDailyStreak();

    void modifyMemberDailyStreak();

    void createInitialHabitStreak(MemberHabit memberHabit);
}
