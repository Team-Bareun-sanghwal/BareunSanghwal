package life.bareun.diary.habit.exception;

import lombok.Getter;

@Getter
public class HabitException extends RuntimeException {

    private final HabitErrorCode errorCode;
    private final String message;

    public HabitException(HabitErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
