package life.bareun.diary.streak.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum HabitTotalStreakErrorCode implements StreakErrorCode {
    NOT_FOUND_HABIT_TOTAL_STREAK(HttpStatus.NOT_FOUND, "해당 해빗의 전체 스트릭이 존재하지 않습니다."),
    ALREADY_EXISTED_HABIT_TOTAL_STREAK(HttpStatus.FORBIDDEN, "해당 해빗의 전체 스트릭이 이미 존재합니다.");

    private final HttpStatus status;
    private final String message;
}
