package life.bareun.diary.streak.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum HabitDailyStreakErrorCode implements StreakErrorCode {
    NOT_FOUND_HABIT_DAILY_STREAK(HttpStatus.NOT_FOUND, "해당 날짜의 해빗 일일 스트릭이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
