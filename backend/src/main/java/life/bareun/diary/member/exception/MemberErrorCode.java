package life.bareun.diary.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다"),
    NO_SUCH_USER(HttpStatus.UNAUTHORIZED, "존재하지 않는 사용자입니다.");

    private final HttpStatus status;
    private final String message;
}
