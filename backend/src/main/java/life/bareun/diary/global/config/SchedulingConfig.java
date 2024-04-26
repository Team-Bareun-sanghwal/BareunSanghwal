package life.bareun.diary.global.config;

import life.bareun.diary.habit.service.HabitService;
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
}
