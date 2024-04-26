package life.bareun.diary.global.notification.repository;

import java.util.Map;
import life.bareun.diary.global.notification.dto.NotificationTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class NotificationTokenRepositoryCustomImpl implements NotificationTokenRepositoryCustom {

    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Object> hashOperations;

    public NotificationTokenRepositoryCustomImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public NotificationTokenDto findNotificationTokenById(String id) {
        Map<String, Object> entries = hashOperations.entries(id);
        return NotificationTokenDto.builder().id(id).token((String) entries.get("token")).build();

    }
}
