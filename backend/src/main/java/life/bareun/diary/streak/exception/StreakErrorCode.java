package life.bareun.diary.streak.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StreakErrorCode {

    NOT_FOUND_MEMBER_TOTAL_STREAK(HttpStatus.NOT_FOUND, "해당 멤버의 전체 스트릭이 존재하지 않습니다."),
    ALREADY_EXISTED_MEMBER_TOTAL_STREAK(HttpStatus.FORBIDDEN, "해당 멤버의 전체 스트릭이 이미 존재합니다."),
    NOT_FOUND_MEMBER_DAILY_STREAK(HttpStatus.NOT_FOUND, "해당 날짜의 멤버 일일 스트릭이 존재하지 않습니다."),
    ALREADY_EXISTED_MEMBER_DAILY_STREAK(HttpStatus.FORBIDDEN, "해당 날짜의 멤버 일일 스트릭이 이미 존재합니다.");

    private final HttpStatus status;
    private final String message;
}
