package life.bareun.diary.global.notification.entity;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "notificationToken", timeToLive = 60 * 60 * 3) //3시간
@ToString
public class NotificationToken {
    @Id
    String id;

    String token;

    @Builder
    public NotificationToken(String id, String token) {
        this.id = id;
        this.token = token;
    }

}
