package life.bareun.diary.global.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import life.bareun.diary.global.security.factory.OAuth2MemberPrincipalFactory;
import life.bareun.diary.global.security.principal.OAuth2MemberPrincipal;
import life.bareun.diary.member.entity.embed.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public class AuthTokenProviderImpl implements AuthTokenProvider {

    private final SecretKey key;
    private final long accessTokenLifetimeSeconds;

    public AuthTokenProviderImpl(String secret, long accessTokenLifetime) {
        this.key = new SecretKeySpec(
            secret.getBytes(StandardCharsets.UTF_8),
            "HmacSha256"
        );

        this.accessTokenLifetimeSeconds = accessTokenLifetime;
    }

    @Override
    public String createToken(Long id, Role role) {
        Date currentDate = new Date();
        Date expiration = getExp(currentDate);

        return Jwts.builder()
            .header()
            .add("typ", "JWT")
            .and()
            .claim("memberId", Long.toString(id))
            .claim("role", role.name())
            .issuedAt(currentDate)
            .expiration(expiration)
            .signWith(key, Jwts.SIG.HS256)
            .encodePayload(true)
            .compact();
    }

    @Override
    public String createToken(OAuth2MemberPrincipal oAuth2MemberPrincipal) {
        Date currentDate = new Date();
        Date expiration = getExp(currentDate);

        return Jwts.builder()
            .header()
            .add("typ", "JWT")
            .and()
            .claim("memberId", oAuth2MemberPrincipal.getName())
            .claim("role", oAuth2MemberPrincipal.getAuthority())
            .issuedAt(currentDate)
            .expiration(expiration)
            .signWith(key, Jwts.SIG.HS256)
            .encodePayload(true)
            .compact();
    }

    private Date getExp(Date currentDate) {
        return new Date(currentDate.getTime() + (accessTokenLifetimeSeconds * 1000L));
    }

    @Override
    public boolean validate(AuthToken authToken) throws ExpiredJwtException {
        // token의 claim을 얻는 과정에서 예외 발생 ≡ token이 유효하지 않다
        Claims claims = authToken.getClaims(this.key);

        return claims != null;
    }

    @Override
    public Long getMemberIdFromAuthToken(AuthToken token) {
        Claims claims = token.getClaims(key);

        return Long.parseLong((String) claims.get("memberId"));
    }

    @Override
    public Authentication getOAuth2AuthenticationToken(AuthToken authToken) {
        if (validate(authToken)) {
            Claims claims = authToken.getClaims(key);

            Long id = Long.parseLong((String) claims.get("memberId"));
            Role role = Role.valueOf((String) claims.get("role"));

            OAuth2MemberPrincipal oAuth2MemberPrincipal = OAuth2MemberPrincipalFactory.of(id, role);
            return new OAuth2AuthenticationToken(
                oAuth2MemberPrincipal,
                oAuth2MemberPrincipal.getAuthorities(),
                "[PROTECTED]"
            );
        }

        return null;
    }
}

