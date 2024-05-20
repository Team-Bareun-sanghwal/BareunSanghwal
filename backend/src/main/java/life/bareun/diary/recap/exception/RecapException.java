package life.bareun.diary.recap.exception;

import lombok.Getter;

@Getter
public class RecapException extends RuntimeException {

    private final RecapErrorCode errorCode;
    private final String message;

    public RecapException(RecapErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
