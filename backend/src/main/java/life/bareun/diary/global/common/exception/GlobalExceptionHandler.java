package life.bareun.diary.global.common.exception;

import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.global.elastic.exception.ElasticException;
import life.bareun.diary.global.notification.exception.NotificationException;
import life.bareun.diary.habit.exception.HabitException;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.recap.exception.RecapException;
import life.bareun.diary.streak.exception.StreakException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<?> memberExceptionHandler(MemberException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
            .body(BaseResponse.error(e.getErrorCode().getStatus().value(), e.getMessage()));
    }

    @ExceptionHandler(HabitException.class)
    public ResponseEntity<?> habitExceptionHandler(HabitException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
            .body(BaseResponse.error(e.getErrorCode().getStatus().value(), e.getMessage()));
    }

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<?> notificationExceptionHandler(NotificationException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
            .body(BaseResponse.error(e.getErrorCode().getStatus().value(), e.getMessage()));
    }

    @ExceptionHandler(RecapException.class)
    public ResponseEntity<?> recapExceptionHandler(RecapException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
            .body(BaseResponse.error(e.getErrorCode().getStatus().value(), e.getMessage()));
    }

    @ExceptionHandler(ElasticException.class)
    public ResponseEntity<?> ElasticExceptionHandler(ElasticException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
            .body(BaseResponse.error(e.getErrorCode().getStatus().value(), e.getMessage()));
    }

    @ExceptionHandler(StreakException.class)
    public ResponseEntity<?> streakExceptionHandler(StreakException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
            .body(BaseResponse.error(e.getErrorCode().getStatus().value(), e.getMessage()));
    }

    /**
     * 예외 코드를 unExpectedExceptionHandler 위에 작성해주세요.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unexpectedExceptionHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상 못한 오류가 발생했습니다. " + e.getMessage()));
    }
}
