package life.bareun.diary.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다"),
    NO_SUCH_MEMBER(HttpStatus.UNAUTHORIZED, "존재하지 않는 사용자의 인증 정보입니다.");

    private final HttpStatus status;
    private final String message;
}
