package life.bareun.diary.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import life.bareun.diary.global.security.principal.OAuth2MemberPrincipal;
import life.bareun.diary.global.security.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

// SimpleUrlAuthenticationSuccessHandler는 성공 후 특정 URL로 redirect하기 위해 쓰인다.
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final AuthTokenProvider authTokenProvider;

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException, ServletException {

        System.out.println(authentication);

        String token = authTokenProvider.createToken(
            (OAuth2MemberPrincipal) authentication.getPrincipal());
        System.out.println("Token: " + token);

        // 응답은 여기서
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(token.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}

