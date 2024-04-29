package life.bareun.diary.global.notification.service;

import life.bareun.diary.global.notification.dto.request.NotificationReqDto;

public interface NotificationService {
    void createToken(NotificationReqDto notificationReqDto);

    void sendNotification();
}
