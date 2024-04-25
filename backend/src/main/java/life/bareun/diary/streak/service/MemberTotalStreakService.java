package life.bareun.diary.streak.service;

import life.bareun.diary.streak.dto.response.MemberStreakResponseDto;
import life.bareun.diary.streak.entity.MemberTotalStreak;

public interface MemberTotalStreakService {

    void createMemberTotalStreak();

    MemberTotalStreak findMemberTotalStreak();

    MemberStreakResponseDto getMemberStreakResponseDto();

    void modifyMemberTotalStreakTotalField(int trackCount);

    void modifyMemberTotalStreakAchieveField(boolean streakFlag, boolean starFlag);

    void modifyMemberTotalStreakStar(int streakCount);
}
