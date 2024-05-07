package life.bareun.diary.streak.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberDailyStreakErrorCode implements StreakErrorCode {
    NOT_FOUND_MEMBER_DAILY_STREAK(HttpStatus.NOT_FOUND, "해당 날짜의 멤버 일일 스트릭이 존재하지 않습니다."),
    ALREADY_EXISTED_MEMBER_DAILY_STREAK(HttpStatus.FORBIDDEN, "해당 날짜의 멤버 일일 스트릭이 이미 존재합니다."),
    NO_EXISTED_TRACKER(HttpStatus.FORBIDDEN, "해당 날짜에 달성할 해빗이 존재하지 않습니다."),
    NOT_ENOUGH_TRACKER_TO_ACHIEVE(HttpStatus.FORBIDDEN, "이미 모든 트래커를 달성했습니다.");

    private final HttpStatus status;
    private final String message;
}
