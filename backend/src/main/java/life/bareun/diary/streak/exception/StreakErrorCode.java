package life.bareun.diary.streak.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StreakErrorCode {

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 멤버의 스트릭입니다.");

    private final HttpStatus status;
    private final String message;
}
