package life.bareun.diary.global.security.token;

import io.jsonwebtoken.JwtException;
import java.time.Duration;
import java.util.Date;
import life.bareun.diary.global.security.principal.OAuth2MemberPrincipal;
import org.springframework.security.core.Authentication;

// 토큰 검증, Authentication 객체 생성
public interface AuthTokenProvider {

    String createAccessToken(Date from, String memberId, String role);

    String createRefreshToken(Date from, String id);

    void validate(AuthToken authToken) throws JwtException;

    Authentication getAuthentication(AuthToken authToken);

    AuthToken tokenToAuthToken(String token);

    Long getMemberIdFromToken(AuthToken authToken);

    Duration getExpiry(AuthToken refreshAuthToken);
}
