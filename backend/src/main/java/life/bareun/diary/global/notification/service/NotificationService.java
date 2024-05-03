package life.bareun.diary.global.notification.service;

import life.bareun.diary.global.notification.dto.NotificationResultTokenDto;
import life.bareun.diary.global.notification.dto.request.NotificationReqDto;
import life.bareun.diary.global.notification.dto.response.NotificationListResDto;
import life.bareun.diary.global.notification.entity.NotificationCategory;
import life.bareun.diary.member.entity.Member;

public interface NotificationService {
    void createToken(NotificationReqDto notificationReqDto);

    void sendNotification(Long notificationCategoryId);

    NotificationListResDto findAllNotification();

    void createNotification(NotificationResultTokenDto notificationResultTokenDto,
        NotificationCategory notificationCategory);

    void sendContinuousStreakMember(Member member, int continuousStreak);
}
