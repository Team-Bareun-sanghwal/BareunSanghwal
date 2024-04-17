package life.bareun.diary.global.common.exception;

import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.member.exception.MemberException;
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
}
