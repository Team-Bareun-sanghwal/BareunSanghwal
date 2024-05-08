package life.bareun.diary.habit.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class HabitServiceImplTest {

    @Autowired
    private HabitService habitService;

    @Test
    @DisplayName("현재 활성화된 사용자 해빗 유지 갱신")
    void connectHabitList() {
        
    }

    @Test
    @DisplayName("랭킹 현재 기준으로 갱신하기")
    void renewHabitRank() {
        habitService.renewHabitRank();
    }
}