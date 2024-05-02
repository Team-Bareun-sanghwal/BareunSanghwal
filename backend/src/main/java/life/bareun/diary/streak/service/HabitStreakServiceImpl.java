package life.bareun.diary.streak.service;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import life.bareun.diary.global.security.util.AuthUtil;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.habit.repository.HabitTrackerRepository;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.streak.entity.HabitDailyStreak;
import life.bareun.diary.streak.entity.HabitTotalStreak;
import life.bareun.diary.streak.entity.MemberTotalStreak;
import life.bareun.diary.streak.entity.embed.AchieveType;
import life.bareun.diary.streak.exception.HabitDailyStreakErrorCode;
import life.bareun.diary.streak.exception.HabitTotalStreakErrorCode;
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
@Transactional
@Service
@RequiredArgsConstructor
public class HabitStreakServiceImpl implements HabitStreakService {

    private final HabitTotalStreakRepository habitTotalStreakRepository;
    private final HabitDailyStreakRepository habitDailyStreakRepository;
    private final MemberTotalStreakRepository memberTotalStreakRepository;
    private final MemberDailyStreakRepository memberDailyStreakRepository;
    private final MemberRepository memberRepository;
    private final HabitTrackerRepository habitTrackerRepository;

    @Override
    public void createInitialHabitStreak(MemberHabit memberHabit) {
        habitTotalStreakRepository.save(
            HabitTotalStreak.builder()
                .memberHabit(memberHabit)
                .build()
        );

        /*
        초기 해빗 생성 시에 오늘 자와 내일 자 해빗 데일리 스트릭을 생성.
        해빗 데일리 스트릭을 생성하는 시점이 스케줄러의 작동 시점의 이후일 수 있으므로.
         */
        createHabitDailyStreak(memberHabit, LocalDate.now());
        createHabitDailyStreak(memberHabit, LocalDate.now().plusDays(1));
    }

    @Override
    public void createHabitDailyStreak(MemberHabit memberHabit, LocalDate date) {
        AtomicReference<AchieveType> achieveTypeToday = new AtomicReference<>(AchieveType.NOT_EXISTED);
        AtomicInteger currentStreakToday = new AtomicInteger(0);

        // 해당 날짜에 이미 해빗 데일리 스트릭이 존재하면 익셉션.
        habitDailyStreakRepository.findByMemberHabitAndCreatedDate(memberHabit, date)
            .ifPresent(habitDailyStreak -> {
                throw new StreakException(HabitDailyStreakErrorCode.ALREADY_EXISTED_HABIT_DAILY_STREAK);
            });

        // 해당 날짜에 해빗 트래커가 존재하면 AchieveType을 해빗 트래커가 존재하지만 아직 달성하지 않음으로 설정.
        habitTrackerRepository.findByMemberHabitAndCreatedYearAndCreatedMonthAndCreatedDay(
            memberHabit,
            date.getYear(),
            date.getMonth().getValue(),
            date.getDayOfMonth()
        ).ifPresent(habitTracker -> {
            achieveTypeToday.set(AchieveType.NOT_ACHIEVE);
        });

        /*
        하루 전의 해빗 일일 스트릭을 참조하여 AchieveType이 NOT_ACHIEVE가 아니면
        현재 연속 스트릭을 이어붙인다.
         */
        habitDailyStreakRepository.findByMemberHabitAndCreatedDate(
            memberHabit,
            date.minusDays(1)
        ).ifPresent(habitDailyStreakYesterday -> {
            if (!habitDailyStreakYesterday.getAchieveType().equals(AchieveType.NOT_ACHIEVE)) {
                currentStreakToday.set(habitDailyStreakYesterday.getCurrentStreak());
            }
        });

        habitDailyStreakRepository.save(
            HabitDailyStreak.builder()
                .memberHabit(memberHabit)
                .achieveType(achieveTypeToday.get())
                .currentStreak(currentStreakToday.get())
                .createdDate(date)
                .build()
        );

        /*
        해빗 데일리 스트릭 생성 시 해당 날짜에 해빗 트래커가 존재하면
        해빗 토탈 스트릭의 토탈 트래커 개수도 증가.
         */
        if (achieveTypeToday.get().equals(AchieveType.NOT_ACHIEVE)) {
            habitTotalStreakRepository.findByMemberHabit(memberHabit)
                .ifPresent(habitTotalStreak -> {
                    habitTotalStreak.modifyTotalTrackerCount(habitTotalStreak.getTotalTrackerCount() + 1);
                });
        }

        /*
         * TODO: 젼영빈 / 해당 날짜의 멤버 일일 스트릭의 토탈 트래커 수를 갱신하는 로직도 필요하다.
         *  해당 날짜의 멤버 일일 스트릭이 존재한다면 갱신해주어야겠지만,
         *  아직 존재하지 않는다면 멤버 일일 스트릭을 만드는 시점에 계산하여도 된다.
         *  멤버 일일 스트릭이 존재하는지 검사하고 존재한다면 토탈 트래커 수를 갱신하는 로직을 추가하자.
         */
    }

