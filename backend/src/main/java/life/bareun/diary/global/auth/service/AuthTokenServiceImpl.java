package life.bareun.diary.global.auth.service;

import io.jsonwebtoken.JwtException;
import java.time.Duration;
import life.bareun.diary.global.auth.exception.AuthException;
import life.bareun.diary.global.auth.exception.AuthErrorCode;
import life.bareun.diary.global.auth.token.AuthToken;
import life.bareun.diary.global.auth.token.AuthTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuthTokenServiceImpl implements AuthTokenService {

    private final StringRedisTemplate authRedisTemplate;
    private final AuthTokenProvider authTokenProvider;

    @Autowired
    public AuthTokenServiceImpl(
        @Qualifier(value = "authRedisTemplate")
        StringRedisTemplate authRedisTemplate,
        AuthTokenProvider authTokenProvider
    ) {
        this.authRedisTemplate = authRedisTemplate;
        this.authTokenProvider = authTokenProvider;
    }

    private static String toAccessTokenKey(Long id) {
        return String.format("accessToken:%d", id);
    }

    private static String toRefreshTokenKey(Long id) {
        return String.format("refreshToken:%d", id);
    }

    @Override
    @Transactional
    public void revokeAccessToken(Long id, AuthToken accessAuthToken) {
        // Token에서 삭제되어도 실제로 전달되는 것은 Header 값 자체이므로 prefix를 지워야 한다.
        // 근데 tokenToAuthToken 내부에서 지워줌
        Duration accessTokenExpiry = authTokenProvider.getExpiry(accessAuthToken);

        authRedisTemplate.opsForValue().set(
            toAccessTokenKey(id),
            accessAuthToken.toString(),
            accessTokenExpiry
        );

    }

    @Override
    @Transactional
    public void revokeRefreshToken(Long id, AuthToken refreshAuthToken) {
        Duration refreshTokenExpiry = authTokenProvider.getExpiry(refreshAuthToken);

        authRedisTemplate.opsForValue().set(
            toRefreshTokenKey(id),
            refreshAuthToken.toString(),
            refreshTokenExpiry
        );
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isRevokedRefreshToken(AuthToken refreshToken) {
        try {
            authTokenProvider.validate(refreshToken);
        } catch (JwtException e) {
            throw new AuthException(AuthErrorCode.INVALID_AUTHENTICATION);
        }

        Long id = authTokenProvider.getMemberIdFromToken(refreshToken);

        // 토큰 값 비교까지 해야 한다.
        return refreshToken.toString().equals(findRefreshTokenById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isRevokedAccessToken(AuthToken accessToken) {
        try {
            authTokenProvider.validate(accessToken);
        } catch (JwtException e) {
            throw new AuthException(AuthErrorCode.INVALID_AUTHENTICATION);
        }

        Long id = authTokenProvider.getMemberIdFromToken(accessToken);
        String accessTokenInRedis = findAccessTokenById(id);
        String accessTokenInRequest = accessToken.toString();

        // 토큰 값 비교까지 해야 한다.
        log.info("Access token in Redis: {}", accessTokenInRedis);
        log.info("Access token in request: {}", accessTokenInRequest);
        return accessTokenInRequest.equals(accessTokenInRedis);
    }

    @Transactional(readOnly = true)
    protected String findAccessTokenById(Long id) {
        return authRedisTemplate.opsForValue().get(toAccessTokenKey(id));
    }

    @Transactional(readOnly = true)
    protected String findRefreshTokenById(Long id) {
        return authRedisTemplate.opsForValue().get(toRefreshTokenKey(id));
    }


}
