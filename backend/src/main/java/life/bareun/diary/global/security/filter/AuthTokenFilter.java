package life.bareun.diary.global.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import life.bareun.diary.global.security.exception.CustomSecurityException;
import life.bareun.diary.global.security.exception.SecurityErrorCode;
import life.bareun.diary.global.security.token.AuthToken;
import life.bareun.diary.global.security.token.AuthTokenProvider;
import life.bareun.diary.global.security.util.ResponseUtil;
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
        System.out.println("PathInfo: " + request.getPathInfo());
        System.out.println("ContextPath: " + request.getContextPath());
        System.out.println("ServletPath: " + request.getServletPath());

        String requestPath = request.getServletPath();

        if (requestPath.startsWith("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
        try {
            log.debug("Requested token: {}", token);
            // System.out.println("Request token: " + token);

            AuthToken authToken = new AuthToken(token);
            Authentication authentication = authTokenProvider.getOAuth2AuthenticationToken(
                authToken
            );
            log.debug("Registered authentication token: {}", authentication);
            // System.out.println("Registered authentication token: " + authentication);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            CustomSecurityException exception = new CustomSecurityException(
                SecurityErrorCode.EXPIRED_AUTHENTICATION);
            ResponseUtil.respondError(response, exception);

        } catch (JwtException e) {
            CustomSecurityException exception = new CustomSecurityException(
                SecurityErrorCode.INVALID_AUTHENTICATION);

            ResponseUtil.respondError(response, exception);
        }
    }

}
