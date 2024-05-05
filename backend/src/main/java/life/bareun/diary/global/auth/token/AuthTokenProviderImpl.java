package life.bareun.diary.global.auth.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import life.bareun.diary.global.auth.embed.OAuth2Provider;
import life.bareun.diary.global.auth.exception.AuthException;
import life.bareun.diary.global.auth.exception.SecurityErrorCode;
import life.bareun.diary.global.auth.factory.OAuth2MemberPrincipalFactory;
import life.bareun.diary.global.auth.principal.OAuth2MemberPrincipal;
import life.bareun.diary.member.entity.embed.Role;
import life.bareun.diary.member.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public class AuthTokenProviderImpl implements AuthTokenProvider {

    private static final String CLAIM_MEMBER_ID = "memberId";
    private static final String CLAIM_ROLE = "role";
    private static final MacAlgorithm macAlgorithm = Jwts.SIG.HS256;
    private final SecretKey key;
    private final long accessTokenLifetimeSeconds;
    private final long refreshTokenLifetimeSeconds;



    public AuthTokenProviderImpl(
        String secret,
        long accessTokenLifetimeSeconds,
        long refreshTokenLifetimeSeconds
    ) {
        this.key = new SecretKeySpec(
            secret.getBytes(StandardCharsets.UTF_8),
            "HmacSHA256"
        );

        // 실행 시마다 키 값이 새로 생성된다.
        // this.key = Jwts.SIG.HS256.key().build();

        this.accessTokenLifetimeSeconds = accessTokenLifetimeSeconds;
        this.refreshTokenLifetimeSeconds = refreshTokenLifetimeSeconds;
    }


    @Override
    public String createAccessToken(Date from, String memberId, String role) {
        Date expiration = getAccessTokenExp(from);

        return Jwts.builder()
            .header()
            .add("alg", macAlgorithm.getId())
            .add("typ", "JWT")
            .and()
            .claim(CLAIM_MEMBER_ID, memberId)
            .claim(CLAIM_ROLE, role)
            .issuedAt(from)
            .expiration(expiration)
            .signWith(key, macAlgorithm)
            .encodePayload(true)
            .compact();
    }

    @Override
    public String createPrefixedAccessToken(Date from, String memberId, String role) {
        return ACCESS_TOKEN_PREFIX + createAccessToken(from, memberId, role);
    }

    @Override
    public String createRefreshToken(Date from, String memberId) {
        Date expiration = getRefreshTokenExp(from);

        // Refresh token은 access token 재발급만이 목적이므로
        // claim에 포함되는 정보를 최소화한다(역할 정보는 제외한다).
        return Jwts.builder()
            .header()
            .add("alg", macAlgorithm.getId())
            .add("typ", "JWT")
            .and()
            .claim(CLAIM_MEMBER_ID, memberId)
            .issuedAt(from)
            .expiration(expiration)
            .signWith(key, macAlgorithm)
            .encodePayload(true)
            .compact();
    }

    private Date getAccessTokenExp(Date currentDate) {
        return new Date(currentDate.getTime() + (accessTokenLifetimeSeconds * 1000L));
    }

    private Date getRefreshTokenExp(Date currentDate) {
        return new Date(currentDate.getTime() + (refreshTokenLifetimeSeconds * 1000L));
    }

    @Override
    public void validate(AuthToken authToken) throws JwtException {
        // token의 claim을 얻는 과정에서 예외 발생 ≡ token이 유효하지 않다
        // 단 ExpiredJwtException 예외는 무시되고, 반환값은 null이 된다.
        // 즉 예외로 취급되지 않으면서 검증 결과는 false가 된다.
        Claims claims = authToken.getClaims(this.key);
    }

    @Override
    public Authentication getAuthentication(AuthToken authToken) throws JwtException {
        try {
            validate(authToken);
        } catch (JwtException e) {
            throw e;
        }

        Claims claims = authToken.getClaims(key);

        Long id = Long.parseLong((String) claims.get("memberId"));
        Role role = Role.valueOf((String) claims.get("role"));
        OAuth2Provider oAuth2Provider = OAuth2Provider.PROTECTED;

        OAuth2MemberPrincipal oAuth2MemberPrincipal = OAuth2MemberPrincipalFactory.of(
            id,
            role,
            oAuth2Provider
        );

        return new OAuth2AuthenticationToken(
            oAuth2MemberPrincipal,
            oAuth2MemberPrincipal.getAuthorities(),
            oAuth2Provider.getValue() // OAuth2 Provider는 토큰에 포함되지 않고, 인증에 필요한 정보가 아니다.
        );
    }

    @Override
    public AuthToken tokenToAuthToken(String token) {
        return new AuthToken(token);
    }

    @Override
    public Long getMemberIdFromToken(AuthToken authToken) {
        try {
            validate(authToken);

            Claims claims = authToken.getClaims(this.key);
            String memberId = (String) claims.get(CLAIM_MEMBER_ID);

            return Long.parseLong(memberId);
        } catch (JwtException | NumberFormatException | NullPointerException e) {

            throw new AuthException(SecurityErrorCode.INVALID_AUTHENTICATION);
        }
    }

    @Override
    public Duration getExpiry(AuthToken refreshAuthToken) {
        Claims claims = refreshAuthToken.getClaims(key);

        Instant curr = new Date().toInstant();
        Instant exp = claims.getExpiration().toInstant();

        return Duration.between(curr, exp);
    }

    @Override
    public String addPrefix(String accessToken) {
        return (!accessToken.startsWith(ACCESS_TOKEN_PREFIX))
            ? (ACCESS_TOKEN_PREFIX + accessToken)
            : accessToken;
    }

    @Override
    public String removePrefix(String accessToken) {
        return accessToken.replace(ACCESS_TOKEN_PREFIX, "");
    }

    @Override
    public long getAccessTokenLifetime() {
        return accessTokenLifetimeSeconds;
    }

}

