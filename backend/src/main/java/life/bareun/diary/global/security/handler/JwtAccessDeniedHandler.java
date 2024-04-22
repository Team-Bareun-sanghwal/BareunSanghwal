package life.bareun.diary.global.security.handler;

import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import life.bareun.diary.global.common.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
        HttpServletRequest request,
        HttpServletResponse response,
        AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        HttpStatus status = HttpStatus.FORBIDDEN;

        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<BaseResponse<String>> responseBody = new ResponseEntity<>(
            new BaseResponse<>(
                status.value(),
                "권한이 부족합니다.",
                null
            ),
            status
        );

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(new Gson().toJson(responseBody).getBytes(StandardCharsets.UTF_8));
    }
}
