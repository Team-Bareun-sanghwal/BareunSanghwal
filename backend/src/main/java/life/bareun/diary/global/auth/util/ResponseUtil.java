package life.bareun.diary.global.auth.util;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import life.bareun.diary.global.auth.config.SecurityConfig;
import life.bareun.diary.global.auth.exception.CustomSecurityException;
import life.bareun.diary.global.auth.factory.SecurityErrorResponseFactory;
import life.bareun.diary.global.common.response.BaseResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    private static final String HEADER = "Set-Cookie";
    private static final String CONTENT_TYPE_JSON = "application/json";

    public static void writeError(
        HttpServletResponse response,
        CustomSecurityException exception
    ) throws IOException {
        try (
            ServletOutputStream outputStream = response.getOutputStream()
        ) {
            response.setStatus(exception.getErrorCode().getStatus().value());
            response.setContentType(CONTENT_TYPE_JSON);
            outputStream.write(
                GsonUtil.toJsonBytesUtf8(
                    SecurityErrorResponseFactory.create(exception)
                )
            );
        } catch (IOException e) {
            throw e;
        }
    }

    public static void writeSuccess(
        HttpServletResponse response,
        BaseResponse<?> baseResponse
    ) throws IOException {
        GsonUtil.toJsonBytesUtf8(
            ResponseEntity
                .status(baseResponse.getStatus())
                .body(baseResponse)
        );

        try (
            ServletOutputStream outputStream = response.getOutputStream()
        ) {
            response.setStatus(baseResponse.getStatus());
            response.setContentType(CONTENT_TYPE_JSON);
            outputStream.write(
                GsonUtil.toJsonBytesUtf8(
                    ResponseEntity
                        .status(baseResponse.getStatus())
                        .body(baseResponse)
                )
            );
        } catch (IOException e) {
            throw e;
        }
    }

}
