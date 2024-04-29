package life.bareun.diary.global.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    private static final int AUTH_DATABASE_INDEX = 0;
    private static final int NOTIFICATION_DATABASE_INDEX = 1;

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    private static RedisStandaloneConfiguration standaloneConfiguration(
        String host,
        int port,
        int databaseIndex
    ) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        config.setDatabase(databaseIndex);
        return config;
    }


    @Bean(name = "authRedisTemplate")
    public RedisTemplate<Long, String> authRedisTemplate(
        @Qualifier("authRedisConnectionFactory")
        RedisConnectionFactory authRedisConnectionFactory
    ) {
        RedisTemplate<Long, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(authRedisConnectionFactory);
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> notificationRedisTemplate(
        @Qualifier("notificationRedisConnectionFactory")
        RedisConnectionFactory notificationRedisConnectionFactory
    ) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(notificationRedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

    @Bean(name = "authRedisConnectionFactory")
    public RedisConnectionFactory authRedisConnectionFactory() {
        RedisStandaloneConfiguration config = standaloneConfiguration(
            host,
            port,
            AUTH_DATABASE_INDEX
        );

        return new LettuceConnectionFactory(config);
    }

    @Bean(name = "notificationRedisConnectionFactory")
    public RedisConnectionFactory notificationRedisConnectionFactory() {
        RedisStandaloneConfiguration config = standaloneConfiguration(
            host,
            port,
            NOTIFICATION_DATABASE_INDEX
        );

        return new LettuceConnectionFactory(config);
    }
}
