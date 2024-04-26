package life.bareun.diary.global.notification.repository;

import life.bareun.diary.global.notification.dto.NotificationTokenDto;

public interface NotificationTokenRepositoryCustom {
    NotificationTokenDto findNotificationTokenById(String id);
}
