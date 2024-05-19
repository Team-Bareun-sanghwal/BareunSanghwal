package life.bareun.diary.global.auth.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import life.bareun.diary.global.auth.config.SecurityConfig;
import life.bareun.diary.global.auth.exception.AuthException;
import life.bareun.diary.global.auth.exception.SecurityErrorCode;
import life.bareun.diary.global.auth.service.AuthTokenService;
import life.bareun.diary.global.auth.token.AuthToken;
import life.bareun.diary.global.auth.token.AuthTokenProvider;
import life.bareun.diary.global.auth.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNullApi;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    private final AuthTokenService authTokenService;
    private final AuthTokenProvider authTokenProvider;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
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
            // accessToken이 없어도 되는 경우는 앞에서 skip된다.
            // 토큰이 없는 경우 NullPointerException 발생
            String accessToken = request.getHeader(SecurityConfig.ACCESS_TOKEN_HEADER);
            log.debug("Requested token: {}", accessToken);

            // 1. accessToken 만료 확인
            // 여기서 "Bearer "가 제거된다.
            AuthToken accessAuthToken = authTokenProvider.tokenToAuthToken(accessToken);
            authTokenProvider.validate(accessAuthToken);
            
            // 2. 만료되지 않았으면 로그아웃한 사용자의 토큰인지 확인
            // Redis 등록은 "Bearer "를 포함하지 않는다.
            if (authTokenService.isRevokedAccessToken(accessAuthToken)) {
                throw new AuthException(SecurityErrorCode.REVOKED_ACCESS_TOKEN);
            }
            
            // 3. 정상 토큰이라면 SecurityContext 등록
            Authentication authentication = authTokenProvider.getAuthentication(accessAuthToken);
            log.info("Registered authentication token: {}", authentication);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            // 2. accessToken 만료 응답
            AuthException exception = new AuthException(
                SecurityErrorCode.EXPIRED_ACCESS_TOKEN
            );
            ResponseUtil.writeError(response, exception);
        } catch (JwtException e) {
            // ExpiredJwtException이 아닌 다른 JWT 예외가 발생한 상태
            // 키가 다르거나, 토큰이 변조됐거나, 토큰에 whitespace가 있거나, ...
            AuthException exception = new AuthException(
                SecurityErrorCode.INVALID_AUTHENTICATION
            );
            ResponseUtil.writeError(response, exception);
        } catch (NullPointerException e) {
            AuthException exception = new AuthException(
                SecurityErrorCode.UNAUTHENTICATED
            );
            ResponseUtil.writeError(response, exception);
        } catch (AuthException e) {
            // 로그아웃된 사용자의 액세스 토큰 응답
            ResponseUtil.writeError(response, e);
        }

    }

}