    @Override
    public void modifyHabitDailyStreak(MemberHabit memberHabit) {
        // TODO: 전영빈 / 구체적인 날짜의 지정이 필요하다면 파라메터로 바꿔주자.
        LocalDate today = LocalDate.now();

        HabitDailyStreak habitDailyStreakToday = habitDailyStreakRepository
            .findByMemberHabitAndCreatedDate(memberHabit, today)
            .orElseThrow(() -> new StreakException(HabitDailyStreakErrorCode.NOT_FOUND_HABIT_DAILY_STREAK));

        if (habitDailyStreakToday.getAchieveType().equals(AchieveType.NOT_EXISTED)) {
            throw new StreakException(HabitDailyStreakErrorCode.NOT_EXISTED_HABIT_TRACKER);
        } else if (habitDailyStreakToday.getAchieveType().equals(AchieveType.ACHIEVE)) {
            throw new StreakException(HabitDailyStreakErrorCode.ALREADY_ACHIEVE_DAILY_STREAM);
        }

        habitDailyStreakToday.modifyCurrentStreak(habitDailyStreakToday.getCurrentStreak() + 1);
        habitDailyStreakToday.modifyAchieveType(AchieveType.ACHIEVE);

        // 해빗 토탈 스트릭의 달성 값을 수정하면서 최장 스트릭 변동 가능 시 함께 수정.
        habitTotalStreakRepository.findByMemberHabit(memberHabit)
            .ifPresentOrElse(habitTotalStreak -> {
                habitTotalStreak.modifyAchieveTrackerCount(habitTotalStreak.getAchieveTrackerCount() + 1);
                habitTotalStreak.modifyLongestStreak(Math.max(
                    habitTotalStreak.getLongestStreak(),
                    habitDailyStreakToday.getCurrentStreak()
                ));
            }, () -> {
                throw new StreakException(HabitTotalStreakErrorCode.NOT_FOUND_HABIT_TOTAL_STREAK);
            });

        // 다음 날의 해빗 데일리 스트릭이 존재하면 currentStreak 값을 함께 수정.
        habitDailyStreakRepository.findByMemberHabitAndCreatedDate(memberHabit, today.plusDays(1))
            .ifPresent(habitDailyStreakTomorrow -> {
                habitDailyStreakTomorrow.modifyCurrentStreak(habitDailyStreakToday.getCurrentStreak());
            });

        /*
         * TODO: 전영빈 / 멤버 데일리 스트릭의 달성 트래커 수와 별 획득 필드,
         *  멤버 토탈 스트릭의 달성 트래커 수와 별 획득 수를 갱신하는 로직이 추가되어야 한다.
         *  상위 서비스에서 넣어주자.
         */
        // memberTotalStreakRepository.findByMember(getCurrentMember())
        //     .ifPresentOrElse(memberTotalStreak -> {
        //         memberTotalStreak.modifyAchieveStreakCount();
        //     })
        //         .orElseThrow(() -> new StreakException(MemberTotalStreakErrorCode.NOT_FOUND_MEMBER_TOTAL_STREAK))
    }

    @Override
    public void deleteHabitDailyStreak(MemberHabit memberHabit) {
        // TODO: 구체적인 날짜의 지정이 필요하다면 파라메터로 바꿔주자.
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        HabitTotalStreak habitTotalStreak = habitTotalStreakRepository.findByMemberHabit(memberHabit)
            .orElseThrow(() -> new StreakException(HabitTotalStreakErrorCode.NOT_FOUND_HABIT_TOTAL_STREAK));

        MemberTotalStreak memberTotalStreak = memberTotalStreakRepository.findByMember(getCurrentMember())
            .orElseThrow(() -> new StreakException(MemberTotalStreakErrorCode.NOT_FOUND_MEMBER_TOTAL_STREAK));

        /*
         * 내일부터 이후 한 달동안의 해빗 데일리 스트릭을 조회하여 삭제.
         * 해빗 데일리 스트릭의 생성 조건 상, 삭제될 수 있는 레코드는 오늘과 내일의 레코드 두 가지밖에 없다.
         * 오늘의 해빗 데일리 스트릭을 삭제할 경우, 다른 테이블이 함께 변동됨에 따라 별을 획득하게 될 가능성이 존재한다.
         * 서비스적으로 악용될 수 있을 것이라 생각되어 오늘자의 해빗 일일 스트릭은 삭제하지 않고 남겨두자.
         */
        habitDailyStreakRepository.findAllHabitDailyStreakByMemberHabitIdBetweenStartDateAndEndDate(
                memberHabit, tomorrow, tomorrow.plusMonths(1))
            .forEach(habitDailyStreak -> {
                /*
                 * AchieveType이 NOT_EXISTED가 아닌 경우,
                 * 즉 그 날에 해빗 트래커가 존재하는 경우 해빗 전체 트래커 개수를 1만큼 감소.
                 * 멤버 전체 스트릭에서 전체 트래커 개수를 1만큼 감소.
                 * 멤버 일일 스트릭에서 전체 트래커 개수를 1만큼 감소.
                 */
                if (!habitDailyStreak.getAchieveType().equals(AchieveType.NOT_EXISTED)) {
                    habitTotalStreak.modifyTotalTrackerCount(habitTotalStreak.getTotalTrackerCount() - 1);
                    memberTotalStreak.modifyTotalTrackerCount(memberTotalStreak.getTotalTrackerCount() - 1);

                    // TODO: 전영빈 / 멤버 일일 스트릭의 토탈 트래커 수를 업데이트하는 로직을 추가해야 한다.
                }

                habitDailyStreakRepository.delete(habitDailyStreak);
            });
    }

    private Member getCurrentMember() {
        return memberRepository.findById(AuthUtil.getMemberIdFromAuthentication())
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }
}
