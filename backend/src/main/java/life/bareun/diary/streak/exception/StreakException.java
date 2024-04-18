package life.bareun.diary.streak.exception;

import lombok.Getter;

@Getter
public class StreakException extends RuntimeException {

    private final StreakErrorCode errorCode;
    private final String message;

    public StreakException(StreakErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

}
