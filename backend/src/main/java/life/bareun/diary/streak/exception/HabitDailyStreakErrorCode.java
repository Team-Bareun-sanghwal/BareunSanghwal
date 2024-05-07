package life.bareun.diary.streak.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum HabitDailyStreakErrorCode implements StreakErrorCode {
    NOT_FOUND_HABIT_DAILY_STREAK(HttpStatus.NOT_FOUND, "해당 날짜의 해빗 일일 스트릭이 존재하지 않습니다."),
    ALREADY_EXISTED_HABIT_DAILY_STREAK(HttpStatus.FORBIDDEN, "해당 날짜에 이미 해빗 일일 스트릭이 존재합니다."),
    NOT_EXISTED_HABIT_TRACKER(HttpStatus.FORBIDDEN, "해당 날짜에 설정되지 않은 해빗 트래커입니다."),
    ALREADY_ACHIEVE_DAILY_STREAM(HttpStatus.FORBIDDEN, "선택한 해빗을 이미 달성했습니다.");

    private final HttpStatus status;
    private final String message;
}
