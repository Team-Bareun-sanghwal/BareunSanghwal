package life.bareun.diary.streak.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberDailyStreakErrorCode implements StreakErrorCode {
    NOT_FOUND_MEMBER_DAILY_STREAK(HttpStatus.NOT_FOUND, "해당 날짜의 멤버 일일 스트릭이 존재하지 않습니다."),
    NOT_FOUND_MEMBER_DAILY_STREAK_YESTERDAY(HttpStatus.NOT_FOUND, "어제 날짜의 멤버 일일 스트릭이 존재하지 않습니다."),
    NOT_FOUND_MEMBER_DAILY_STREAK_TODAY(HttpStatus.NOT_FOUND, "오늘 날짜의 멤버 일일 스트릭이 존재하지 않습니다."),
    ALREADY_EXISTED_MEMBER_DAILY_STREAK_TODAY(HttpStatus.FORBIDDEN, "오늘 날짜의 멤버 일일 스트릭이 이미 존재합니다."),
    ALREADY_EXISTED_MEMBER_DAILY_STREAK_TOMORROW(HttpStatus.FORBIDDEN, "내일 날짜의 멤버 일일 스트릭이 이미 존재합니다.");

    private final HttpStatus status;
    private final String message;
}
