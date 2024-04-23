package life.bareun.diary.streak.service;

import life.bareun.diary.streak.entity.embed.AchieveType;

public interface MemberDailyStreakService {

    void createMemberDailyStreak(int trackerCount, AchieveType achieveType);
}
