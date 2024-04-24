package life.bareun.diary.streak.service;

import life.bareun.diary.streak.entity.MemberDailyStreak;
import life.bareun.diary.streak.entity.embed.AchieveType;

public interface MemberDailyStreakService {

    void createMemberDailyStreakInit();

    void createMemberDailyStreakSchedule(int trackerCount, AchieveType achieveType);

    MemberDailyStreak findMemberDailyStreak();

    void modifyMemberDailyStreak();
}
