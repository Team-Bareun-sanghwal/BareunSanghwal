package life.bareun.diary.global.auth.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import life.bareun.diary.global.auth.config.SecurityConfig;
import life.bareun.diary.global.auth.exception.CustomSecurityException;
import life.bareun.diary.global.auth.exception.SecurityErrorCode;
import life.bareun.diary.global.auth.token.AuthToken;
import life.bareun.diary.global.auth.token.AuthTokenProvider;
import life.bareun.diary.global.auth.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    private final AuthTokenProvider authTokenProvider;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        // System.out.println("PathInfo: " + request.getPathInfo());
        // System.out.println("ContextPath: " + request.getContextPath());
        // System.out.println("ServletPath: " + request.getServletPath());
        log.debug("PathInfo: {}", request.getPathInfo());
        log.debug("ContextPath: {}", request.getContextPath());
        log.debug("ServletPath: {}", request.getServletPath());

        String requestPath = request.getServletPath();

        // 인증이 필요 없는 요청 경로 허용
        // 로그인(회원가입), accessToken 요청 등이 여기에 해당한다.
        if (requestPath.startsWith("/login") || requestPath.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 1. accessToken 만료 확인
            String accessToken = request.getHeader(SecurityConfig.ACCESS_TOKEN_HEADER);
            log.debug("Requested token: {}", accessToken);

            if (accessToken != null) {
                accessToken = authTokenProvider.removePrefix(accessToken);
            }

            AuthToken accessAuthToken = authTokenProvider.tokenToAuthToken(accessToken);
            authTokenProvider.validate(accessAuthToken);

            Authentication authentication = authTokenProvider.getAuthentication(accessAuthToken);
            log.debug("Registered authentication token: {}", authentication);
            System.out.println("Registered authentication token: " + authentication);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            // 2. accessToken 만료 응답
            CustomSecurityException exception = new CustomSecurityException(
                SecurityErrorCode.EXPIRED_ACCESS_TOKEN
            );
            ResponseUtil.respondError(response, exception);
        } catch (JwtException e) {
            // ExpiredJwtException이 아닌 다른 JWT 예외가 발생한 상태
            // 키가 다르거나, 변조됐거나, ....
            CustomSecurityException exception = new CustomSecurityException(
                SecurityErrorCode.INVALID_AUTHENTICATION
            );
            ResponseUtil.respondError(response, exception);
        }
    }

}
