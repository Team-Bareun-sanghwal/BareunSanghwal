package life.bareun.diary.streak.service;

import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.streak.dto.response.HabitStreakResDto;
import life.bareun.diary.streak.dto.response.MemberStreakResDto;

public interface StreakService {

    MemberStreakResDto getMemberStreakResDto();

    HabitStreakResDto getHabitStreakResDtoByMemberHabitId(String dateString, Long memberHabitId);

    HabitStreakResDto getHabitStreakResDtoByMember(String dateString);

    void initialMemberStreak(Member member);

    void initialHabitStreak(MemberHabit memberHabit);
}
