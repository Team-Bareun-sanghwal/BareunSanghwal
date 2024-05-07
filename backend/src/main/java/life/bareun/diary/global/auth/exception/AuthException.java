package life.bareun.diary.global.auth.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private final SecurityErrorCode errorCode;
    private final String message;

    public AuthException(SecurityErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
