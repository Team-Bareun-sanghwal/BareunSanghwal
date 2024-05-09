package life.bareun.diary.streak.service;

import java.time.LocalDate;
import java.util.Optional;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.streak.dto.response.MemberStreakResDto;
import life.bareun.diary.streak.dto.response.MonthStreakResDto;
import life.bareun.diary.streak.entity.MemberDailyStreak;

public interface MemberStreakService {

    void createInitialMemberStreak(Member member);

    void createMemberDailyStreak(Member member, LocalDate date);

    void achieveMemberStreak(Member member, int currentStreak);

    MemberStreakResDto getMemberStreakResDto(Member member);

    Optional<MemberDailyStreak> findMemberDailyStreak(Member member, LocalDate date);

    void recoveryMemberDailyStreak(Member member, LocalDate date);

    int recoveryMemberDailyStreakCount(Member member, LocalDate startDate, LocalDate endDate);

    void recoveryMemberTotalStreak(Member member, int longestStreak);

    MonthStreakResDto getMemberDailyStreakResDtoByMemberId(Long memberId, LocalDate firstDayOfMonth,
        LocalDate lastDayOfMonth);
}
