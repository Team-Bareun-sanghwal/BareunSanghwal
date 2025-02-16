package life.bareun.diary.global.notification.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NotificationErrorCode {

    NOT_FOUND_ADMIN_SDK(HttpStatus.NOT_FOUND, "존재하지 않는 Admin SDK입니다."),

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),

    NOT_FOUND_NOTIFICATION_TOKEN(HttpStatus.NOT_FOUND, "존재하지 않는 사용자 토큰입니다."),

    NOT_VALID_NOTIFICATION_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 알림 토큰입니다."),

    NOT_VALID_NOTIFICATION_CATEGORY(HttpStatus.BAD_REQUEST, "유효하지 않은 알림 카테고리입니다."),

    NOT_FOUND_NOTIFICATION_MEMBER(HttpStatus.NOT_FOUND, "알림을 전송할 수 있는 사용자가 존재하지 않습니다."),

    NOT_FOUND_STREAK_PHRASE(HttpStatus.NOT_FOUND, "존재하지 않는 스트릭 문구입니다.");

    private final HttpStatus status;
    private final String message;
}
