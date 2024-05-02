package life.bareun.diary.streak.service;

import java.time.LocalDate;
import java.util.List;
import life.bareun.diary.global.auth.util.AuthUtil;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.streak.dto.StreakInfoByDayDto;
import life.bareun.diary.streak.dto.response.HabitStreakResDto;
import life.bareun.diary.streak.dto.response.MemberStreakResDto;
import life.bareun.diary.streak.entity.HabitDailyStreak;
import life.bareun.diary.streak.entity.HabitTotalStreak;
import life.bareun.diary.streak.entity.MemberDailyStreak;
import life.bareun.diary.streak.entity.MemberTotalStreak;
import life.bareun.diary.streak.entity.embed.AchieveType;
import life.bareun.diary.streak.exception.MemberDailyStreakErrorCode;
import life.bareun.diary.streak.exception.MemberTotalStreakErrorCode;
import life.bareun.diary.streak.exception.StreakException;
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

    /**
     * 멤버 전체 스트릭을 memberStreakResDto의 형태로 반환.
     */
    @Override
    public MemberStreakResDto getMemberStreakResDto() {
        MemberTotalStreak memberTotalStreak = findMemberTotalStreak();

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

    /**
     * 초기 회원 가입 시에 호출. 멤버 전체 스트릭과 해당 날짜의 멤버 일일 스트릭을 생성.
     */
    @Override
    public void createInitialMemberStreak(Member member) {
        memberTotalStreakRepository.save(
            MemberTotalStreak
                .builder()
                .member(member)
                .build()
        );

        memberDailyStreakRepository.save(
            MemberDailyStreak.builder()
                .member(member)
                .createdDate(LocalDate.now())
                .totalTrackerCount(0)
                .achieveType(AchieveType.NOT_EXISTED)
                .currentStreak(0)
                .build()
        );
    }

    /**
     * 스케줄러로 호출. 해당 날짜의 트래커가 생성된 뒤에 전체 스트릭 수를 1만큼 증가. 전체 트래커 수를 생성된 트래커 수만큼 증가.
     */
    @Override
    public void modifyMemberTotalStreakTotalField(int trackerCount) {
        MemberTotalStreak memberTotalStreak = findMemberTotalStreak();

        memberTotalStreak.increaseTotalStreakCountByOne();
        memberTotalStreak.increaseTotalTrackerCount(trackerCount);
    }

    /**
     * 멤버가 트래커를 달성했을 때 호출. 달성한 트래커 개수를 1만큼 증가. 멤버 일일 스트릭의 achieveType이 NOT_ACHIEVE이면 달성 스트릭을 1만큼 증가. starFlag가 true면 별
     * 개수를 1만큼 증가.
     * <p>
     * TODO: 전영빈 // 외부에서 호출될 이유가 없으므로 상속을 포기하고 private로 돌릴 지 고민 중.
     */
    @Override
    public void modifyMemberTotalStreakAchieveField(boolean streakFlag, boolean starFlag) {
        MemberTotalStreak memberTotalStreak = findMemberTotalStreak();

        memberTotalStreak.increaseAchieveTrackerCountByOne();
        if (streakFlag) {
            memberTotalStreak.increaseAchieveStreakCountByOne();
        }

        if (starFlag) {
            memberTotalStreak.addStarCount();
        }
    }

    /**
     * 멤버 전체 스트릭을 반환.
     */
    @Override
    @Transactional(readOnly = true)
    public MemberTotalStreak findMemberTotalStreak() {
        Member member = getCurrentMember();

        return memberTotalStreakRepository.findByMember(member)
            .orElseThrow(() ->
                new StreakException(MemberTotalStreakErrorCode.NOT_FOUND_MEMBER_TOTAL_STREAK));
    }
    
    /**
     * 스케줄러로 실행. 다음 날짜의 멤버 일일 스트릭을 생성.
     * <p>
     * TODO: 전영빈 // 스케줄러를 11시에 돌려서 다음날 것을 미리 만든다고하면 11시 30분에 사용자가 해빗을 완료하면 어떡하지?
     */
    @Override
    public void createMemberDailyStreak(Member member, int trackerCount, AchieveType achieveType) {
        MemberDailyStreak memberDailyStreakToday = memberDailyStreakRepository.findByMemberAndCreatedDate(member,
                LocalDate.now())
            .orElseThrow(() -> new StreakException(MemberDailyStreakErrorCode.NOT_FOUND_MEMBER_DAILY_STREAK_TODAY));

        memberDailyStreakRepository.findByMemberAndCreatedDate(member, LocalDate.now().plusDays(1))
            .ifPresentOrElse(memberDailyStreakTomorrow -> {
                throw new StreakException(MemberDailyStreakErrorCode.ALREADY_EXISTED_MEMBER_DAILY_STREAK_TOMORROW);
            }, () -> {
                memberDailyStreakRepository.save(MemberDailyStreak.builder()
                    .createdDate(LocalDate.now().plusDays(1))
                    .totalTrackerCount(trackerCount)
                    .achieveType(achieveType)
                    .currentStreak(memberDailyStreakToday.getCurrentStreak())
                    .build()
                );
            });
    }

    /**
     * 멤버 일일 스트릭을 반환.
     */
    @Override
    public MemberDailyStreak findMemberDailyStreak() {
        Member member = getCurrentMember();

        return memberDailyStreakRepository.findByMemberAndCreatedDate(member, LocalDate.now())
            .orElseThrow(() -> new StreakException(MemberDailyStreakErrorCode.NOT_FOUND_MEMBER_DAILY_STREAK_TODAY));
    }

    /**
     * 멤버가 트래커를 달성했을 때 호출. 달성 트래커 수를 1만큼 증가. 멤버가 오늘 트래커를 달성한 적이 없으면 현재 스트릭 수를 1만큼 증가.
     */
    @Override
    public void modifyMemberDailyStreak() {
        MemberDailyStreak memberDailyStreak = findMemberDailyStreak();

        memberDailyStreak.increaseAchieveTrackerCountByOne();

        boolean streakFlag = false;
        boolean starFlag = false;

        if (memberDailyStreak.getAchieveTrackerCount() == memberDailyStreak.getTotalTrackerCount()) {
            streakFlag = true;
            memberDailyStreak.changeAchieveTypeByAchieve();
        }

        if (memberDailyStreak.getAchieveType().equals(AchieveType.NOT_ACHIEVE)) {
            starFlag = true;
            memberDailyStreak.changeAchieveTypeByAchieve();
            memberDailyStreak.increaseCurrentStreakByOne();
        }

        modifyMemberTotalStreakAchieveField(streakFlag, starFlag);
    }

    /**
     * 멤버가 해빗을 생성할 때 호출. 멤버해빗 전체 스트릭과 일일 스트릭을 생성.
     */
    @Override
    public void createInitialHabitStreak(MemberHabit memberHabit) {
        habitTotalStreakRepository.save(
            HabitTotalStreak.builder()
                .memberHabit(memberHabit)
                .build()
        );

        habitDailyStreakRepository.save(
            HabitDailyStreak.builder()
                .memberHabit(memberHabit)
                .achieveType(AchieveType.NOT_EXISTED)
                .currentStreak(0)
                .createdDate(LocalDate.now())
                .build()
        );
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
