package life.bareun.diary.global.config;

import java.time.LocalDate;
import life.bareun.diary.global.notification.service.NotificationService;
import life.bareun.diary.habit.service.HabitService;
import life.bareun.diary.member.service.MemberService;
import life.bareun.diary.recap.service.RecapService;
import life.bareun.diary.streak.service.StreakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulingConfig {

    private final HabitService habitService;
    private final RecapService recapService;
    private final MemberService memberService;
    private final StreakService streakService;
    private final NotificationService notificationService;

    // ┌───────────── second (0-59)
    // │ ┌───────────── minute (0–59)
    // │ │ ┌───────────── hour (0–23)
    // │ │ │ ┌───────────── day of the month (1–31)
    // │ │ │ │ ┌───────────── month (1–12)
    // │ │ │ │ │ ┌───────────── day of the week (0–6) (일~토, 시스템에 따라 7도 일요일일 수 있음)
    // │ │ │ │ │ │
    // │ │ │ │ │ │
    // │ │ │ │ │ │
    // │ │ │ │ │ │
    // * * * * * *

    // 월 말에 현재 활성화된 사용자 해빗 그대로 연장하기
    @Scheduled(cron = "0 0 23 L * ?")
    public void connectHabitList() {
        habitService.connectHabitList();
    }

    // 월 초에 리캡 생성하기
    @Scheduled(cron = "0 0 0 1 * ?")
    public void createRecap() {
        recapService.createRecap();
    }

    // 다음 날짜의 해빗 일일 스트릭과 멤버 일일 스트릭 생성
    @Scheduled(cron = "0 0 0 * * ?")
    public void createDailyStreak() {
        LocalDate today = LocalDate.now();

        memberService.findAllMember()
            .forEach(member -> {
                streakService.createDailyStreak(member, today);
            });
    }

    // 6시간마다
    @Scheduled(cron = "0 0 */6 * * ?")
    public void renewHabitRank() {
        habitService.renewHabitRank();
    }

    // 매 월 00시에 무료 리커버리 지급
    @Scheduled(cron = "0 0 0 1 * ?")
    public void provideFreeRecovery() {
        memberService.initStreakRecoveryForAllMembersMonthly();
    }

    // 10시 5분까지 행운 포인트 미수확한 사람들
    @Scheduled(cron = "0 5 22 * * ?")
    public void sentNotificationLuckyPoint() {
        notificationService.sendNotification(1L);
    }

    // 10시까지 하나의 해빗 트래커라도 미수행한 사람들
    @Scheduled(cron = "0 0 22 * * ?")
    public void sendNotificationUnaccompanied() {
        notificationService.sendNotification(2L);
    }

    // 새벽 12시 반, 오늘의 한 마디 알림
    @Scheduled(cron = "0 30 0 * * ?")
    public void sendNotificationDailyPhrase() {
        notificationService.sendNotification(4L);
    }
}
