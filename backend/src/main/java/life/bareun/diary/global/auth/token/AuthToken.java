package life.bareun.diary.global.auth.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class AuthToken {

    private final String token;

    public Claims getClaims(SecretKey key) throws JwtException {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (IllegalArgumentException ignored) {
            log.debug("AuthToken cannot be null");
        }

        return claims;
    }

    @Override
    public String toString() {
        return token;
    }
}