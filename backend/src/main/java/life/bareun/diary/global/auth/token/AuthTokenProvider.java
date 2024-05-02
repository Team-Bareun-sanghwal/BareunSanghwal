package life.bareun.diary.global.auth.token;

import io.jsonwebtoken.JwtException;
import java.time.Duration;
import java.util.Date;
import org.springframework.security.core.Authentication;

// 토큰 검증, Authentication 객체 생성
public interface AuthTokenProvider {

    String ACCESS_TOKEN_PREFIX = "Bearer ";

    String createAccessToken(Date from, String memberId, String role);

    String createPrefixedAccessToken(Date from, String memberId, String role);

    String createRefreshToken(Date from, String id);

    void validate(AuthToken authToken) throws JwtException;

    Authentication getAuthentication(AuthToken authToken);

    AuthToken tokenToAuthToken(String token);

    Long getMemberIdFromToken(AuthToken authToken);

    Duration getExpiry(AuthToken refreshAuthToken);

    String addPrefix(String accessToken);

    String removePrefix(String accessToken);


}
