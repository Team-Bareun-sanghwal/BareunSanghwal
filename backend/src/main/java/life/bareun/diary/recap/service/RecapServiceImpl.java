package life.bareun.diary.recap.service;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import life.bareun.diary.global.config.WebClientConfig;
import life.bareun.diary.global.notification.dto.NotificationResultTokenDto;
import life.bareun.diary.global.notification.entity.NotificationCategory;
import life.bareun.diary.global.notification.repository.NotificationCategoryRepository;
import life.bareun.diary.global.notification.repository.NotificationTokenRepository;
import life.bareun.diary.global.notification.service.NotificationService;
import life.bareun.diary.global.auth.util.AuthUtil;
import life.bareun.diary.habit.entity.Habit;
import life.bareun.diary.habit.entity.HabitTracker;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.habit.repository.HabitRepository;
import life.bareun.diary.habit.repository.HabitTrackerRepository;
import life.bareun.diary.habit.repository.MemberHabitRepository;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.recap.dto.MessageDto;
import life.bareun.diary.recap.dto.RecapDto;
import life.bareun.diary.recap.dto.RecapHabitRateDto;
import life.bareun.diary.recap.dto.RecapMemberDto;
import life.bareun.diary.recap.dto.RecapMemberHabitDto;
import life.bareun.diary.recap.dto.RecapMemberHabitRateDto;
import life.bareun.diary.recap.dto.RecapMemberMonthDto;
import life.bareun.diary.recap.dto.RecapModifyDto;
import life.bareun.diary.recap.dto.RecapMonthDto;
import life.bareun.diary.recap.dto.RecapSimpleDto;
import life.bareun.diary.recap.dto.request.GptReqDto;
import life.bareun.diary.recap.dto.response.GptResDto;
import life.bareun.diary.recap.dto.response.RecapDetailResDto;
import life.bareun.diary.recap.dto.response.RecapListResDto;
import life.bareun.diary.recap.entity.Recap;
import life.bareun.diary.recap.entity.RecapHabitAccomplished;
import life.bareun.diary.recap.entity.RecapHabitRatio;
import life.bareun.diary.recap.entity.embed.Occasion;
import life.bareun.diary.recap.exception.RecapErrorCode;
import life.bareun.diary.recap.exception.RecapException;
import life.bareun.diary.recap.repository.RecapHabitAccomplishedRepository;
import life.bareun.diary.recap.repository.RecapHabitRatioRepository;
import life.bareun.diary.recap.repository.RecapRepository;
import life.bareun.diary.streak.repository.MemberDailyStreakRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RecapServiceImpl implements RecapService {

    private final MemberRepository memberRepository;

    private final RecapHabitAccomplishedRepository recapHabitAccomplishedRepository;

    private final RecapHabitRatioRepository recapHabitRatioRepository;

    private final RecapRepository recapRepository;

    private final HabitRepository habitRepository;

    private final MemberDailyStreakRepository memberDailyStreakRepository;

    private final HabitTrackerRepository habitTrackerRepository;

    private final MemberHabitRepository memberHabitRepository;

    private final WebClientConfig webClient;

    private final NotificationTokenRepository notificationTokenRepository;

    private final NotificationCategoryRepository notificationCategoryRepository;

    private final NotificationService notificationService;

    @Value("${gpt.api.key}")
    private String apiKey;

    @Value("${gpt.api.model}")
    private String apiModel;

    private static final String PROMPT = " 라는 내용을 가장 핵심이 되는 한 단어로 요약해주세요. 반드시 명사여야 하고, 가장 많이 언급되는 단어를 기반으로 해야 합니다. 특수기호가 없어야 하며 5글자 이내로 해주세요.";
    private static final String IMAGE_BASIC = "basic";

    @Override
    public void createRecap() {
        // 두 번 이상 완료한 사용자 해빗을 하나라도 가지고 있는 사용자 리스트
        // 자정에 시작하기 때문에 전 달에 달성한 목록을 가져와야 함
        // LocalDate nowMonth = LocalDate.now().minusMonths(1L);
        LocalDate nowMonth = LocalDate.now();
        List<RecapMemberDto> recapMemberList = recapRepository.findAllAppropriateMember(
            RecapMonthDto.builder().year(nowMonth.getYear()).month(nowMonth.getMonth().getValue())
                .build());

        // 기간 내에 작성한 해빗 트래커들만 가져오기 위해 범위 지정
        LocalDateTime startDateTime = nowMonth.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endDateTime = nowMonth.withDayOfMonth(nowMonth.lengthOfMonth())
            .atTime(23, 59, 59);

        // Redis에 존재하는 사용자 토큰 목록
        Map<Long, String> notificationTokenMap = notificationTokenRepository.findAllNotificationToken();

        // 커스텀할 content
        NotificationCategory notificationCategory = notificationCategoryRepository.findById(3L)
            .orElseThrow(() -> new RecapException(RecapErrorCode.NOT_FOUND_NOTIFICATION_CATEGORY));

        for (RecapMemberDto recapMemberDto : recapMemberList) {
            Member member = findMember();
            // 처음에 recap을 생성
            Recap recap = recapRepository.save(Recap.builder().member(member).build());

            // 사용자의 사용자 해빗 당 몇 개 했는지 가져옴(2개 이상만)
            List<RecapMemberHabitDto> recapMemberHabitDtoList =
                recapRepository.findAllAppropriateMemberHabit(RecapMemberMonthDto.builder()
                    .member(recapMemberDto.member()).year(nowMonth.getYear())
                    .month(nowMonth.getMonth().getValue()).build());
            long sum = 0;
            for (RecapMemberHabitDto recapMemberHabitDto : recapMemberHabitDtoList) {
                sum += recapMemberHabitDto.habitTrackerCount();
            }

            // 베스트 해빗 찾기
            long bestHabitId = findBestHabit(recapMemberHabitDtoList, sum);
            Map<Long, Long> habitCountMap = new ConcurrentHashMap<>();
            StringBuilder totalContent = new StringBuilder();
            for (RecapMemberHabitDto recapMemberHabitDto : recapMemberHabitDtoList) {
                // recapHabitAccomplished 생성
                // 만약 habitTrackerCount 값이 max와 같으면 isBest = true 아니면 false
                // memberHabit, recap, 달성률, 년도, 월
                boolean isBest = recapMemberHabitDto.memberHabit().getId() == bestHabitId;
                int totalCount = habitTrackerRepository.countByMemberHabit(
                    recapMemberHabitDto.memberHabit());
                recapHabitAccomplishedRepository.save(
                    RecapHabitAccomplished.builder().memberHabit(recapMemberHabitDto.memberHabit())
                        .recap(recap)
                        .isBest(isBest).createdYear(nowMonth.getYear())
                        .createdMonth(nowMonth.getMonthValue())
                        .actionCount(Math.toIntExact(recapMemberHabitDto.habitTrackerCount()))
                        .missCount((int) (totalCount - recapMemberHabitDto.habitTrackerCount()))
                        .achievementRate(
                            (int) (((recapMemberHabitDto.habitTrackerCount() * 1.0) / totalCount)
                                * 100))
                        .build());

                habitCountMap.put(recapMemberHabitDto.memberHabit().getHabit().getId(),
                    habitCountMap.getOrDefault(recapMemberHabitDto.memberHabit().getHabit().getId(),
                        0L) + recapMemberHabitDto.habitTrackerCount());

                // 키워드 생성할 텍스트 파일 만들기
                List<HabitTracker> habitTrackerList = habitTrackerRepository.findAllByMemberHabitAndContentIsNotNullAndSucceededTimeBetween(
                    recapMemberHabitDto.memberHabit(), startDateTime, endDateTime);
                for (HabitTracker habitTracker : habitTrackerList) {
                    totalContent.append(habitTracker.getContent()).append("\n");
                }
            }

            // recapHabitRatio 생성
            createRecapHabitRatio(habitCountMap, sum, recap, nowMonth);

            // 베스트 해빗의 이미지 중 랜덤으로 하나 가져오기
            String imageUrl = findRandomImage(bestHabitId);

            // 가장 많이 제출한 시간대 구하기
            int currentTime = findMostSubmitTime(nowMonth, member);
            Occasion occasion = findOccasion(currentTime);
            // 별 개수 구하기
            LocalDate startDate = nowMonth.withDayOfMonth(1);
            LocalDate endDate = nowMonth.withDayOfMonth(nowMonth.lengthOfMonth());
            int wholeStreak = memberDailyStreakRepository
                .countByIsStaredAndMemberAndCreatedDateBetween(true, member, startDate,
                    endDate);

            // 키워드 생성
            String keyword = createKeyWord(totalContent.toString()).content();
            recapRepository.modifyRecap(
                RecapModifyDto.builder().recap(recap).wholeStreak(wholeStreak)
                    .maxHabitImage(imageUrl).mostFrequencyWord(keyword)
                    .mostFrequencyTime(occasion).build());

            if (notificationTokenMap.containsKey(recapMemberDto.member().getId())) {
                notificationService.createNotification(
                    NotificationResultTokenDto.builder().member(recapMemberDto.member()).content(
                            String.format(notificationCategory.getContent(),
                                recapMemberDto.member().getNickname(), nowMonth.getYear(),
                                nowMonth.getMonthValue()))
                        .token(notificationTokenMap.get(recapMemberDto.member().getId())).build(),
                    notificationCategory);
            }
        }
    }

    @Override
    public RecapListResDto findAllRecap() {
        Member member = findMember();
        List<Recap> recapList = recapRepository.findAllByMember(member);

        Set<Integer> yearSet = new HashSet<>();
        for (Recap recap : recapList) {
            yearSet.add(recap.getCreatedDatetime().getYear());
        }
        Map<Integer, Object> yearRecapMap = new HashMap<>();
        List<Integer> yearList = new ArrayList<>();
        for (Integer year : yearSet) {
            List<RecapSimpleDto> recapSimpleDtoList = new ArrayList<>();
            yearRecapMap.put(year, recapSimpleDtoList);
            yearList.add(year);
        }
        for (Recap recap : recapList) {
            List<RecapSimpleDto> recapSimpleDtoList = (List<RecapSimpleDto>) yearRecapMap.get(
                recap.getCreatedDatetime().minusMonths(1L).getYear());
            recapSimpleDtoList.add(
                RecapSimpleDto.builder().recapId(recap.getId()).image(recap.getMaxHabitImage())
                    .period(recap.getCreatedDatetime().minusMonths(1L).toLocalDate()).build());
        }

        List<RecapDto> recapDtoList = new ArrayList<>();
        for (Entry<Integer, Object> entry : yearRecapMap.entrySet()) {
            List<RecapSimpleDto> recapSimpleDtoList = (List<RecapSimpleDto>) entry.getValue();
            recapSimpleDtoList.sort((o1, o2) -> o2.period().compareTo(o1.period()));
            recapDtoList.add(
                RecapDto.builder().year(entry.getKey()).recapList(recapSimpleDtoList).build());
        }

        // 연도 내림차순으로 정렬
        recapDtoList.sort((o1, o2) -> o2.year() - o1.year());
        yearList.sort((o1, o2) -> o2 - o1);

        return RecapListResDto.builder().recapGroupList(recapDtoList).yearList(yearList).build();

    }

    @Override
    // 리캡 상세 조회
    public RecapDetailResDto findDetailRecap(Long recapId) {
        // mostSucceededMemberHabit과 rateByMemberHabitList를 구하기
        Recap recap = recapRepository.findById(recapId)
            .orElseThrow(() -> new RecapException(RecapErrorCode.NOT_FOUND_RECAP));
        List<RecapHabitAccomplished> recapHabitAccomplishedList = recapHabitAccomplishedRepository.findAllByRecap_OrderByActionCountDesc(
            recap);

        // ratio 비율 상위 다섯개 및 사용자 해빗 달성률의 평균 구하기
        String mostSucceededMemberHabit = recapHabitAccomplishedRepository.findByRecapAndIsBest(
            recap, true).getMemberHabit().getAlias();
        int achievementRateSum = 0;
        List<RecapMemberHabitRateDto> recapMemberHabitRateDtoList = new ArrayList<>();
        for (RecapHabitAccomplished recapHabitAccomplished : recapHabitAccomplishedList) {
            achievementRateSum += recapHabitAccomplished.getAchievementRate();
            recapMemberHabitRateDtoList.add(RecapMemberHabitRateDto.builder()
                .name(recapHabitAccomplished.getMemberHabit().getAlias())
                .actionCount(recapHabitAccomplished.getActionCount())
                .missCount(recapHabitAccomplished.getMissCount())
                .ratio(recapHabitAccomplished.getAchievementRate()).build());
        }
        double averageRateByMemberHabit =
            Math.round(((achievementRateSum * 1.0) / recapHabitAccomplishedList.size()) * 10)
                / 10.0;

        // totalCount 내림차순으로 정렬
        recapMemberHabitRateDtoList.sort(
            (o1, o2) -> (o2.actionCount() + o2.missCount()) - (o1.actionCount() + o1.missCount()));

        // 첫 5개 요소를 제외한 나머지 요소를 삭제
        recapMemberHabitRateDtoList.subList(5, recapMemberHabitRateDtoList.size()).clear();

        // mostSucceededHabit과 rateByHabitList를 구하기
        List<RecapHabitRatio> recapHabitRatioList = recapHabitRatioRepository.findTop4ByRecap_OrderByRatioDesc(
            recap);
        List<RecapHabitRateDto> recapHabitRateDtoList = new ArrayList<>();
        int sum = 0;
        for (RecapHabitRatio recapHabitRatio : recapHabitRatioList) {
            int ratio = (int) recapHabitRatio.getRatio();
            sum += ratio;
            recapHabitRateDtoList.add(
                RecapHabitRateDto.builder().name(recapHabitRatio.getHabit().getName()).ratio(ratio)
                    .build());
        }
        recapHabitRateDtoList.add(RecapHabitRateDto.builder().name("기타").ratio(100 - sum).build());
        String mostSucceededHabit = recapHabitRateDtoList.get(0).name();
        String mostSubmitTime = recap.getMostFrequencyTime().name();
        String myKeyWord = recap.getMostFrequencyWord();
        int collectedStar = recap.getWholeStreak();
        String image = recap.getMaxHabitImage();

        Member member = findMember();

        return RecapDetailResDto.builder().mostSucceededHabit(mostSucceededHabit)
            .mostSucceededMemberHabit(mostSucceededMemberHabit)
            .averageRateByMemberHabit(averageRateByMemberHabit)
            .rateByMemberHabitList(recapMemberHabitRateDtoList)
            .rateByHabitList(recapHabitRateDtoList).mostSubmitTime(mostSubmitTime)
            .collectedStar(collectedStar).myKeyWord(myKeyWord).image(image)
            .year(recap.getCreatedDatetime().getYear())
            .month(recap.getCreatedDatetime().getMonthValue())
            .memberName(member.getNickname()).build();
    }

    private GptResDto createKeyWord(String totalContent) {
        MessageDto messageDto = MessageDto.builder().role("user")
            .content(totalContent + PROMPT).build();
        GptReqDto gptReqDto = GptReqDto.builder().model(apiModel).message(messageDto)
            .temperature(1).max_tokens(16).build();
        return webClient.webClient().post()
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + apiKey)
            .bodyValue(gptReqDto)
            .retrieve().bodyToMono(JsonNode.class).<GptResDto>handle((response, sink) -> {
                if (response.get("choices").get(0).get("message").get("content") == null) {
                    sink.error(new RecapException(RecapErrorCode.NOT_CREATE_RECAP_CONTENT));
                    return;
                }
                sink.next(GptResDto.builder()
                    .content(response.get("choices").get(0).get("message").get("content").asText())
                    .build());
            }).block();
    }

    private int findMostSubmitTime(LocalDate nowMonth, Member member) {
        int timeMax = 0;
        int currentTime = 0;
        int endDay = nowMonth.lengthOfMonth();
        for (int time = 5; time < 24; time += 6) {
            int timeCount = 0;
            for (int day = 1; day <= endDay; day++) {
                LocalDateTime startDateTime = nowMonth.withDayOfMonth(day)
                    .atTime(time - 5, 0, 0);
                LocalDateTime endDateTime = nowMonth.withDayOfMonth(day)
                    .atTime(time, 59, 59);
                timeCount += habitTrackerRepository.countByMemberAndSucceededTimeBetween(member,
                    startDateTime, endDateTime);
            }
            if (timeMax < timeCount) {
                timeMax = timeCount;
                currentTime = time;
            }
        }
        return currentTime;
    }

    private Long findBestHabit(List<RecapMemberHabitDto> recapMemberHabitDtoList, long sum) {
        List<long[]> bestHabitList = new ArrayList<>();
        bestHabitList.add(new long[]{recapMemberHabitDtoList.get(0).memberHabit().getId(),
            recapMemberHabitDtoList.get(0).habitTrackerCount()});

        // 달성 횟수가 가장 많은 것
        for (int memberHabitId = 1; memberHabitId < recapMemberHabitDtoList.size();
            memberHabitId++) {
            if (bestHabitList.get(0)[1] == recapMemberHabitDtoList.get(memberHabitId)
                .habitTrackerCount()) {
                bestHabitList.add(
                    new long[]{recapMemberHabitDtoList.get(memberHabitId).memberHabit().getId(),
                        recapMemberHabitDtoList.get(memberHabitId).habitTrackerCount()});
            }
        }
        if (bestHabitList.size() == 1) {
            return bestHabitList.get(0)[0];
        }

        // 달성률이 가장 높은 것
        List<Long> bestAchievementHabitList = new ArrayList<>();
        double bestAchievementRate = 0;
        long memberHabitId;
        for (long[] memberHabitPair : bestHabitList) {
            double nowRatio = ((double) memberHabitPair[1] / sum) * 100;
            if (bestAchievementRate < nowRatio) {
                bestAchievementRate = nowRatio;
                memberHabitId = memberHabitPair[0];
                bestAchievementHabitList.clear();
                bestAchievementHabitList.add(memberHabitId);
            } else if (bestAchievementRate == nowRatio) {
                bestAchievementHabitList.add(memberHabitPair[0]);
            }
        }

        if (bestAchievementHabitList.size() == 1) {
            return bestAchievementHabitList.get(0);
        }

        // 해빗 스트릭이 작은 것
        memberHabitId = 0L;
        LocalDateTime nowDateTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        for (Long bestAchievementHabit : bestAchievementHabitList) {
            // 최신 생성 순으로
            MemberHabit memberHabit = memberHabitRepository.findById(bestAchievementHabit)
                .orElseThrow(() -> new RecapException(RecapErrorCode.NOT_FOUND_MEMBER_HABIT));
            if (nowDateTime.isBefore(memberHabit.getCreatedDatetime())) {
                memberHabitId = memberHabit.getId();
                nowDateTime = memberHabit.getCreatedDatetime();
            }
        }
        return memberHabitId;
    }

    private String findRandomImage(Long bestHabitId) {
        MemberHabit memberHabit = memberHabitRepository.findById(bestHabitId)
            .orElseThrow(() -> new RecapException(RecapErrorCode.NOT_FOUND_MEMBER_HABIT));
        List<HabitTracker> habitTrackerList = habitTrackerRepository.findAllByMemberHabit(
            memberHabit);
        List<String> imageList = new ArrayList<>();
        for (HabitTracker habitTracker : habitTrackerList) {
            if (habitTracker.getImage() != null) {
                imageList.add(habitTracker.getImage());
            }
        }
        // 기본 이미지 url 넣기(환경변수)
        if (imageList.isEmpty()) {
            return IMAGE_BASIC;
        } else {
            Collections.sort(imageList);
            return imageList.get(0);
        }
    }

    private void createRecapHabitRatio(Map<Long, Long> habitCountMap, long sum, Recap recap,
        LocalDate nowMonth) {
        for (Entry<Long, Long> entry : habitCountMap.entrySet()) {
            Habit habit = habitRepository.findById(entry.getKey())
                .orElseThrow(() -> new RecapException(RecapErrorCode.NOT_FOUND_HABIT));
            double ratio = ((double) entry.getValue() / sum) * 100;
            // recapHabitRatio 생성
            recapHabitRatioRepository.save(
                RecapHabitRatio.builder().habit(habit)
                    .recap(recap).createdYear(nowMonth.getYear())
                    .createdMonth(nowMonth.getMonthValue()).ratio(ratio).build());
        }
    }

    private Occasion findOccasion(int currentTime) {
        return switch (currentTime) {
            case 5 -> Occasion.DAWN;
            case 11 -> Occasion.MORNING;
            case 17 -> Occasion.EVENING;
            default -> Occasion.NIGHT;
        };
    }

    private Member findMember() {
        return memberRepository.findById(AuthUtil.getMemberIdFromAuthentication())
            .orElseThrow(() -> new RecapException(RecapErrorCode.NOT_FOUND_MEMBER));
    }
}
