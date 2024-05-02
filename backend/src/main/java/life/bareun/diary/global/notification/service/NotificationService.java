package life.bareun.diary.global.notification.service;

import life.bareun.diary.global.notification.dto.request.NotificationReqDto;
import life.bareun.diary.global.notification.dto.response.NotificationListResDto;

public interface NotificationService {
    void createToken(NotificationReqDto notificationReqDto);

    void sendNotification();

    NotificationListResDto findAllNotification();
}
