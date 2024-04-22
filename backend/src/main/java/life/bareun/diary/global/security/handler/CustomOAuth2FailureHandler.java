package life.bareun.diary.global.security.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import life.bareun.diary.global.security.exception.CustomSecurityException;
import life.bareun.diary.global.security.exception.SecurityErrorCode;

public class CustomOAuth2FailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException exception
    ) throws IOException, ServletException {
        throw new CustomSecurityException(SecurityErrorCode.UNAUTHENTICATED);
    }
}
