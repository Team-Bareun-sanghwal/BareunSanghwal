package life.bareun.diary.global.security.factory;

import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.global.security.exception.CustomSecurityException;
import org.springframework.http.ResponseEntity;

public class SecurityErrorResponseFactory {

    public static ResponseEntity<?> create(CustomSecurityException exception) {
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
