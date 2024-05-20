package life.bareun.diary.habit.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum HabitErrorCode {

    NOT_FOUND_HABIT(HttpStatus.NOT_FOUND, "존재하지 않는 해빗입니다"),

    NOT_FOUND_MEMBER_HABIT(HttpStatus.NOT_FOUND, "존재하지 않는 사용자 해빗입니다"),

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다"),

    NOT_FOUND_HABIT_TRACKER(HttpStatus.NOT_FOUND, "존재하지 않는 해빗 트래커입니다."),

    INVALID_PARAMETER_HABIT_TRACKER(HttpStatus.BAD_REQUEST, "이미 완료된 해빗 트래커입니다."),

    INVALID_PARAMETER_MEMBER_HABIT(HttpStatus.BAD_REQUEST, "사용자 해빗 생성에 파라미터가 잘못되었습니다.");

    private final HttpStatus status;
    private final String message;
}
