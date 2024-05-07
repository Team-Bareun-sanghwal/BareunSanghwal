package life.bareun.diary.streak.service;

import java.time.LocalDate;
import java.util.List;
import life.bareun.diary.global.security.util.AuthUtil;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.habit.repository.MemberHabitRepository;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.streak.dto.response.HabitStreakResDto;
import life.bareun.diary.streak.dto.response.MemberStreakResDto;
import life.bareun.diary.streak.entity.HabitDailyStreak;
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
    public HabitStreakResDto getHabitStreakResDtoByMemberHabitId(String dateString, Long memberHabitId) {
        String[] splitDateString = dateString.split("-");

        int year = Integer.parseInt(splitDateString[0]);
        int month = Integer.parseInt(splitDateString[1]);

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);

        return habitStreakService.getHabitStreakResDtoByMemberHabitId(memberHabitId, firstDayOfMonth, lastDayOfMonth);
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

        return habitStreakService.getHabitStreakResDtoByMemberId(member.getId(), firstDayOfMonth, lastDayOfMonth);
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
        List<MemberHabit> memberHabitList = memberHabitRepository.findAllByIsDeletedAndMember(false,
            member);

        for (MemberHabit memberHabit : memberHabitList) {
            habitStreakService.createHabitDailyStreak(memberHabit, date);
        }

        memberStreakService.createMemberDailyStreak(member, date);
    }

    @Override
    public void achieveStreak(MemberHabit memberHabit) {
        HabitDailyStreak habitDailyStreak = habitStreakService.achieveHabitStreak(memberHabit);
        memberStreakService.achieveMemberStreak(getCurrentMember(), habitDailyStreak.getCurrentStreak());
    }

    @Override
    public void recoveryStreak() {

    }

    /**
     * 멤버 엔티티 반환.
     */
    private Member getCurrentMember() {
        return memberRepository.findById(AuthUtil.getMemberIdFromAuthentication())
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }
}
