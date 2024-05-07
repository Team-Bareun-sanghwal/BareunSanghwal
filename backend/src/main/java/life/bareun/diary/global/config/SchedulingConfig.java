package life.bareun.diary.global.config;

import java.time.LocalDate;
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
}
