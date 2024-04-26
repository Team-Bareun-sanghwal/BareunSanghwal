package life.bareun.diary.global.common.exception;

import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.habit.exception.HabitException;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.recap.exception.RecapException;
import life.bareun.diary.streak.exception.StreakException;
import lombok.extern.slf4j.Slf4j;
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

    @ExceptionHandler(RecapException.class)
    public ResponseEntity<?> habitExceptionHandler(RecapException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
            .body(BaseResponse.error(e.getErrorCode().getStatus().value(), e.getMessage()));
    }

    @ExceptionHandler(StreakException.class)
    public ResponseEntity<?> streakExceptionHandler(StreakException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus())
            .body(BaseResponse.error(e.getErrorCode().getStatus().value(), e.getMessage()));
    }
}
