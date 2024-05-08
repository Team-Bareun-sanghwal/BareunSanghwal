package life.bareun.diary.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다"),
    NO_SUCH_MEMBER(HttpStatus.UNAUTHORIZED, "존재하지 않는 사용자의 인증 정보입니다."),
    NO_INITIAL_DATA_TREE(HttpStatus.NOT_FOUND, "회원가입을 위한 초기 나무 데이터가 없습니다."),
    NO_INITIAL_DATA_STREAK_COLOR(HttpStatus.NOT_FOUND, "회원가입을 위한 초기 스트릭 색상 데이터가 없습니다."),
    NO_INITIAL_DATA_TREE_COLOR(HttpStatus.NOT_FOUND, "회원가입을 위한 초기 나무 색상 데이터가 없습니다."),
    ALREADY_HARVESTED(HttpStatus.FORBIDDEN, "오늘은 이미 나무 포인트를 수확했습니다."),
    NO_SUCH_DAILY_PHRASE(HttpStatus.NOT_FOUND, "존재하지 않는 오늘의 문구의 정보입니다."),
    STREAK_RECOVERY_UNAVAILABLE(HttpStatus.UNPROCESSABLE_ENTITY, "보유한 스트릭 리커버리가 없습니다");


    private final HttpStatus status;
    private final String message;
}
