package life.bareun.diary.member.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import life.bareun.diary.global.auth.embed.MemberStatus;
import life.bareun.diary.global.auth.embed.OAuth2Provider;
import life.bareun.diary.global.auth.exception.AuthException;
import life.bareun.diary.global.auth.exception.SecurityErrorCode;
import life.bareun.diary.global.auth.principal.MemberPrincipal;
import life.bareun.diary.global.auth.service.AuthTokenService;
import life.bareun.diary.global.auth.token.AuthToken;
import life.bareun.diary.global.auth.token.AuthTokenProvider;
import life.bareun.diary.global.auth.util.AuthUtil;
import life.bareun.diary.habit.dto.response.HabitTrackerWeekResDto;
import life.bareun.diary.habit.repository.HabitTrackerRepository;
import life.bareun.diary.habit.repository.MemberHabitRepository;
import life.bareun.diary.habit.service.HabitTrackerService;
import life.bareun.diary.member.dto.MemberHabitTrackerDto;
import life.bareun.diary.member.dto.MemberHabitsDto;
import life.bareun.diary.member.dto.MemberPracticeCountPerDayOfWeekDto;
import life.bareun.diary.member.dto.MemberPracticeCountPerHourDto;
import life.bareun.diary.member.dto.MemberPracticedHabitDto;
import life.bareun.diary.member.dto.embed.DayOfWeek;
import life.bareun.diary.member.dto.request.MemberUpdateReqDto;
import life.bareun.diary.member.dto.response.MemberHabitTrackersResDto;
import life.bareun.diary.member.dto.response.MemberHabitsResDto;
import life.bareun.diary.member.dto.response.MemberInfoResDto;
import life.bareun.diary.member.dto.response.MemberLongestStreakResDto;
import life.bareun.diary.member.dto.response.MemberPointResDto;
import life.bareun.diary.member.dto.response.MemberStatisticResDto;
import life.bareun.diary.member.dto.response.MemberStreakColorResDto;
import life.bareun.diary.member.dto.response.MemberStreakRecoveryCountResDto;
import life.bareun.diary.member.dto.response.MemberTreeColorResDto;
import life.bareun.diary.member.dto.response.MemberTreePointResDto;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.entity.MemberRecovery;
import life.bareun.diary.member.entity.Tree;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.mapper.MemberMapper;
import life.bareun.diary.member.repository.MemberRecoveryRepository;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.product.exception.ProductErrorCode;
import life.bareun.diary.product.exception.ProductException;
import life.bareun.diary.product.repository.StreakColorRepository;
import life.bareun.diary.product.repository.TreeColorRepository;
import life.bareun.diary.streak.dto.response.MemberStreakResDto;
import life.bareun.diary.streak.service.StreakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private static final int COLOR_INDEX_MAX = 2;
    private static final int COLOR_INDEX_DEFAULT = 1;
    private static final int COLOR_INDEX_MIN = 0;

    private static final SecureRandom RANDOM = new SecureRandom();

    private final AuthTokenProvider authTokenProvider;
    private final AuthTokenService authTokenService;
    private final StreakService streakService;
    private final HabitTrackerService habitTrackerService;

    private final MemberRepository memberRepository;
    private final MemberRecoveryRepository memberRecoveryRepository;
    private final StreakColorRepository streakColorRepository;
    private final TreeColorRepository treeColorRepository;
    private final HabitTrackerRepository habitTrackerRepository;
    private final MemberHabitRepository memberHabitRepository;

    @Override
    @Transactional
    public MemberPrincipal loginOrRegister(String sub, OAuth2Provider oAuth2Provider) {
        // AtomicBoolean isNewMember = new AtomicBoolean(false);
        AtomicReference<MemberStatus> memberStatus = new AtomicReference<>();

        Member member = memberRepository.findBySub(sub)
            .orElseGet(
                // 신규회원
                () -> {
                    memberStatus.set(MemberStatus.NEW);
                    Member savedMember = memberRepository.save(
                        Member.create(sub, oAuth2Provider)
                    );
                    memberRecoveryRepository.save(
                        MemberRecovery.create(savedMember)
                    );

                    streakService.createInitialMemberStreak(savedMember);
                    return savedMember;
                }
            );

        // orElseGet에서 return되지 않았음 -> 신규회원이 아님
        // 해당 회원의 정보가 입력되었는지 검증한다.
        memberStatus.set(
            isNullMember(member) ? MemberStatus.NUL : MemberStatus.OLD
        );

        return new MemberPrincipal(
            member.getId(),
            member.getRole(),
            member.getProvider(),
            memberStatus.get()
        );
    }

    private boolean isNullMember(Member member) {
        boolean isNickNameNull = (member.getNickname() == null);
        boolean isBirthNull = (member.getBirth() == null);
        boolean isGenderNull = (member.getGender() == null);
        boolean isJobNull = (member.getJob() == null);

        return isNickNameNull || isBirthNull || isGenderNull || isJobNull;
    }

    @Override
    public void logout(String refreshToken) {
        AuthToken refreshAuthToken = authTokenProvider.tokenToAuthToken(refreshToken);
        Long id = AuthUtil.getMemberIdFromAuthentication();
        Long targetId = authTokenProvider.getMemberIdFromToken(refreshAuthToken);

        // Access token과 refresh token의 사용자 정보가 다르면 예외 발생
        if (!id.equals(targetId)) {
            throw new AuthException(SecurityErrorCode.UNMATCHED_AUTHENTICATION);
        }

        authTokenService.revoke(id, refreshToken);
    }

    @Override
    @Transactional
    public void update(MemberUpdateReqDto memberUpdateReqDto) {
        Long id = AuthUtil.getMemberIdFromAuthentication();

        Member member = memberRepository.findById(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER)
            );

        member.update(memberUpdateReqDto);
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void delete() {
        Long id = AuthUtil.getMemberIdFromAuthentication();

        // 제약조건으로 인해 member_recovery를 먼저 삭제해야 한다.
        memberRecoveryRepository.deleteByMemberId(id);
        memberRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberInfoResDto info() {
        Long id = AuthUtil.getMemberIdFromAuthentication();
        Member member = memberRepository.findById(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
            );

        return MemberMapper.INSTANCE.toMemberInfoRes(member);
    }

    @Override
    public MemberStreakColorResDto streakColor() {
        Long id = AuthUtil.getMemberIdFromAuthentication();
        Member member = memberRepository.findById(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
            );

        String streakColorName = streakColorRepository.findById(
                member.getCurrentStreakColorId()
            )
            .orElseThrow(
                () -> new ProductException(ProductErrorCode.NO_SUCH_STREAK_COLOR)
            )
            .getName();

        return new MemberStreakColorResDto(streakColorName);
    }

    @Override
    public MemberTreeColorResDto treeColor() {
        Long id = AuthUtil.getMemberIdFromAuthentication();
        Member member = memberRepository.findById(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
            );

        String treeColorName = treeColorRepository.findById(
                member.getCurrentStreakColorId()
            )
            .orElseThrow(
                () -> new ProductException(ProductErrorCode.NO_SUCH_STREAK_COLOR)
            )
            .getName();

        return new MemberTreeColorResDto(treeColorName);
    }

    @Override
    @Transactional
    public void grantFreeRecoveryToAllMembers() {
        // 회원 탈퇴 시 MemberRecovery가 먼저 삭제되므로
        // memberRecovery의 memberId가 Member 테이블에 있는지 확인할 필요가 없다.
        List<MemberRecovery> memberRecoveries = memberRecoveryRepository.findAll();

        for (MemberRecovery memberRecovery : memberRecoveries) {
            memberRecovery.sendFreeRecovery();
            memberRecoveryRepository.save(memberRecovery);
        }
    }

    @Override
    public MemberPointResDto point() {
        Long id = AuthUtil.getMemberIdFromAuthentication();
        Member member = memberRepository.findById(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
            );

        return new MemberPointResDto(member.getPoint());
    }

    @Override
    public MemberLongestStreakResDto longestStreak() {
        int longestStreak = streakService.getMemberStreakResDto().longestStreakCount();
        return new MemberLongestStreakResDto(longestStreak);
    }

    @Override
    public MemberStreakRecoveryCountResDto streakRecoveryCount() {
        Long id = AuthUtil.getMemberIdFromAuthentication();

        Integer freeRecoveryCount = memberRecoveryRepository.findByMemberId(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
            ).getFreeRecoveryCount();

        Integer paidRecoveryCount = memberRepository.findById(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
            ).getPaidRecoveryCount();

        return new MemberStreakRecoveryCountResDto(
            freeRecoveryCount + paidRecoveryCount,
            freeRecoveryCount
        );
    }

    @Override
    public MemberHabitsResDto habits() {
        Long id = AuthUtil.getMemberIdFromAuthentication();
        List<MemberHabitsDto> memberHabits = memberHabitRepository
            .findAllByMemberIdOrderByCreatedDatetime(id);

        return new MemberHabitsResDto(
            memberHabits
        );
    }

    @Override
    @Transactional(readOnly = true)
    public MemberStatisticResDto statistic() {
        Long memberId = AuthUtil.getMemberIdFromAuthentication();
        // 수행 횟수 기준 상위 5개, 나머지는 기타로 합치기
        // 내림차순 정렬된 데이터
        List<MemberPracticedHabitDto> topHabits = processTopHabits(
            habitTrackerRepository.findTopHabits(memberId),
            memberId
        );
        String maxPracticedHabit = topHabits.get(0).habit();

        // 요일 별 달성 횟수
        // 최대 또는 최대값 중복 허용
        List<MemberPracticeCountPerDayOfWeekDto> dataPerDayOfWeek
            = practiceCountPerDayOfWeek();

        // 4 (0~24시까지 1시간 단위로 24개)
        List<MemberPracticeCountPerHourDto> practiceCountPerHour
            = habitTrackerRepository.countPracticedHabitsPerHour(memberId);
        practiceCountPerHour = processPracticeCountPerHour(practiceCountPerHour);

        LocalDate createdAt = memberRepository.findById(memberId).orElseThrow(
            () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
        ).getCreatedDateTime().toLocalDate();
        LocalDate now = LocalDate.now();

        int totalDays = (int) ChronoUnit.DAYS.between(createdAt, now);
        MemberStreakResDto memberStreakDto = streakService.getMemberStreakResDto();
        int streakDays = memberStreakDto.achieveStreakCount();
        int starredDays = memberStreakDto.starCount();
        int longestStreak = memberStreakDto.longestStreakCount();

        return MemberStatisticResDto.builder()
            .practicedHabitsTop(topHabits)
            .maxPracticedHabit(maxPracticedHabit)
            .practiceCountsPerDayOfWeek(dataPerDayOfWeek)
            .practiceCountsPerHour(practiceCountPerHour)
            .totalDays(totalDays)
            .streakDays(streakDays)
            .starredDays(starredDays)
            .longestStreak(longestStreak)
            .build();
    }

    @Transactional(readOnly = true)
    protected List<MemberPracticedHabitDto> processTopHabits(
        List<MemberPracticedHabitDto> topHabits,
        Long memberId
    ) {
        long counts = habitTrackerRepository.countByMemberId(memberId);

        if (counts > topHabits.size()) {
            long sum = topHabits.stream()
                .mapToLong(MemberPracticedHabitDto::value)
                .sum();
            long etcValue = counts - sum;

            topHabits.add(
                new MemberPracticedHabitDto(
                    "기타",
                    etcValue
                )
            );
        }

        return topHabits;
    }

    @Transactional(readOnly = true)
    protected List<MemberPracticeCountPerDayOfWeekDto> practiceCountPerDayOfWeek() {
        HabitTrackerWeekResDto weekly = habitTrackerService.findAllWeekHabitTracker();
        List<Integer> weeklyValues = Arrays.asList(
            weekly.monday(), weekly.tuesday(), weekly.wednesday(),
            weekly.thursday(), weekly.friday(),
            weekly.saturday(), weekly.sunday()
        );
        int maxValue = weeklyValues.stream().max(Integer::compare).get();
        int minValue = weeklyValues.stream().min(Integer::compare).get();

        List<MemberPracticeCountPerDayOfWeekDto> practiceCountsPerDayOfWeek = new ArrayList<>(7);
        for (int i = 0; i < weeklyValues.size(); ++i) {
            int val = weeklyValues.get(i);
            int colorIndex;
            if (val == minValue) {
                colorIndex = COLOR_INDEX_MIN;
            } else if (val == maxValue) {
                colorIndex = COLOR_INDEX_MAX;
            } else {
                colorIndex = COLOR_INDEX_DEFAULT;
            }
            practiceCountsPerDayOfWeek.add(
                new MemberPracticeCountPerDayOfWeekDto(
                    DayOfWeek.getValueByIndex(i),
                    val,
                    colorIndex
                )
            );
        }

        return practiceCountsPerDayOfWeek;
    }


    private List<MemberPracticeCountPerHourDto> processPracticeCountPerHour(
        List<MemberPracticeCountPerHourDto> practiceCountsPerHour
    ) {
        Map<Integer, Integer> memberPracticeCountPerHourMap = new HashMap<>();
        for (MemberPracticeCountPerHourDto data : practiceCountsPerHour) {
            memberPracticeCountPerHourMap.put(data.time(), data.value());
        }

        List<MemberPracticeCountPerHourDto> processedPracticeCountsPerHour = new ArrayList<>();
        Set<Integer> times = memberPracticeCountPerHourMap.keySet();
        for (int i = 0; i < 24; ++i) {
            MemberPracticeCountPerHourDto processedMemberPracticeCountPerHour = new MemberPracticeCountPerHourDto(
                i,
                (times.contains(i)) ? memberPracticeCountPerHourMap.get(i) : 0
            );
            processedPracticeCountsPerHour.add(processedMemberPracticeCountPerHour);
        }

        return processedPracticeCountsPerHour;
    }

    @Override
    public MemberTreePointResDto treePoint() {
        Long id = AuthUtil.getMemberIdFromAuthentication();
        Member member = memberRepository.findById(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
            );
        Tree tree = member.getTree();
        int point = RANDOM.nextInt(tree.getRangeFrom(), tree.getRangeTo()) + 1;

        member.addPoint(point);
        System.out.println("point: " + member.getPoint());
        memberRepository.save(member);

        return new MemberTreePointResDto(point);
    }

    @Override
    public MemberHabitTrackersResDto habitTrackers(String memberHabitId) {
        Long memberId = AuthUtil.getMemberIdFromAuthentication();
        Long longMemberHabitId = Long.parseLong(memberHabitId);

        List<Integer> yearList = habitTrackerRepository.findAllCreatedYear(
            memberId,
            longMemberHabitId
        );
        List<MemberHabitTrackerDto> habitTrackerGroupList = new ArrayList<>();
        for (Integer year : yearList) {
            habitTrackerGroupList.add(
                habitTrackerRepository.findAllHabitTrackerId(
                    year,
                    memberId,
                    longMemberHabitId
                )
            );
        }

        return new MemberHabitTrackersResDto(
            habitTrackerGroupList,
            yearList
        );
    }
}
