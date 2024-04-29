package life.bareun.diary.global.security.config;


import life.bareun.diary.global.security.token.AuthTokenProvider;
import life.bareun.diary.global.security.token.AuthTokenProviderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JwtConfig {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token.lifetime}")
    private String accessTokenLifetimeSeconds;

    @Value("${jwt.refresh-token.lifetime}")
    private String refreshTokenLifetimeSeconds;

    @Bean
    public AuthTokenProvider authTokenProvider() {
        return new AuthTokenProviderImpl(
            secretKey,
            Long.parseLong(accessTokenLifetimeSeconds),
            Long.parseLong(refreshTokenLifetimeSeconds)
        );
    }
}

