package life.bareun.diary.global.auth.util;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import life.bareun.diary.global.auth.config.SecurityConfig;
import life.bareun.diary.global.auth.exception.AuthException;
import life.bareun.diary.global.auth.factory.SecurityErrorResponseFactory;
import life.bareun.diary.global.common.response.BaseResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    private static final String HEADER = "Set-Cookie";
    private static final String CONTENT_TYPE_JSON = "application/json";

    public static void writeError(
        HttpServletResponse response,
        AuthException exception
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

    public static void addAccessTokenCookie(
        HttpServletResponse response,
        String accessToken,
        long maxAgeSeconds
    ) {
        response.addHeader(
            HEADER,
            createResponseCookieString(
                SecurityConfig.ACCESS_TOKEN_HEADER,
                accessToken,
                maxAgeSeconds
            )
        );

        System.out.println("AccessToken is added");

    }

    public static void addRefreshTokenCookie(
        HttpServletResponse response,
        String refreshToken,
        long maxAgeSeconds
    ) {
        response.addHeader(
            HEADER,
            createResponseCookieString(
                SecurityConfig.REFRESH_TOKEN_HEADER,
                refreshToken,
                maxAgeSeconds
            )
        );
    }

    private static String createResponseCookieString(
        String name,
        String value,
        long maxAgeSeconds
    ) {
        return ResponseCookie
            .from(name, value)
            .sameSite("None")
            .secure(true) // HTTPS만 허용
            .httpOnly(true) // HTTP 패킷으로만 쿠키를 받을 수 있음
            .path("/")
            .maxAge(maxAgeSeconds)
            .build()
            .toString();
    }

}
