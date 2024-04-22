package life.bareun.diary.global.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import life.bareun.diary.global.common.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException
    ) throws IOException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<BaseResponse<String>> responseBody = new ResponseEntity<>(
            new BaseResponse<>(
                status.value(),
                "인증에 실패했습니다.",
                null
            ),
            status
        );

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(new Gson().toJson(responseBody).getBytes(StandardCharsets.UTF_8));

    }

}
