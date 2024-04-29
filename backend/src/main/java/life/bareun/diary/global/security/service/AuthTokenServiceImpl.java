package life.bareun.diary.global.security.service;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    private final RedisTemplate<Long, String> authRedisTemplate;

    @Autowired
    public AuthTokenServiceImpl(
        @Qualifier(value = "authRedisTemplate")
        RedisTemplate<Long, String> authRedisTemplate
    ) {
        this.authRedisTemplate = authRedisTemplate;
    }

    @Override
    @Transactional
    public void revoke(Long id, String value, Duration expiry) {
        authRedisTemplate.opsForValue().set(id, value, expiry);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExpired(Long id) {
        return findById(id) != null;
    }

    @Transactional(readOnly = true)
    protected String findById(Long id) {
        return authRedisTemplate.opsForValue().get(id);
    }
}
