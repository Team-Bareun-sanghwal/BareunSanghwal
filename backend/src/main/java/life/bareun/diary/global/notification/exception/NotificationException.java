package life.bareun.diary.global.notification.exception;

import lombok.Getter;

@Getter
public class NotificationException extends RuntimeException {

    private final NotificationErrorCode errorCode;
    private final String message;

    public NotificationException(NotificationErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
