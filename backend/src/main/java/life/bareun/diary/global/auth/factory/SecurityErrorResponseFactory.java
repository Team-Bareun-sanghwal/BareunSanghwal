package life.bareun.diary.global.auth.factory;

import life.bareun.diary.global.auth.exception.AuthException;
import life.bareun.diary.global.common.response.BaseResponse;
import org.springframework.http.ResponseEntity;

public class SecurityErrorResponseFactory {

    public static ResponseEntity<?> create(AuthException exception) {
        return ResponseEntity
            .status(
                exception.getErrorCode().getStatus()
            )
            .body(
                BaseResponse.error(
                    exception.getErrorCode().getStatus().value(),
                    exception.getMessage()
                )
            );
    }


}
