package life.bareun.diary.global.security.util;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.global.security.exception.CustomSecurityException;
import life.bareun.diary.global.security.factory.SecurityErrorResponseFactory;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static void respondError(
        HttpServletResponse response,
        CustomSecurityException exception
    ) throws IOException {
        try (
            ServletOutputStream outputStream = response.getOutputStream()
        ) {
            response.setStatus(exception.getErrorCode().getStatus().value());
            response.setContentType("application/json");
            outputStream.write(
                GsonUtil.toJsonBytesUtf8(
                    SecurityErrorResponseFactory.create(exception)
                )
            );
        } catch (IOException e) {
            throw e;
        }
    }

    public static void respondSuccess(
        HttpServletResponse response,
        BaseResponse<?> baseResponse
    ) throws IOException {
        GsonUtil.toJsonBytesUtf8(ResponseEntity
            .status(baseResponse.getStatus())
            .body(baseResponse));

        try (
            ServletOutputStream outputStream = response.getOutputStream()
        ) {
            response.setStatus(baseResponse.getStatus());
            response.setContentType("application/json");
            outputStream.write(
                GsonUtil.toJsonBytesUtf8(ResponseEntity
                    .status(baseResponse.getStatus())
                    .body(baseResponse))
            );
        } catch (IOException e) {
            throw e;
        }
    }

}
