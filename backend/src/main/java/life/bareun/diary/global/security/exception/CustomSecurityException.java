package life.bareun.diary.global.security.exception;

import lombok.Getter;

@Getter
public class CustomSecurityException extends RuntimeException {

    private final SecurityErrorCode errorCode;
    private final String message;

    public CustomSecurityException(SecurityErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
