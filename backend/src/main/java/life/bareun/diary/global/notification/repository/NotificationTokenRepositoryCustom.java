package life.bareun.diary.global.notification.repository;

import java.util.Map;

public interface NotificationTokenRepositoryCustom {
    Map<Long, String> findAllNotificationToken();
}
