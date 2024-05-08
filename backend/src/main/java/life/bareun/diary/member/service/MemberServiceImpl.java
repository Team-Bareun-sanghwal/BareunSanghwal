package life.bareun.diary.member.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
import life.bareun.diary.habit.dto.response.HabitPracticeCountPerDayOfWeekDto;
import life.bareun.diary.habit.repository.HabitTrackerRepository;
import life.bareun.diary.habit.repository.MemberHabitRepository;
import life.bareun.diary.member.dto.MemberHabitListElementDto;
import life.bareun.diary.member.dto.MemberHabitTrackerDto;
import life.bareun.diary.member.dto.MemberPracticeCountPerDayOfWeekDto;
import life.bareun.diary.member.dto.MemberPracticeCountPerHourDto;
import life.bareun.diary.member.dto.MemberRegisterDto;
import life.bareun.diary.member.dto.MemberTopHabitDto;
import life.bareun.diary.member.dto.embed.DayOfWeek;
import life.bareun.diary.member.dto.request.MemberUpdateReqDto;
import life.bareun.diary.member.dto.response.MemberDailyPhraseResDto;
import life.bareun.diary.member.dto.response.MemberHabitListResDto;
import life.bareun.diary.member.dto.response.MemberHabitTrackersResDto;
import life.bareun.diary.member.dto.response.MemberInfoResDto;
import life.bareun.diary.member.dto.response.MemberLongestStreakResDto;
import life.bareun.diary.member.dto.response.MemberPointResDto;
import life.bareun.diary.member.dto.response.MemberStatisticResDto;
import life.bareun.diary.member.dto.response.MemberStreakColorResDto;
import life.bareun.diary.member.dto.response.MemberStreakRecoveryCountResDto;
import life.bareun.diary.member.dto.response.MemberTreeColorResDto;
import life.bareun.diary.member.dto.response.MemberTreePointResDto;
import life.bareun.diary.member.entity.DailyPhrase;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.entity.MemberDailyPhrase;
import life.bareun.diary.member.entity.MemberRecovery;
import life.bareun.diary.member.entity.Tree;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.mapper.MemberMapper;
import life.bareun.diary.member.repository.DailyPhraseRepository;
import life.bareun.diary.member.repository.MemberDailyPhraseRepository;
import life.bareun.diary.member.repository.MemberRecoveryRepository;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.member.repository.TreeRepository;
import life.bareun.diary.product.entity.StreakColor;
import life.bareun.diary.product.entity.TreeColor;
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

    private static final Long DEFAULT_TREE_ID = 1L;
    private static final String DEFAULT_STREAK_COLOR_NAME = "bareun_sanghwal";
    private static final String DEFAULT_TREE_COLOR_NAME = "green";


    private static final SecureRandom RANDOM = new SecureRandom();

    private final AuthTokenProvider authTokenProvider;
    private final AuthTokenService authTokenService;
    private final StreakService streakService;

    private final MemberRepository memberRepository;
    private final MemberRecoveryRepository memberRecoveryRepository;
    private final StreakColorRepository streakColorRepository;
    private final TreeColorRepository treeColorRepository;
    private final HabitTrackerRepository habitTrackerRepository;
    private final MemberHabitRepository memberHabitRepository;
    private final MemberDailyPhraseRepository memberDailyPhraseRepository;
    private final TreeRepository treeRepository;
    private final DailyPhraseRepository dailyPhraseRepository;

    @Transactional(readOnly = true)
    protected Member getCurrentMember() {
        Long id = AuthUtil.getMemberIdFromAuthentication();

        return memberRepository.findById(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
            );
    }


    @Override
    @Transactional
    public MemberPrincipal loginOrRegister(String sub, OAuth2Provider oAuth2Provider) {
        AtomicReference<MemberStatus> memberStatus = new AtomicReference<>();

        Member member = memberRepository.findBySub(sub)
            .orElseGet(
                // 신규회원
                () -> {
                    memberStatus.set(MemberStatus.NEW);

                    Member newMember = Member.create(
                        MemberRegisterDto.builder()
                            .sub(sub)
                            .oAuth2Provider(oAuth2Provider)
                            .defaultTree(getDefaultTree())
                            .defaultStreakColorId(getDefaultStreakColorId())
                            .defaultTreeColorId(getDefaultTreeColorId())
                            .build()
                    );

                    // 신규 사용자의 리커버리 현황 데이터 생성
                    Member savedMember = memberRepository.save(newMember);
                    memberRecoveryRepository.save(
                        MemberRecovery.create(savedMember)
                    );

                    // 신규 사용자의 오늘의 문구 데이터 생성
                    long dailyPhraseCount = dailyPhraseRepository.count();
                    long dailyPhraseId = RANDOM.nextLong(dailyPhraseCount) + 1L;
                    DailyPhrase initDailyPhrase = dailyPhraseRepository.findById(dailyPhraseId)
                        .orElseThrow(
                            () -> new MemberException(MemberErrorCode.NO_SUCH_DAILY_PHRASE)
                        );
                    memberDailyPhraseRepository.save(
                        MemberDailyPhrase.create(newMember, initDailyPhrase)
                    );

                    streakService.initialMemberStreak(savedMember);
                    return savedMember;
                }
            );

        // orElseGet()이 호출되지 않음 -> findBySub로 찾아짐
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

    private Tree getDefaultTree() {
        return treeRepository.findById(DEFAULT_TREE_ID)
            .orElseGet(
                () -> {
                    List<Tree> treeList = treeRepository.findAll();
                    if (!treeList.isEmpty()) {
                        return treeList.get(0);
                    } else {
                        throw new MemberException(MemberErrorCode.NO_INITIAL_DATA_TREE);
                    }
                }
            );
    }

    private Integer getDefaultStreakColorId() {
        return streakColorRepository.findByName(DEFAULT_STREAK_COLOR_NAME)
            .orElseGet(
                () -> {
                    List<StreakColor> streakColorList = streakColorRepository.findAll();
                    if (!streakColorList.isEmpty()) {
                        return streakColorList.get(0);
                    } else {
                        throw new MemberException(MemberErrorCode.NO_INITIAL_DATA_STREAK_COLOR);
                    }
                }
            )
            .getId();
    }

    private Integer getDefaultTreeColorId() {
        return treeColorRepository.findByName(DEFAULT_TREE_COLOR_NAME)
            .orElseGet(
                () -> {
                    List<TreeColor> treeColorList = treeColorRepository.findAll();
                    if (!treeColorList.isEmpty()) {
                        return treeColorList.get(0);
                    } else {
                        throw new MemberException(MemberErrorCode.NO_INITIAL_DATA_TREE_COLOR);
                    }
                }
            )
            .getId();
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
        Member member = getCurrentMember();
        member.update(memberUpdateReqDto);
        // memberRepository.save(member);
    }

    @Override
    @Transactional
    public void delete() {
        getCurrentMember().delete();
    }

    @Override
    @Transactional(readOnly = true)
    public MemberInfoResDto info() {
        return MemberMapper.INSTANCE.toMemberInfoRes(getCurrentMember());
    }

    @Override
    @Transactional(readOnly = true)
    public MemberStreakColorResDto streakColor() {
        Member member = getCurrentMember();
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
    @Transactional(readOnly = true)
    public MemberTreeColorResDto treeColor() {
        Member member = getCurrentMember();

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
    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    // 월마다 수행되는 메소드
    @Transactional
    public void initStreakRecoveryForAllMembersMonthly() {
        // 회원 탈퇴 시 MemberRecovery가 먼저 삭제되므로
        // memberRecovery의 memberId가 Member 테이블에 있는지 확인할 필요가 없다.
        List<MemberRecovery> memberRecoveries = memberRecoveryRepository.findAll();

        for (MemberRecovery memberRecovery : memberRecoveries) {
            // 무료 리커버리를 발급하고
            memberRecovery.grantFreeRecovery();
            // 리커버리 가격을 초기화한다.
            memberRecovery.initRecoveryPrice();
            memberRecoveryRepository.save(memberRecovery);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MemberPointResDto point() {
        return new MemberPointResDto(getCurrentMember().getPoint());
    }

    @Override
    @Transactional(readOnly = true)
    public MemberLongestStreakResDto longestStreak() {
        int longestStreak = streakService.getMemberStreakResDto().longestStreakCount();
        return new MemberLongestStreakResDto(longestStreak);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberStreakRecoveryCountResDto streakRecoveryCount() {
        Member member = getCurrentMember();

        Integer freeRecoveryCount = memberRecoveryRepository.findByMember(member)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
            ).getFreeRecoveryCount();

        Integer paidRecoveryCount = member.getPaidRecoveryCount();

        return new MemberStreakRecoveryCountResDto(
            freeRecoveryCount + paidRecoveryCount,
            freeRecoveryCount
        );
    }

    @Override
    @Transactional(readOnly = true)
    public MemberHabitListResDto habits() {
        Long id = AuthUtil.getMemberIdFromAuthentication();
        List<MemberHabitListElementDto> memberHabits = memberHabitRepository
            .findAllByMemberIdOrderByCreatedDatetime(id);

        return new MemberHabitListResDto(
            memberHabits
        );
    }

    @Override
    @Transactional(readOnly = true)
    public MemberStatisticResDto statistic() {
        Long memberId = AuthUtil.getMemberIdFromAuthentication();
        // 수행 횟수 기준 상위 5개, 나머지는 기타로 합치기
        // 내림차순 정렬된 데이터, 각 MemberPracticedHabitDto의 value는 비율
        // 데이터가 없는 경우 공백 리스트([])
        List<MemberTopHabitDto> topHabits = processTopHabits(
            habitTrackerRepository.findTopHabits(memberId),
            memberId
        );

        // 가장 많이 수행한 해빗 이름
        // 데이터가 없는 경우 null
        String maxPracticedHabit = topHabits.isEmpty() ? null : topHabits.get(0).habit();

        // 요일 별 달성 횟수
        // 최대 또는 최대값 중복 허용
        List<MemberPracticeCountPerDayOfWeekDto> dataPerDayOfWeek
            = practiceCountListPerDayOfWeek(memberId);

        // 4 (0~24시까지 1시간 단위로 24개)
        List<MemberPracticeCountPerHourDto> practiceCountPerHour = processPracticeCountPerHour(
            habitTrackerRepository.countPracticedHabitsPerHour(memberId)
        );

        LocalDate memberCreatedDate = getCurrentMember().getCreatedDateTime().toLocalDate();
        LocalDate now = LocalDate.now();

        // 총 서비스 사용 일 수
        int totalDays = (int) ChronoUnit.DAYS.between(
            memberCreatedDate,
            now
        );

        // 스트릭 관련 데이터
        MemberStreakResDto memberStreakDto = streakService.getMemberStreakResDto();

        // 스트릭을 채운 날의 수
        int streakDays = memberStreakDto.achieveStreakCount();

        // 별을 받은(모든 해빗을 달성한) 날의 수
        int starredDays = memberStreakDto.starCount();

        // 최장 스트릭
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
    protected List<MemberTopHabitDto> processTopHabits(
        List<MemberTopHabitDto> topHabits,
        Long memberId
    ) {
        int count = habitTrackerRepository.countByMemberId(memberId).intValue();

        // 전체 갯수가 topHabits 갯수보다 많다면
        if (count > topHabits.size()) {
            // 100에서 topHabits의 합을 뺀 값이 기타가 된다.
            int sum = topHabits.stream()
                .mapToInt(MemberTopHabitDto::value)
                .sum();
            int etcValue = 100 - sum;

            topHabits.add(
                new MemberTopHabitDto(
                    "기타",
                    etcValue
                )
            );
        }

        return topHabits;
    }

    @Transactional(readOnly = true)
    protected List<MemberPracticeCountPerDayOfWeekDto> practiceCountListPerDayOfWeek(
        Long memberId) {
        // colorIdx가 없는 DTO의 리스트를 받는다.
        List<HabitPracticeCountPerDayOfWeekDto> habitPracticeCountPerDayOfWeekDtoList
            = habitTrackerRepository.countPracticedHabitsPerDayOfWeek(memberId);

        if (habitPracticeCountPerDayOfWeekDtoList.isEmpty()) {
            return new ArrayList<>();
        }

        // 요일 별 수행횟수의 최대, 최소 값을 구한다.
        int maxValue = habitPracticeCountPerDayOfWeekDtoList.stream()
            .mapToInt(HabitPracticeCountPerDayOfWeekDto::value)
            .max()
            .getAsInt();
        int minValue = habitPracticeCountPerDayOfWeekDtoList.stream()
            .mapToInt(HabitPracticeCountPerDayOfWeekDto::value)
            .min()
            .getAsInt();

        List<MemberPracticeCountPerDayOfWeekDto> practiceCountListPerDayOfWeek =
            habitPracticeCountPerDayOfWeekDtoList.stream()
                .map(
                    // map()을 통해 colorIdx를 설정한다.
                    habitPracticeCountPerDayOfWeekDto -> {
                        int value = habitPracticeCountPerDayOfWeekDto.value();

                        int colorIdx = COLOR_INDEX_DEFAULT;
                        if (value == minValue) {
                            colorIdx = COLOR_INDEX_MIN;
                        } else if (value == maxValue) {
                            colorIdx = COLOR_INDEX_MAX;
                        }

                        return new MemberPracticeCountPerDayOfWeekDto(
                            DayOfWeek.getValueByIndex(habitPracticeCountPerDayOfWeekDto.day()),
                            value,
                            colorIdx
                        );
                    }
                ).toList();

        return practiceCountListPerDayOfWeek;
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
    @Transactional
    public MemberTreePointResDto treePoint() {
        Member member = getCurrentMember();

        // 오늘 받았다면
        if (member.getLastHarvestedDate().getDayOfMonth() == LocalDate.now().getDayOfMonth()) {
            throw new MemberException(MemberErrorCode.ALREADY_HARVESTED);
        }

        Tree tree = member.getTree();
        int point = RANDOM.nextInt(tree.getRangeFrom(), tree.getRangeTo()) + 1;

        member.addPoint(point);
        System.out.println("point: " + member.getPoint());
        memberRepository.save(member);

        return new MemberTreePointResDto(point);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberHabitTrackersResDto habitTrackers(String memberHabitId) {
        // 필요시 memberId 데이터베이스 존재 여부 체크
        Long memberId = AuthUtil.getMemberIdFromAuthentication();
        Long longMemberHabitId = Long.parseLong(memberHabitId);

        List<Integer> yearList = habitTrackerRepository.findAllCreatedYearByMemberHabitId(
            memberId,
            longMemberHabitId
        );
        List<MemberHabitTrackerDto> habitTrackerGroupList = new ArrayList<>();
        for (Integer year : yearList) {
            habitTrackerGroupList.add(
                habitTrackerRepository.findAllHabitTrackerByYearAndMemberHabitId(
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

    @Override
    @Transactional(readOnly = true)
    public MemberDailyPhraseResDto dailyPhrase() {
        Member member = getCurrentMember();

        MemberDailyPhrase memberDailyPhrase = memberDailyPhraseRepository.findByMember(member)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER)
            );

        return new MemberDailyPhraseResDto(
            memberDailyPhrase.getDailyPhrase().getPhrase()
        );
    }

}
