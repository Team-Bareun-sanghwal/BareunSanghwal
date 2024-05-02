package life.bareun.diary.streak.service;

import java.time.LocalDate;
import java.util.List;
import life.bareun.diary.global.security.util.AuthUtil;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.streak.dto.StreakInfoByDayDto;
import life.bareun.diary.streak.dto.response.HabitStreakResDto;
import life.bareun.diary.streak.dto.response.MemberStreakResDto;
import life.bareun.diary.streak.entity.MemberTotalStreak;
import life.bareun.diary.streak.repository.HabitDailyStreakRepository;
import life.bareun.diary.streak.repository.HabitTotalStreakRepository;
import life.bareun.diary.streak.repository.MemberDailyStreakRepository;
import life.bareun.diary.streak.repository.MemberTotalStreakRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StreakServiceImpl implements StreakService {

    private final MemberTotalStreakRepository memberTotalStreakRepository;
    private final MemberDailyStreakRepository memberDailyStreakRepository;
    private final HabitTotalStreakRepository habitTotalStreakRepository;
    private final HabitDailyStreakRepository habitDailyStreakRepository;
    private final MemberRepository memberRepository;

    private final MemberStreakService memberStreakService;
    private final HabitStreakService habitStreakService;
    
    /**
     * 멤버 전체 스트릭을 memberStreakResDto의 형태로 반환.
     */
    @Override
    public MemberStreakResDto getMemberStreakResDto() {
        MemberTotalStreak memberTotalStreak = memberStreakService.findMemberTotalStreak();

        return MemberStreakResDto.builder().totalStreakCount(memberTotalStreak.getTotalStreakCount())
            .achieveStreakCount(memberTotalStreak.getAchieveStreakCount()).starCount(memberTotalStreak.getStarCount())
            .longestStreakCount(memberTotalStreak.getLongestStreak()).build();
    }

    /**
     * 파라메터와 동일한 MemberHabitId를 가지는 해빗 일일 스트릭들을 조회.
     */
    @Override
    public HabitStreakResDto getHabitStreakResDtoByMemberHabitId(String dateString, Long memberHabitId) {
        String[] splitDateString = dateString.split("-");

        int year = Integer.parseInt(splitDateString[0]);
        int month = Integer.parseInt(splitDateString[1]);

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);

        List<StreakInfoByDayDto> streakDayInfoList = habitDailyStreakRepository
            .findStreakDayInfoByMemberHabitId(firstDayOfMonth, lastDayOfMonth, memberHabitId);

        int achieveCount = 0;
        int totalCount = 0;
        for (StreakInfoByDayDto dto : streakDayInfoList) {
            achieveCount += dto.achieveCount();
            totalCount += dto.totalCount();
        }

        return HabitStreakResDto.builder()
            .achieveProportion(getProportion(achieveCount, totalCount))
            .dayOfWeekFirst(firstDayOfMonth.getDayOfWeek().getValue() - 1)
            .dayInfo(habitDailyStreakRepository.findStreakDayInfoByMemberHabitId(firstDayOfMonth, lastDayOfMonth,
                memberHabitId))
            .build();
    }

    /**
     * 피라메터와 동일한 MemberId를 가지는 해빗 일일 스트릭드을 조회.
     */
    @Override
    public HabitStreakResDto getHabitStreakResDtoByMember(String dateString) {
        String[] splitDateString = dateString.split("-");

        int year = Integer.parseInt(splitDateString[0]);
        int month = Integer.parseInt(splitDateString[1]);

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);

        Member member = getCurrentMember();

        List<StreakInfoByDayDto> streakDayInfoList = habitDailyStreakRepository
            .findStreakDayInfoByMemberId(firstDayOfMonth, lastDayOfMonth, member.getId());

        int achieveCount = 0;
        int totalCount = 0;
        for (StreakInfoByDayDto dto : streakDayInfoList) {
            achieveCount += dto.achieveCount();
            totalCount += dto.totalCount();
        }

        return HabitStreakResDto.builder()
            .achieveProportion(getProportion(achieveCount, totalCount))
            .dayOfWeekFirst(firstDayOfMonth.getDayOfWeek().getValue() - 1)
            .dayInfo(habitDailyStreakRepository.findStreakDayInfoByMemberId(firstDayOfMonth, lastDayOfMonth,
                member.getId()))
            .build();
    }

    @Override
    public void initialMemberStreak(Member member) {
        memberStreakService.createInitialMemberStreak(member);
    }

    @Override
    public void initialHabitStreak(MemberHabit memberHabit) {
        habitStreakService.createInitialHabitStreak(memberHabit);
    }

    /**
     * 멤버 엔티티 반환.
     */
    private Member getCurrentMember() {
        return memberRepository.findById(AuthUtil.getMemberIdFromAuthentication())
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }

    private double getProportion(int achieveCount, int totalCount) {
        return Math.round((double) achieveCount / (double) totalCount * 100.0);
    }
}
