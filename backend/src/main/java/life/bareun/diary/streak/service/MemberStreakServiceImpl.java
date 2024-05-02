package life.bareun.diary.streak.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import life.bareun.diary.global.security.util.AuthUtil;
import life.bareun.diary.habit.dto.HabitTrackerCountDto;
import life.bareun.diary.habit.repository.HabitTrackerRepository;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.streak.entity.MemberDailyStreak;
import life.bareun.diary.streak.entity.MemberTotalStreak;
import life.bareun.diary.streak.entity.embed.AchieveType;
import life.bareun.diary.streak.exception.MemberDailyStreakErrorCode;
import life.bareun.diary.streak.exception.MemberTotalStreakErrorCode;
import life.bareun.diary.streak.exception.StreakException;
import life.bareun.diary.streak.repository.MemberDailyStreakRepository;
import life.bareun.diary.streak.repository.MemberTotalStreakRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class MemberStreakServiceImpl implements MemberStreakService {

    private final MemberTotalStreakRepository memberTotalStreakRepository;
    private final MemberDailyStreakRepository memberDailyStreakRepository;
    private final HabitTrackerRepository habitTrackerRepository;
    private final MemberRepository memberRepository;

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

        createMemberDailyStreak(member, AchieveType.NOT_EXISTED, LocalDate.now());
        createMemberDailyStreak(member, AchieveType.NOT_EXISTED, LocalDate.now());
    }

    @Override
    public void createMemberDailyStreak(Member member, AchieveType achieveType, LocalDate date) {
        AtomicReference<AchieveType> achieveTypeToday = new AtomicReference<>(AchieveType.NOT_EXISTED);
        AtomicInteger currentStreakToday = new AtomicInteger(0);

        memberDailyStreakRepository.findByMemberAndCreatedDate(member, date)
            .ifPresent(memberDailyStreak -> {
                throw new StreakException(MemberDailyStreakErrorCode.NOT_FOUND_MEMBER_DAILY_STREAK);
            });

        /*
         * 해당 날짜에 해빗 트래커가 존재하는 개수를 세고 그만큼 totolTrackerCount를, AchieveType을 NOT_ACHIEVE 로 설정한다.
         * 만약 개수가 0이면 그 날 해결해야 할 해빗 트래커가 없으므로 AchieveType을 NOT_EXISTED로 설정한다.
         */
        int totalTrackerCount = Math.toIntExact(habitTrackerRepository.getHabitTrackerCountByMemberAndDate(
            HabitTrackerCountDto.builder()
                .member(member)
                .date(date)
                .build()
        ));

        memberDailyStreakRepository.findByMemberAndCreatedDate(member, date.minusDays(1))
            .ifPresent(memberDailyStreak -> {
                if (!memberDailyStreak.getAchieveType().equals(AchieveType.NOT_ACHIEVE)) {
                    currentStreakToday.set(memberDailyStreak.getCurrentStreak());
                }
            });

        memberDailyStreakRepository.save(
            MemberDailyStreak.builder()
                .member(member)
                .createdDate(date)
                .totalTrackerCount(totalTrackerCount)
                .achieveType(achieveTypeToday.get())
                .currentStreak(currentStreakToday.get())
                .build()
        );
    }

    /**
     * 스케줄러로 호출. 해당 날짜의 트래커가 생성된 뒤에 전체 스트릭 수를 1 만큼 증가. 전체 트래커 수를 trackerCount 만큼 증가.
     */
    @Override
    public void modifyMemberTotalStreakTotalField(int trackerCount) {
        MemberTotalStreak memberTotalStreak = findMemberTotalStreak();

        memberTotalStreak.modifyAchieveStreakCount(memberTotalStreak.getTotalStreakCount() + 1);
        memberTotalStreak.modifyTotalTrackerCount(memberTotalStreak.getTotalTrackerCount() + trackerCount);
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

        memberTotalStreak.modifyAchieveTrackerCount(memberTotalStreak.getAchieveTrackerCount() + 1);
        if (streakFlag) {
            memberTotalStreak.modifyAchieveStreakCount(memberTotalStreak.getAchieveTrackerCount() + 1);
        }

        if (starFlag) {
            memberTotalStreak.modifyStarCount(memberTotalStreak.getStarCount() + 1);
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
     * 멤버 일일 스트릭을 반환.
     */
    @Override
    public Optional<MemberDailyStreak> findMemberDailyStreak(LocalDate date) {
        Member member = getCurrentMember();

        return memberDailyStreakRepository.findByMemberAndCreatedDate(member, date);
    }

    /**
     * 멤버가 트래커를 달성했을 때 호출. 달성 트래커 수를 1만큼 증가. 멤버가 오늘 트래커를 달성한 적이 없으면 현재 스트릭 수를 1만큼 증가.
     */
    @Override
    public void modifyMemberDailyStreak() {
        MemberDailyStreak memberDailyStreak = findMemberDailyStreak(LocalDate.now())
            .orElseThrow(() -> new StreakException(MemberDailyStreakErrorCode.NOT_FOUND_MEMBER_DAILY_STREAK));

        if (memberDailyStreak.getAchieveType().equals(AchieveType.NOT_EXISTED)) {
            // TODO: 전영빈 / NOT_EXISTED 면 트래커가 존재하지 않는다는 것이니까 트레커가 존재하지 않는 다는 익셉션 반환.
            throw new StreakException(MemberDailyStreakErrorCode.NO_TRACKER);
        }

        if (memberDailyStreak.getAchieveTrackerCount() == memberDailyStreak.getTotalTrackerCount()) {
            // TODO: 전영빈 / 이미 달성한 트래커의 개수와 전체 트래커의 개수가 같으면 오늘은 더 이상 달성할 트래커가 존재하지 않는 다는 것.
            // 이 익셉션의 처리 의무를 어디로 주는게 맞을까?
            throw new StreakException(MemberDailyStreakErrorCode.NOT_ENOUGH_TRACKER_TO_ACHIEVE);
        }

        memberDailyStreak.modifyAchieveTrackerCount(memberDailyStreak.getAchieveTrackerCount() + 1);

        boolean streakFlag = false;
        boolean starFlag = false;

        if (memberDailyStreak.getAchieveTrackerCount() == memberDailyStreak.getTotalTrackerCount()) {
            streakFlag = true;
        }

        if (memberDailyStreak.getAchieveType().equals(AchieveType.NOT_ACHIEVE)) {
            starFlag = true;
            memberDailyStreak.modifyCurrentStreak(memberDailyStreak.getCurrentStreak() + 1);
        }

        memberDailyStreak.modifyAchieveType(AchieveType.ACHIEVE);

        modifyMemberTotalStreakAchieveField(streakFlag, starFlag);
    }

    /**
     * 멤버 엔티티 반환.
     */
    private Member getCurrentMember() {
        return memberRepository.findById(AuthUtil.getMemberIdFromAuthentication())
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }
}
