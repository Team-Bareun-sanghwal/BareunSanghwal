package life.bareun.diary.global.security.service;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthTokenServiceImpl implements AuthTokenService {

    private final RedisTemplate<Long, String> redisTemplate;

    @Override
    @Transactional
    public void add(Long id, String value, Duration expiry) {
        redisTemplate.opsForValue().set(id, value, expiry);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExpired(Long id) {
        return findById(id) != null;
    }

    @Transactional(readOnly = true)
    private String findById(Long id) {
        return redisTemplate.opsForValue().get(id);
    }
}
