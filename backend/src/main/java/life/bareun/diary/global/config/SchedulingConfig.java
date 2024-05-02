package life.bareun.diary.global.config;

import life.bareun.diary.habit.service.HabitService;
import life.bareun.diary.member.service.MemberService;
import life.bareun.diary.recap.service.RecapService;
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
    @Scheduled(cron = "0 59 23 L * ?")
    public void connectHabitList() {
        habitService.connectHabitList();
    }

    // 월 초에 리캡 생성하기
    @Scheduled(cron = "0 0 0 1 * ?")
    public void createRecap() {
        recapService.createRecap();
    }

    // 6시간마다
    @Scheduled(cron = "0 0 */6 * * ?")
    public void renewHabitRank() { habitService.renewHabitRank(); }

    // 매 월 00시에 무료 리커버리 지급
    @Scheduled(cron = "0 0 0 1 * ?")
    public void provideFreeRecovery() {
        memberService.grantFreeRecoveryToAllMembers();
    }
}
