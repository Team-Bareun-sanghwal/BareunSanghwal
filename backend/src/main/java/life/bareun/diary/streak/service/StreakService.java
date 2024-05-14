package life.bareun.diary.streak.service;

import java.time.LocalDate;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.streak.dto.response.MemberStreakResDto;
import life.bareun.diary.streak.dto.response.MonthStreakResDto;
import life.bareun.diary.streak.dto.response.StreakRecoveryInfoResDto;

public interface StreakService {

    MemberStreakResDto getMemberStreakResDto();

    MonthStreakResDto getHabitStreakResDtoByMemberHabitId(String dateString, Long memberHabitId);

    MonthStreakResDto getHabitStreakResDtoByMember(String dateString);

    StreakRecoveryInfoResDto getStreakRecoveryInfoResDto(LocalDate date);

    void initialMemberStreak(Member member);

    void initialHabitStreak(MemberHabit memberHabit);

    void createDailyStreak(Member member, LocalDate date);

    void achieveStreak(MemberHabit memberHabit, LocalDate date);

    void deleteHabitStreak(MemberHabit memberHabit);

    void recoveryStreak(LocalDate date);
}
