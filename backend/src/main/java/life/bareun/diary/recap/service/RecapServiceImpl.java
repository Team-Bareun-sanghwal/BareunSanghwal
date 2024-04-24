package life.bareun.diary.recap.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import life.bareun.diary.habit.entity.Habit;
import life.bareun.diary.habit.entity.HabitTracker;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.habit.repository.HabitRepository;
import life.bareun.diary.habit.repository.HabitTrackerRepository;
import life.bareun.diary.habit.repository.MemberHabitRepository;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.recap.dto.RecapMemberDto;
import life.bareun.diary.recap.dto.RecapMemberHabitDto;
import life.bareun.diary.recap.dto.RecapMemberMonthDto;
import life.bareun.diary.recap.dto.RecapModifyDto;
import life.bareun.diary.recap.dto.RecapMonthDto;
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

    @Override
    public void createRecap() {
        // security 코드 완성되면 추가
        Member member = memberRepository.findById(1L)
            .orElseThrow(() -> new RecapException(RecapErrorCode.NOT_FOUND_MEMBER));
        // 처음에 recap을 생성
        Recap recap = recapRepository.save(Recap.builder().member(member).build());

        // 두 번 이상 완료한 사용자 해빗을 하나라도 가지고 있는 사용자 리스트
        // 자정에 시작하기 때문에 전 달에 달성한 목록을 가져와야 함
        // LocalDate nowMonth = LocalDate.now().minusMonths(1L);
        LocalDate nowMonth = LocalDate.now();
        List<RecapMemberDto> recapMemberList = recapRepository.findAllAppropriateMember(
            RecapMonthDto.builder().year(nowMonth.getYear()).month(nowMonth.getMonth().getValue())
                .build());

        for (RecapMemberDto recapMemberDto : recapMemberList) {
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
            for (RecapMemberHabitDto recapMemberHabitDto : recapMemberHabitDtoList) {
                // recapHabitAccomplished 생성
                // 만약 habitTrackerCount 값이 max와 같으면 isBest = true 아니면 false
                // memberHabit, recap, 달성률, 년도, 월
                boolean isBest = recapMemberHabitDto.memberHabit().getId() == bestHabitId;
                double achievementRatio =
                    ((double) recapMemberHabitDto.habitTrackerCount() / sum) * 100;
                recapHabitAccomplishedRepository.save(
                    RecapHabitAccomplished.builder().memberHabit(recapMemberHabitDto.memberHabit())
                        .recap(recap)
                        .isBest(isBest).createdYear(nowMonth.getYear())
                        .createdMonth(nowMonth.getMonthValue()).achievementRatio(achievementRatio)
                        .build());

                habitCountMap.put(recapMemberHabitDto.memberHabit().getHabit().getId(),
                    habitCountMap.getOrDefault(recapMemberHabitDto.memberHabit().getHabit().getId(),
                        0L) + 1);
            }

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

            // 베스트 해빗의 이미지 중 랜덤으로 하나 가져오기
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
            String imageUrl;
            if (imageList.isEmpty()) {
                imageUrl = "basic";
            } else {
                Collections.sort(imageList);
                imageUrl = imageList.get(0);
            }

            // 가장 많이 제출한 시간대 구하기
            LocalDateTime startDateTime = nowMonth.withDayOfMonth(1).atStartOfDay();
            int currentTime = findMostSubmitTime(startDateTime, nowMonth, member);
            Occasion occasion = findOccasion(currentTime);


            // 별 개수 구하기
            LocalDate startDate = nowMonth.withDayOfMonth(1);
            LocalDate endDate = nowMonth.withDayOfMonth(nowMonth.lengthOfMonth());
            int wholeStreak = memberDailyStreakRepository
                .countByIsStaredAndMemberAndCreatedDateBetween(true, member, startDate,
                    endDate);
            // 키워드 생성
            recapRepository.modifyRecap(
                RecapModifyDto.builder().recap(recap).wholeStreak(wholeStreak)
                    .maxHabitImage(imageUrl).mostFrequencyWord("keyword")
                    .mostFrequencyTime(occasion).build());
        }
    }

    private int findMostSubmitTime(LocalDateTime startDateTime, LocalDate nowMonth, Member member) {
        int timeMax = 0;
        int currentTime = 0;
        for (int time = 5; time < 24; time += 6) {
            LocalDateTime endDateTime = nowMonth.withDayOfMonth(nowMonth.lengthOfMonth())
                .atTime(time, 59, 59);
            int timeCount = habitTrackerRepository.countByMemberAndSucceededTimeBetween(member,
                startDateTime, endDateTime);
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

    private Occasion findOccasion(int currentTime) {
        return switch (currentTime) {
            case 5 -> Occasion.DAWN;
            case 11 -> Occasion.MORNING;
            case 17 -> Occasion.EVENING;
            default -> Occasion.NIGHT;
        };
    }
}
