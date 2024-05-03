package life.bareun.diary.global.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import life.bareun.diary.global.auth.exception.CustomSecurityException;
import life.bareun.diary.global.auth.exception.SecurityErrorCode;
import life.bareun.diary.global.auth.util.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomOAuth2FailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException exception
    ) throws IOException, ServletException {
        CustomSecurityException customException = new CustomSecurityException(
            SecurityErrorCode.UNAUTHENTICATED);

        ResponseUtil.writeError(response, customException);
    }
}
