package life.bareun.diary.recap.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RecapErrorCode {

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다"),

    NOT_FOUND_HABIT(HttpStatus.NOT_FOUND, "존재하지 않는 해빗입니다"),

    NOT_FOUND_MEMBER_HABIT(HttpStatus.NOT_FOUND, "존재하지 않는 사용자 해빗입니다"),

    NOT_FOUND_RECAP(HttpStatus.NOT_FOUND, "존재하지 않는 리캡입니다"),

    NOT_CREATE_RECAP_CONTENT(HttpStatus.NO_CONTENT, "리캡 요약이 정상적으로 처리되지 않았습니다");

    private final HttpStatus status;
    private final String message;
}
