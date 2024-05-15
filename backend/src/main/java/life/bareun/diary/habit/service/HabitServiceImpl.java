package life.bareun.diary.habit.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import life.bareun.diary.global.auth.util.AuthUtil;
import life.bareun.diary.global.elastic.dto.ElasticDto;
import life.bareun.diary.global.elastic.service.ElasticService;
import life.bareun.diary.habit.dto.HabitMatchDto;
import life.bareun.diary.habit.dto.HabitRecommendDto;
import life.bareun.diary.habit.dto.HabitTrackerCreateDto;
import life.bareun.diary.habit.dto.HabitTrackerLastDto;
import life.bareun.diary.habit.dto.HabitTrackerTodayDto;
import life.bareun.diary.habit.dto.HabitTrackerTodayFactorDto;
import life.bareun.diary.habit.dto.MemberHabitActiveDto;
import life.bareun.diary.habit.dto.MemberHabitActiveSimpleDto;
import life.bareun.diary.habit.dto.MemberHabitDto;
import life.bareun.diary.habit.dto.MemberHabitModifyDto;
import life.bareun.diary.habit.dto.MemberHabitNonActiveDto;
import life.bareun.diary.habit.dto.request.HabitCreateReqDto;
import life.bareun.diary.habit.dto.request.HabitDeleteReqDto;
import life.bareun.diary.habit.dto.response.HabitMatchResDto;
import life.bareun.diary.habit.dto.response.HabitRankResDto;
import life.bareun.diary.habit.dto.response.MemberHabitActiveResDto;
import life.bareun.diary.habit.dto.response.MemberHabitActiveSimpleResDto;
import life.bareun.diary.habit.dto.response.MemberHabitNonActiveResDto;
import life.bareun.diary.habit.dto.response.MemberHabitResDto;
import life.bareun.diary.habit.entity.Habit;
import life.bareun.diary.habit.entity.HabitDay;
import life.bareun.diary.habit.entity.HabitRecommend;
import life.bareun.diary.habit.entity.HabitTracker;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.habit.entity.embed.MaintainWay;
import life.bareun.diary.habit.exception.HabitErrorCode;
import life.bareun.diary.habit.exception.HabitException;
import life.bareun.diary.habit.repository.HabitDayRepository;
import life.bareun.diary.habit.repository.HabitRecommendRepository;
import life.bareun.diary.habit.repository.HabitRepository;
import life.bareun.diary.habit.repository.HabitTrackerRepository;
import life.bareun.diary.habit.repository.MemberHabitRepository;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.streak.entity.HabitDailyStreak;
import life.bareun.diary.streak.exception.HabitDailyStreakErrorCode;
import life.bareun.diary.streak.exception.StreakException;
import life.bareun.diary.streak.repository.HabitDailyStreakRepository;
import life.bareun.diary.streak.service.StreakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;

    private final MemberHabitRepository memberHabitRepository;

    private final MemberRepository memberRepository;

    private final HabitTrackerService habitTrackerService;

    private final HabitTrackerRepository habitTrackerRepository;

    private final HabitDayRepository habitDayRepository;

    private final HabitDailyStreakRepository habitDailyStreakRepository;

    private final StreakService streakService;

    private final ElasticService elasticService;

    private final HabitRecommendRepository habitRecommendRepository;

    // 엘라스틱서치 로그 수집을 위한 logger
    private final Logger rankLogger = LoggerFactory.getLogger("rank-log");

    @Override
    // 사용자가 해빗을 생성
    public void createMemberHabit(HabitCreateReqDto habitCreateReqDto) {
        if((habitCreateReqDto.dayOfWeek() == null && habitCreateReqDto.period() == null)
        || habitCreateReqDto.icon() == null || habitCreateReqDto.alias() == null) {
            throw new HabitException(HabitErrorCode.INVALID_PARAMETER_MEMBER_HABIT);
        }

        rankLogger.info("rank-log {} {}", habitCreateReqDto.habitId(), "랭킹 순위에 반영됩니다.");
        // 멤버, 해빗 가져오기
        Habit habit = habitRepository.findById(habitCreateReqDto.habitId())
            .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_HABIT));
        Member member = findMember();
        // 달의 마지막 연, 월, 일 가져오기
        LocalDate lastDay = YearMonth.of(LocalDate.now().getYear(), LocalDate.now().getMonth())
            .atEndOfMonth();

        MemberHabit memberHabit;

        // 만약 말일이라면 아무것도 안함
        // 말일이 아니라면 말일까지 증가시키면서 맞는 조건에만 생성
        if (habitCreateReqDto.dayOfWeek() == null) {
            // 만약 2~6이 아니라면 오류
            if(habitCreateReqDto.period() > 6 || habitCreateReqDto.period() < 2) {
                throw new HabitException(HabitErrorCode.INVALID_PARAMETER_MEMBER_HABIT);
            }
            memberHabit = memberHabitRepository.save(
                MemberHabit.builder().member(member).habit(habit).alias(habitCreateReqDto.alias())
                    .icon(habitCreateReqDto.icon()).isDeleted(false).maintainWay(
                        MaintainWay.PERIOD).maintainAmount(habitCreateReqDto.period()).build());
            // 주기 방식으로 트래커 목록 생성(내일부터)
            LocalDate startDay = LocalDate.now().plusDays(1L);
            createHabitTrackerByPeriod(startDay, lastDay, member, memberHabit,
                habitCreateReqDto.period());
        } else {
            memberHabit = memberHabitRepository.save(
                MemberHabit.builder().member(member).habit(habit).alias(habitCreateReqDto.alias())
                    .icon(habitCreateReqDto.icon()).isDeleted(false).maintainWay(
                        MaintainWay.DAY).maintainAmount(0).build());
            for (Integer day : habitCreateReqDto.dayOfWeek()) {
                // 만약 1~7이 아니라면 오류
                if(day > 7 || day < 1) {
                    throw new HabitException(HabitErrorCode.INVALID_PARAMETER_MEMBER_HABIT);
                }
                habitDayRepository.save(
                    HabitDay.builder().memberHabit(memberHabit).day(day).build());
                // 요일 방식으로 트래커 목록 생성
                long plusDay = 1L;

                while (LocalDate.now().plusDays(plusDay).getDayOfWeek().getValue() != day) {
                    plusDay++;
                }
                LocalDate startDay = LocalDate.now().plusDays(plusDay);
                // 일주일씩 증가시키며 생성
                createHabitTrackerByDay(startDay, lastDay, member, memberHabit, day);
            }
        }

        streakService.initialHabitStreak(memberHabit);
    }

    @Override
    // 사용자 해빗을 삭제
    public void deleteMemberHabit(HabitDeleteReqDto habitDeleteReqDto) {
        streakService.deleteHabitStreak(memberHabitRepository.findById(habitDeleteReqDto.memberHabitId())
            .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_MEMBER_HABIT)));
        
        // 만약 모두 삭제한다고 하면 이전 기록들까지 전부 삭제
        if (Boolean.TRUE.equals(habitDeleteReqDto.isDeleteAll())) {
            habitTrackerService.deleteAllHabitTracker(habitDeleteReqDto.memberHabitId());
            MemberHabit memberHabit = memberHabitRepository.findById(
                    habitDeleteReqDto.memberHabitId())
                .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_MEMBER_HABIT));
            habitDayRepository.deleteAllByMemberHabit(memberHabit);
            memberHabitRepository.deleteById(habitDeleteReqDto.memberHabitId());
        } else {
            // 아니라면 이전 것들은 유지
            habitTrackerService.deleteAfterHabitTracker(habitDeleteReqDto.memberHabitId());
            memberHabitRepository.modifyStatus(
                MemberHabitModifyDto.builder().memberHabitId(habitDeleteReqDto.memberHabitId())
                    .succeededTime(LocalDateTime.now()).build());
        }
    }

    @Override
    // 이번 달에 한 번이라도 유지한 적이 있는 사용자 해빗 가져오기
    public MemberHabitResDto findAllMonthMemberHabit(String monthValue) {
        Member member = findMember();
        YearMonth yearMonth = YearMonth.of(Integer.parseInt(monthValue.substring(0, 4)),
            Integer.parseInt(monthValue.substring(5)));
        LocalDateTime startDateTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDateTime = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        List<MemberHabit> memberHabitList = memberHabitRepository
            .findAllByMemberAndCreatedDatetimeBetween(member, startDateTime, endDateTime);

        List<MemberHabitDto> memberHabitDtoList = new ArrayList<>();
        for (MemberHabit memberHabit : memberHabitList) {
            if (Boolean.TRUE.equals(habitTrackerService
                .existsByMemberHabitAndSucceededTimeIsNotNull(memberHabit))) {
                memberHabitDtoList.add(
                    MemberHabitDto.builder().memberHabitId(memberHabit.getId())
                        .alias(memberHabit.getAlias()).icon(memberHabit.getIcon()).build());
            }
        }
        return MemberHabitResDto.builder().memberHabitDtoList(memberHabitDtoList).build();
    }

    @Override
    // 활성화된 해빗 유지하기
    public void connectHabitList() {
        // 이번 달의 마지막 날
        LocalDate nowMonth = LocalDate.now();
        int lastDayOfNowMonth = nowMonth.lengthOfMonth();

        // 다음 달 첫 일
        LocalDate nextMonth = nowMonth.plusMonths(1L).withDayOfMonth(1);

        // 다음 달의 마지막 일
        LocalDate lastDay = YearMonth.of(nextMonth.getYear(), nextMonth.getMonth()).atEndOfMonth();

        // 이번 달에 유지중인 memberHabit을 모두 가져오기
        List<MemberHabit> memberHabitList = memberHabitRepository.findAllByIsDeleted(false);
        for (MemberHabit memberHabit : memberHabitList) {
            // 만약 유지 방법이 요일이라면
            if (memberHabit.getMaintainWay() == MaintainWay.DAY) {
                // 여러 요일일수도 있기 때문에 요일 리스트를 조회
                List<HabitDay> habitDayList = habitDayRepository.findAllByMemberHabit(memberHabit);
                // 요일 방식으로 트래커 목록 생성
                for (HabitDay habitDay : habitDayList) {
                    long plusDay = 1L;
                    while (nextMonth.plusDays(plusDay).getDayOfWeek().getValue()
                        != habitDay.getDay()) {
                        plusDay++;
                    }
                    // 일주일씩 증가시키며 생성
                    LocalDate startDay = nextMonth.plusDays(plusDay);
                    createHabitTrackerByDay(startDay, lastDay, memberHabit.getMember(), memberHabit,
                        habitDay.getDay());
                }
            } else {
                // 만약 유지 방법이 주기라면
                // 가장 마지막 생성된 해빗 트래커 가져오기
                HabitTracker habitTracker = habitTrackerService.findLastHabitTracker(
                    HabitTrackerLastDto.builder().memberHabit(memberHabit).build());

                // 만약 말일에 생성되기 때문에 해빗 트래커가 하나도 없다면 1일부터 생성
                int startDay = 1;
                if (habitTracker != null) {
                    // 주기를 더한 날이 말일보다 더 크게 될 때까지 증가
                    startDay = habitTracker.getCreatedDay();
                    while (startDay <= lastDayOfNowMonth) {
                        startDay += memberHabit.getMaintainAmount();
                    }
                    // 말일을 빼서 다음 달의 해당 해빗의 첫 해빗 트래커 날짜를 구함
                    startDay -= lastDayOfNowMonth;
                }
                LocalDate startDate = LocalDate.of(nowMonth.getYear(),
                    nextMonth.getMonth().getValue(), startDay);
                // 주기 방식으로 생성
                createHabitTrackerByPeriod(startDate, lastDay, memberHabit.getMember(), memberHabit,
                    memberHabit.getMaintainAmount());
            }
        }
    }

    @Override
    // 모든 활성화된 사용자 해빗 리스트 조회
    public MemberHabitActiveResDto findAllActiveMemberHabit() {
        Member member = findMember();
        // 해당 사용자의 삭제되지 않은 사용자 해빗 리스트 조회
        List<MemberHabit> memberHabitList = memberHabitRepository.findAllByIsDeletedAndMember(false,
            member);

        LocalDate nowMonth = LocalDate.now();
        // 오늘 날짜에 있는 사용자의 해빗 트래커 리스트 조회
        List<HabitTrackerTodayDto> habitTrackerList = habitTrackerRepository
            .findAllTodayHabitTracker(HabitTrackerTodayFactorDto.builder()
                .memberId(AuthUtil.getMemberIdFromAuthentication())
                .createdYear(nowMonth.getYear()).createdMonth(nowMonth.getMonthValue())
                .createdDay(nowMonth.getDayOfMonth()).build());

        List<MemberHabitActiveDto> memberHabitActiveDtoList = new ArrayList<>();
        // 활성화된 사용자 해빗 리스트를 순회하며
        for (MemberHabit memberHabit : memberHabitList) {
            Long habitTrackerId = 0L;
            // 오늘 있는 사용자의 해빗 트래커 리스트를 순회하며
            // 만약 memberHabit의 id와 해빗 트래커의 memberHabitId()가 같다면 habitTrackerId에 저장함
            // 하나의 사용자 해빗에 매핑되는 해빗 트래커는 하루에 최대 하나이기 때문에 찾았다면 break
            for (HabitTrackerTodayDto habitTrackerTodayDto : habitTrackerList) {
                if (Objects.equals(habitTrackerTodayDto.memberHabitId(), memberHabit.getId())) {
                    habitTrackerId = habitTrackerTodayDto.habitTrackerId();
                    break;
                }
            }

            HabitTracker habitTracker = null;

            if (habitTrackerId != 0L) {
                habitTracker = habitTrackerRepository.findById(habitTrackerId)
                    .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_HABIT_TRACKER));
            }

            boolean isSucceeded = habitTracker != null && habitTracker.getSucceededTime() != null;

            // 현재 사용자 해빗의 스트릭
            HabitDailyStreak habitDailyStreak = habitDailyStreakRepository
                .findByMemberHabitAndCreatedDate(memberHabit, LocalDate.now())
                .orElseThrow(() -> new StreakException(
                    HabitDailyStreakErrorCode.NOT_FOUND_HABIT_DAILY_STREAK));

            List<Integer> dayList = habitDayRepository.findAllDayByMemberHabit(memberHabit);
            memberHabitActiveDtoList.add(
                MemberHabitActiveDto.builder().name(memberHabit.getHabit().getName())
                    .alias(memberHabit.getAlias()).memberHabitId(memberHabit.getId())
                    .icon(memberHabit.getIcon()).createdAt(memberHabit.getCreatedDatetime())
                    .habitTrackerId(habitTrackerId)
                    .currentStreak(habitDailyStreak.getCurrentStreak()).dayList(dayList)
                    .isSucceeded(isSucceeded).build());
        }
        return MemberHabitActiveResDto.builder().memberHabitList(memberHabitActiveDtoList).build();
    }

    @Override
    // 모든 비활성화된 사용자 해빗 리스트 조회
    public MemberHabitNonActiveResDto findAllNonActiveMemberHabit() {
        Member member = findMember();
        List<MemberHabit> memberHabitList = memberHabitRepository.findAllByIsDeletedAndMember(true,
            member);
        List<MemberHabitNonActiveDto> memberHabitNonActiveDtoList = new ArrayList<>();
        for (MemberHabit memberHabit : memberHabitList) {
            memberHabitNonActiveDtoList.add(
                MemberHabitNonActiveDto.builder().name(memberHabit.getHabit().getName())
                    .alias(memberHabit.getAlias()).memberHabitId(memberHabit.getId())
                    .icon(memberHabit.getIcon()).createdAt(memberHabit.getCreatedDatetime())
                    .succeededTime(memberHabit.getSucceededDatetime()).build());
        }
        return MemberHabitNonActiveResDto.builder().memberHabitList(memberHabitNonActiveDtoList)
            .build();
    }

    @Override
    // 해빗 검색어에 따른 검색
    public HabitMatchResDto findAllMatchHabit(String habitName) {
        List<Habit> habitList = habitRepository.findByNameContaining(habitName);
        List<HabitMatchDto> habitMatchDtoList = new ArrayList<>();
        for (Habit habit : habitList) {
            habitMatchDtoList.add(
                HabitMatchDto.builder().habitId(habit.getId()).habitName(habit.getName()).build());
        }
        return HabitMatchResDto.builder().habitList(habitMatchDtoList).build();
    }

    @Override
    public MemberHabitActiveSimpleResDto findAllActiveSimpleMemberHabit() {
        Member member = findMember();
        // 해당 사용자의 삭제되지 않은 사용자 해빗 리스트 조회
        List<MemberHabit> memberHabitList = memberHabitRepository.findAllByIsDeletedAndMember(false,
            member);

        List<MemberHabitActiveSimpleDto> memberHabitActiveSimpleDtoList = new ArrayList<>();
        // 활성화된 사용자 해빗 리스트를 순회하며
        for (MemberHabit memberHabit : memberHabitList) {
            List<Integer> dayList = habitDayRepository.findAllDayByMemberHabit(memberHabit);
            memberHabitActiveSimpleDtoList.add(
                MemberHabitActiveSimpleDto.builder().name(memberHabit.getHabit().getName())
                    .alias(memberHabit.getAlias()).memberHabitId(memberHabit.getId())
                    .dayList(dayList).build());
        }
        return MemberHabitActiveSimpleResDto.builder()
            .memberHabitList(memberHabitActiveSimpleDtoList).build();
    }

    @Override
    public List<MemberHabit> findAllActiveMemberHabitByMember(Member member) {
        return memberHabitRepository.findAllByIsDeletedAndMember(false, member);
    }

    @Override
    public void renewHabitRank() {
        List<ElasticDto> logList = elasticService.findAllElasticLog();
        Map<Long, Long> logMap = new ConcurrentHashMap<>();
        for (ElasticDto elasticDto : logList) {
            logMap.put(elasticDto.habitId(),
                logMap.getOrDefault(elasticDto.habitId(), 0L) + 1L);
        }
        PriorityQueue<long[]> priorityQueue = new PriorityQueue<>((o1, o2) -> {
            // index=1의 값에 따라 내림차순 정렬
            return Long.compare(o2[1], o1[1]);
        });

        for (Long id : logMap.keySet()) {
            priorityQueue.offer(new long[]{id, logMap.get(id)});
        }

        habitRecommendRepository.deleteAll();
        int totalCount = 10;
        Set<Long> set = new ConcurrentSkipListSet<>();
        while (!priorityQueue.isEmpty() && totalCount-- > 0) {
            long[] habitCount = priorityQueue.poll();
            Habit habit = habitRepository.findById(habitCount[0])
                .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_HABIT));
            habitRecommendRepository.save(HabitRecommend.builder().habit(habit).build());
            set.add(habitCount[0]);
            log.info(Arrays.toString(habitCount));
        }

        // 만약 총 10개가 되지 않으면
        if (totalCount > 0) {
            SecureRandom secureRandom = new SecureRandom();
            while (totalCount > 0) {
                Long randomNumber = secureRandom.nextInt(313) + 1L;
                if (set.contains(randomNumber)) {
                    continue;
                }
                totalCount--;
                set.add(randomNumber);
                Habit habit = habitRepository.findById(randomNumber)
                    .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_HABIT));
                habitRecommendRepository.save(HabitRecommend.builder().habit(habit).build());
            }
        }
    }

    @Override
    public HabitRankResDto findAllHabitRank() {
        List<HabitRecommend> habitRecommendList = habitRecommendRepository.findAllByOrderByIdAsc();
        List<HabitRecommendDto> habitRecommendDtoList = new ArrayList<>();
        for (HabitRecommend habitRecommend : habitRecommendList) {
            habitRecommendDtoList.add(
                HabitRecommendDto.builder().habitId(habitRecommend.getHabit().getId())
                    .name(habitRecommend.getHabit().getName()).build());
        }
        return HabitRankResDto.builder().habitList(habitRecommendDtoList).build();
    }

    // 주기에 따른 해빗 트래커 생성
    private void createHabitTrackerByPeriod(LocalDate startDay, LocalDate lastDay, Member member,
        MemberHabit memberHabit, int period) {
        for (LocalDate nowDay = startDay; !nowDay.isAfter(lastDay);
            nowDay = nowDay.plusDays(period)) {
            habitTrackerService.createHabitTrackerByPeriod(
                HabitTrackerCreateDto.builder().member(member).memberHabit(memberHabit)
                    .amount(period).targetDay(nowDay)
                    .build());
        }
    }

    // 요일에 따른 해빗 트래커 생성
    private void createHabitTrackerByDay(LocalDate startDay, LocalDate lastDay, Member member,
        MemberHabit memberHabit, int dayOfWeek) {
        for (LocalDate nowDay = startDay; !nowDay.isAfter(lastDay);
            nowDay = nowDay.plusDays(7L)) {
            habitTrackerService.createHabitTrackerByDay(
                HabitTrackerCreateDto.builder().member(member).memberHabit(memberHabit)
                    .amount(dayOfWeek).targetDay(nowDay)
                    .build());
        }
    }

    private Member findMember() {
        return memberRepository.findById(AuthUtil.getMemberIdFromAuthentication())
            .orElseThrow(() -> new HabitException(HabitErrorCode.NOT_FOUND_MEMBER));
    }
}
