package life.bareun.diary.global.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import life.bareun.diary.global.notification.dto.NotificationTokenDto;
import life.bareun.diary.global.notification.dto.request.NotificationReqDto;
import life.bareun.diary.global.notification.entity.NotificationToken;
import life.bareun.diary.global.notification.exception.NotificationErrorCode;
import life.bareun.diary.global.notification.exception.NotificationException;
import life.bareun.diary.global.notification.repository.NotificationTokenRepository;
import life.bareun.diary.global.security.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationTokenRepository notificationTokenRepository;

    private static final String PREFIX = "[FCM]";

    @Override
    public void createToken(NotificationReqDto notificationReqDto) {
        if(notificationReqDto.notificationToken() == null) {
            throw new NotificationException(NotificationErrorCode.NOT_VALID_NOTIFICATION_TOKEN);
        }
        Long id = AuthUtil.getMemberIdFromAuthentication();
        System.out.println(notificationReqDto.notificationToken());
        notificationTokenRepository.save(
            NotificationToken.builder().id(id).token(notificationReqDto.notificationToken()).build());
    }

    @Override
    public void sendNotification() {
        String content = "알림입니다.";
        String id = "notificationToken:" + AuthUtil.getMemberIdFromAuthentication();
        NotificationTokenDto notificationTokenDto = notificationTokenRepository.findNotificationTokenById(
            id);
        System.out.println(notificationTokenDto);
        if (notificationTokenDto.token() == null) {
            throw new NotificationException(NotificationErrorCode.NOT_FOUND_NOTIFICATION_TOKEN);
        }
        Message message = Message.builder().setToken(notificationTokenDto.token()).setWebpushConfig(
            WebpushConfig.builder().putHeader("ttl", "300")
                .setNotification(new WebpushNotification("bareun", content)).build()).build();

        try {
            FirebaseMessaging.getInstance().sendAsync(message).get();
        } catch (Exception e) {
            throw new NotificationException(NotificationErrorCode.FAIL_SEND_NOTIFICATION);
        }

    }
}
