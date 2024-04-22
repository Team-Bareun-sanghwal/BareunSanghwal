package life.bareun.diary.global.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SecurityErrorCode {
    NO_OAUTH_INFO(HttpStatus.BAD_REQUEST, "OAuth2 provider 정보가 존재하지 않습니다."),
    BAD_AUTH_INFO(HttpStatus.BAD_REQUEST, "잘못된 인증자 정보입니다."),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    AUTHENTICATION_FAILURE(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    EXPIRED_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증 정보가 만료되었습니다.");

    private HttpStatus status;
    private String message;
}
