package life.bareun.diary.streak.service;

import java.time.LocalDate;
import java.util.List;
import life.bareun.diary.global.auth.util.AuthUtil;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.habit.repository.MemberHabitRepository;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.entity.MemberRecovery;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRecoveryRepository;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.streak.dto.response.MemberStreakResDto;
import life.bareun.diary.streak.dto.response.MonthStreakResDto;
import life.bareun.diary.streak.dto.response.StreakRecoveryInfoResDto;
import life.bareun.diary.streak.entity.HabitDailyStreak;
import life.bareun.diary.streak.exception.MemberDailyStreakErrorCode;
import life.bareun.diary.streak.exception.StreakException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StreakServiceImpl implements StreakService {

    private final MemberHabitRepository memberHabitRepository;

    private final MemberStreakService memberStreakService;
    private final HabitStreakService habitStreakService;
    private final MemberRepository memberRepository;
    private final MemberRecoveryRepository memberRecoveryRepository;

    /**
     * 멤버 전체 스트릭을 memberStreakResDto의 형태로 반환.
     */
    @Override
    public MemberStreakResDto getMemberStreakResDto() {
        return memberStreakService.getMemberStreakResDto(getCurrentMember());
    }

    /**
     * 파라메터와 동일한 MemberHabitId를 가지는 해빗 일일 스트릭들을 조회.
     */
    @Override
    public MonthStreakResDto getHabitStreakResDtoByMemberHabitId(String dateString, Long memberHabitId) {
        String[] splitDateString = dateString.split("-");

        int year = Integer.parseInt(splitDateString[0]);
        int month = Integer.parseInt(splitDateString[1]);

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);

        return habitStreakService.getHabitDailyStreakResDtoByMemberHabitId(memberHabitId, firstDayOfMonth,
            lastDayOfMonth);
    }

    /**
     * 피라메터와 동일한 MemberId를 가지는 해빗 일일 스트릭드을 조회.
     */
    @Override
    public MonthStreakResDto getHabitStreakResDtoByMember(String dateString) {
        String[] splitDateString = dateString.split("-");

        int year = Integer.parseInt(splitDateString[0]);
        int month = Integer.parseInt(splitDateString[1]);

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);

        Member member = getCurrentMember();

        return memberStreakService.getMemberDailyStreakResDtoByMemberId(
            member.getId(), firstDayOfMonth, lastDayOfMonth);
    }

    @Override
    public StreakRecoveryInfoResDto getStreakRecoveryInfoResDto(LocalDate date) {

        LocalDate today = LocalDate.now();
        if (!date.isBefore(today)
            || (date.getYear() != today.getYear() || date.getMonthValue() != today.getMonthValue())) {
            throw new StreakException(MemberDailyStreakErrorCode.UNAVAILABLE_TIME_ACCESS);
        }

        LocalDate firstDayOfMonth = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
        LocalDate lastDayOfMonth = LocalDate.now();

        return memberStreakService.getStreakRecoveryInfoResDto(
            getCurrentMember(), date, firstDayOfMonth, lastDayOfMonth);
    }

    @Override
    public void initialMemberStreak(Member member) {
        memberStreakService.createInitialMemberStreak(member);
    }

    @Override
    public void initialHabitStreak(MemberHabit memberHabit) {
        habitStreakService.createInitialHabitStreak(memberHabit);
    }

    @Override
    public void createDailyStreak(Member member, LocalDate date) {
        List<MemberHabit> memberHabitList = memberHabitRepository.findAllByIsDeletedAndMember(false, member);

        for (MemberHabit memberHabit : memberHabitList) {
            habitStreakService.createHabitDailyStreak(memberHabit, date);
        }

        memberStreakService.createMemberDailyStreak(member, date);
    }

    @Override
    public void achieveStreak(MemberHabit memberHabit, LocalDate date) {
        HabitDailyStreak habitDailyStreak = habitStreakService.achieveHabitStreak(memberHabit, date);
        memberStreakService.achieveMemberStreak(memberHabit.getMember(), habitDailyStreak.getCurrentStreak(), date);
    }

    @Override
    public void deleteHabitStreak(MemberHabit memberHabit) {
        habitStreakService.deleteHabitTotalStreak(memberHabit);
        habitStreakService.deleteHabitDailyStreak(memberHabit);
    }

    @Override
    public void recoveryStreak(LocalDate date) {
        /*
         * 리커버리를 사용하려는 날짜가 현재 날짜와 같거나 이후이면 혹은 현재 날짜의 달에 포함되지 않는 경우
         * 해당 날짜에는 리커버리를 사용할 수 없으므로 예외 발생.
         */
        LocalDate today = LocalDate.now();
        if (!date.isBefore(today)
            || (date.getYear() != today.getYear() || date.getMonthValue() != today.getMonthValue())) {
            throw new StreakException(MemberDailyStreakErrorCode.UNAVAILABLE_TIME_ACCESS);
        }

        Member member = getCurrentMember();
        LocalDate firstDate = LocalDate.of(date.getYear(), date.getMonthValue(), 1).minusDays(1);
        memberStreakService.recoveryMemberDailyStreak(member, date); // clear

        int longestStreak = memberStreakService.recoveryMemberDailyStreakCount(member, firstDate, today);
        memberStreakService.recoveryMemberTotalStreak(member, longestStreak);

        useStreakRecovery(member);
    }

    private void useStreakRecovery(Member member) {
        // 무료 있으면 무료 씀
        MemberRecovery memberRecovery = memberRecoveryRepository.findByMember(member)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
            );

        // 무료 리커버리를 사용할 수 있다면
        if (memberRecovery.isFreeRecoveryAvailable()) {
            // 사용하고 함수 종료
            memberRecovery.useFreeRecovery();
            return;
        }

        // 유료 리커버리 사용
        if (member.isPaidRecoveryAvailable()) {
            member.usePaidRecovery();
        } else {
            throw new MemberException(MemberErrorCode.STREAK_RECOVERY_UNAVAILABLE);
        }
    }


    /**
     * 멤버 엔티티 반환.
     */
    private Member getCurrentMember() {
        return memberRepository.findById(AuthUtil.getMemberIdFromAuthentication())
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }
}
