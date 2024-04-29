package life.bareun.diary.global.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SecurityErrorCode {
    NO_OAUTH_INFO(HttpStatus.BAD_REQUEST, "OAuth2 provider 정보가 존재하지 않습니다."),
    BAD_OAUTH_INFO(HttpStatus.BAD_REQUEST, "잘못된 OAuth2 provider 정보입니다."),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    AUTHENTICATION_FAILURE(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰이 만료되었습니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "다시 로그인하십시오."),
    INVALID_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증 정보가 유효하지 않습니다."),
    NO_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰 발급을 위한 정보가 없습니다"),
    UNMATCHED_AUTHENTICATION(HttpStatus.BAD_REQUEST, "인증 정보가 일치하지 않습니다."),
    REVOKED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "로그아웃된 사용자입니다.");


    private HttpStatus status;
    private String message;
}
