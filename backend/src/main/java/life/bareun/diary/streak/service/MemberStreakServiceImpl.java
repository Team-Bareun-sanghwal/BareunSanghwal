package life.bareun.diary.streak.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import life.bareun.diary.global.auth.util.AuthUtil;
import life.bareun.diary.habit.dto.HabitTrackerCountDto;
import life.bareun.diary.habit.repository.HabitTrackerRepository;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.streak.dto.response.MemberStreakResDto;
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

        createMemberDailyStreak(member, LocalDate.now());
    }

    @Override
    public void createMemberDailyStreak(Member member, LocalDate date) {
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

        if (totalTrackerCount > 0) {
            achieveTypeToday.set(AchieveType.NOT_ACHIEVE);
        }

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


    @Override
    public void achieveMemberStreak(Member member, int currentStreak) {
        LocalDate today = LocalDate.now();
        MemberDailyStreak memberDailyStreakToday = memberDailyStreakRepository
            .findByMemberAndCreatedDate(member, today)
            .orElseThrow(() -> new StreakException(MemberDailyStreakErrorCode.NOT_FOUND_MEMBER_DAILY_STREAK));

        MemberTotalStreak memberTotalStreak = memberTotalStreakRepository.findByMember(member)
            .orElseThrow(() -> new StreakException(MemberTotalStreakErrorCode.NOT_FOUND_MEMBER_TOTAL_STREAK));

        // 이미 별을 획득한 경우, 즉 주어진 해빗을 모두 해결했을 경우
        if (memberDailyStreakToday.isStared()) {
            throw new StreakException(MemberDailyStreakErrorCode.NOT_ENOUGH_TRACKER_TO_ACHIEVE);
        }
        // 트래커가 존재하지 않는 경우
        if (memberDailyStreakToday.getTotalTrackerCount() == 0) {
            throw new StreakException(MemberDailyStreakErrorCode.NO_EXISTED_TRACKER);
        }

        // 아직 오늘 한 번도 완료한 해빗 트래커가 없는 경우
        if (memberDailyStreakToday.getAchieveType().equals(AchieveType.NOT_ACHIEVE)) {
            memberDailyStreakToday.modifyCurrentStreak(memberDailyStreakToday.getCurrentStreak() + 1);
            memberDailyStreakToday.modifyAchieveType(AchieveType.ACHIEVE);
            memberTotalStreak.modifyAchieveStreakCount(memberTotalStreak.getAchieveStreakCount() + 1);
        }

        // 오늘 달성한 트래커 수를 증가시키고 달성 트래커 수 == 전체 트래커 수라면 별 획득
        memberDailyStreakToday.modifyAchieveTrackerCount(memberDailyStreakToday.getAchieveTrackerCount() + 1);
        memberTotalStreak.modifyAchieveTrackerCount(memberTotalStreak.getAchieveTrackerCount() + 1);
        if (memberDailyStreakToday.getAchieveTrackerCount() == memberDailyStreakToday.getTotalTrackerCount()) {
            memberDailyStreakToday.modifyIsStared(true);
            memberTotalStreak.modifyStarCount(memberTotalStreak.getStarCount() + 1);
        }

        // 멤버 전체 스트릭의 최장 스트릭을 갱신
        memberTotalStreak.modifyLongestStreak(
            Math.max(
                memberTotalStreak.getLongestStreak(),
                memberDailyStreakToday.getCurrentStreak()));

        // 만약 내일의 멤버 데일리 스트릭이 존재하면 currentStreak을 이어준다.
        memberDailyStreakRepository.findByMemberAndCreatedDate(member, today.plusDays(1))
            .ifPresent(memberDailyStreakTomorrow -> {
                memberDailyStreakTomorrow.modifyCurrentStreak(memberDailyStreakToday.getCurrentStreak());
            });
    }

    @Override
    public MemberStreakResDto getMemberStreakResDto(Member member) {
        MemberTotalStreak memberTotalStreak = memberTotalStreakRepository.findByMember(member)
            .orElseThrow(() -> new StreakException(MemberTotalStreakErrorCode.NOT_FOUND_MEMBER_TOTAL_STREAK));

        return MemberStreakResDto.builder().totalStreakCount(memberTotalStreak.getTotalStreakCount())
            .achieveStreakCount(memberTotalStreak.getAchieveStreakCount()).starCount(memberTotalStreak.getStarCount())
            .longestStreakCount(memberTotalStreak.getLongestStreak()).build();
    }

    @Override
    public Optional<MemberDailyStreak> findMemberDailyStreak(Member member, LocalDate date) {
        return memberDailyStreakRepository.findByMemberAndCreatedDate(member, date);
    }

    @Override
    public void recoveryMemberDailyStreak(Member member, LocalDate date) {
        MemberDailyStreak memberDailyStreak = memberDailyStreakRepository.findByMemberAndCreatedDate(member, date)
            .orElseThrow(() -> new StreakException(MemberDailyStreakErrorCode.NOT_FOUND_MEMBER_DAILY_STREAK));

        memberDailyStreak.modifyAchieveType(AchieveType.RECOVERY);
    }

    @Override
    public void recoveryMemberDailyStreakCount(Member member, LocalDate startDate, LocalDate endDate) {

    }

    /**
     * 멤버 엔티티 반환.
     */
    private Member getCurrentMember() {
        return memberRepository.findById(AuthUtil.getMemberIdFromAuthentication())
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }
}
