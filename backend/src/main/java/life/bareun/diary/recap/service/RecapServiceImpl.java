package life.bareun.diary.recap.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import life.bareun.diary.habit.entity.Habit;
import life.bareun.diary.habit.repository.HabitRepository;
import life.bareun.diary.habit.repository.HabitTrackerRepository;
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

    @Override
    // 리캡 생성하기
    public void createRecap() {
        LocalDate nowMonth = LocalDate.now().minusMonths(1L);
        LocalDateTime startDateTime = nowMonth.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endDateTime = nowMonth.withDayOfMonth(nowMonth.lengthOfMonth())
            .atTime(23, 59, 59);

        // 해빗 당 2개 이상의 해빗 트래커 완료를 한 사용자만 분류
        // 이번 달에 한 번이라도 달성된 해빗 트래커 리스트

    }

    @Override
    public String findTemp() {
        // security 코드 완성되면 추가
        Member member = memberRepository.findById(1L)
            .orElseThrow(() -> new RecapException(RecapErrorCode.NOT_FOUND_MEMBER));
        // 처음에 recap을 생성
        Recap recap = recapRepository.save(Recap.builder().member(member).build());

        // 두 번 이상 완료한 사용자 해빗을 하나라도 가지고 있는 사용자 리스트
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

            long max = recapMemberHabitDtoList.get(0).habitTrackerCount();
            Map<Long, Long> habitCountMap = new ConcurrentHashMap<>();

            for (RecapMemberHabitDto recapMemberHabitDto : recapMemberHabitDtoList) {
                // recapHabitAccomplished 생성
                // 만약 habitTrackerCount 값이 max와 같으면 isBest = true 아니면 false
                // memberHabit, recap, 달성률, 년도, 월
                boolean isBest = recapMemberHabitDto.habitTrackerCount() == max;
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

            // 별 개수 구하기
            LocalDate startDate = nowMonth.withDayOfMonth(1);
            LocalDate endDate = nowMonth.withDayOfMonth(nowMonth.lengthOfMonth());
            int wholeStreak =
                memberDailyStreakRepository.countByIsStaredAndMemberAndCreatedDateBetween(
                    true, member, startDate, endDate);

            // 가장 많이 제출한 시간대 구하기
            LocalDateTime startDateTime = nowMonth.withDayOfMonth(1).atStartOfDay();
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
            Occasion occasion = findOccasion(currentTime);

            // 이미지와 키워드 생성

            recapRepository.modifyRecap(
                RecapModifyDto.builder().recap(recap).wholeStreak(wholeStreak)
                    .mostFrequencyTime(occasion).build());

        }
        return null;
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
