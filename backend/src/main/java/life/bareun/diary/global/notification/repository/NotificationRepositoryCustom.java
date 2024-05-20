package life.bareun.diary.global.notification.repository;

import life.bareun.diary.global.notification.dto.NotificationStatusModifyDto;

public interface NotificationRepositoryCustom {
    void modifyIsRead(NotificationStatusModifyDto notificationStatusModifyDto);
}
