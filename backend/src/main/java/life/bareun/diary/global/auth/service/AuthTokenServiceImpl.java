package life.bareun.diary.global.auth.service;

import io.jsonwebtoken.JwtException;
import java.time.Duration;
import life.bareun.diary.global.auth.exception.CustomSecurityException;
import life.bareun.diary.global.auth.exception.SecurityErrorCode;
import life.bareun.diary.global.auth.token.AuthToken;
import life.bareun.diary.global.auth.token.AuthTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private final RedisTemplate<Long, String> authRedisTemplate;
    private final AuthTokenProvider authTokenProvider;

    @Autowired
    public AuthTokenServiceImpl(
        @Qualifier(value = "authRedisTemplate")
        RedisTemplate<Long, String> authRedisTemplate,
        AuthTokenProvider authTokenProvider
    ) {
        this.authRedisTemplate = authRedisTemplate;
        this.authTokenProvider = authTokenProvider;
    }

    @Override
    @Transactional
    public void revoke(Long id, String refreshToken) {
        AuthToken authRefreshToken = authTokenProvider.tokenToAuthToken(refreshToken);
        Duration expiry = authTokenProvider.getExpiry(authRefreshToken);

        authRedisTemplate.opsForValue().set(id, refreshToken, expiry);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isRevoked(String refreshToken) {
        AuthToken authToken = authTokenProvider.tokenToAuthToken(refreshToken);
        try {
            authTokenProvider.validate(authToken);
        } catch (JwtException e) {
            throw new CustomSecurityException(SecurityErrorCode.INVALID_AUTHENTICATION);
        }

        Long id = authTokenProvider.getMemberIdFromToken(authToken);

        // 토큰 값 비교까지 해야 한다.
        return findById(id).equals(refreshToken);
    }

    @Transactional(readOnly = true)
    protected String findById(Long id) {
        return authRedisTemplate.opsForValue().get(id);
    }
}
