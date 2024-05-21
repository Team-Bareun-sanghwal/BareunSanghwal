package life.bareun.diary.global.notification.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import life.bareun.diary.global.notification.dto.NotificationTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class NotificationTokenRepositoryCustomImpl implements NotificationTokenRepositoryCustom {

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOperations;

    public NotificationTokenRepositoryCustomImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    private NotificationTokenDto findNotificationTokenById(String id) {
        Map<String, Object> entries = hashOperations.entries(id);
        return NotificationTokenDto.builder().id(Long.parseLong(id.substring(18)))
            .token((String) entries.get("token")).build();
    }

    @Override
    public NotificationTokenDto findNotificationTokenByMemberId(String memberId) {
        return findNotificationTokenById(memberId);
    }

    @Override
    public Map<Long, String> findAllNotificationToken() {
        // ScanOptions 객체를 생성하여 스캔할 때 사용할 패턴 설정
        ScanOptions options = ScanOptions.scanOptions().match("*notificationToken:*").build();

        // Redis 콜백을 사용하여 SCAN 실행
        Map<Long, String> notificationTokenMap = new ConcurrentHashMap<>();
        redisTemplate.execute((RedisCallback<Void>) connection -> {
            Cursor<byte[]> cursor = connection.scan(options);
            while (cursor.hasNext()) {
                NotificationTokenDto notificationTokenDto = findNotificationTokenById(
                    new String(cursor.next()));
                notificationTokenMap.put(notificationTokenDto.id(), notificationTokenDto.token());
            }
            cursor.close();
            return null;
        });
        return notificationTokenMap;
    }
}
