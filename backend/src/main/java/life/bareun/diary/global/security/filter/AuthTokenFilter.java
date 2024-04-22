package life.bareun.diary.global.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import life.bareun.diary.global.security.exception.CustomSecurityException;
import life.bareun.diary.global.security.exception.SecurityErrorCode;
import life.bareun.diary.global.security.token.AuthToken;
import life.bareun.diary.global.security.token.AuthTokenProvider;
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

        String token = request.getHeader("Authorization");
        try {
            AuthToken authToken = new AuthToken(token);
            Authentication authentication = authTokenProvider.getOAuth2AuthenticationToken(
                authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException e) {
            throw new CustomSecurityException(SecurityErrorCode.EXPIRED_AUTHENTICATION);
        }

        filterChain.doFilter(request, response);
    }

}
