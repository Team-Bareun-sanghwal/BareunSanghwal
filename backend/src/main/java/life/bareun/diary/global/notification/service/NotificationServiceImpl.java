package life.bareun.diary.global.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import java.util.ArrayList;
import java.util.List;
import life.bareun.diary.global.notification.dto.NotificationDto;
import life.bareun.diary.global.notification.dto.NotificationStatusModifyDto;
import life.bareun.diary.global.notification.dto.NotificationTokenDto;
import life.bareun.diary.global.notification.dto.request.NotificationReqDto;
import life.bareun.diary.global.notification.dto.response.NotificationListResDto;
import life.bareun.diary.global.notification.entity.Notification;
import life.bareun.diary.global.notification.entity.NotificationToken;
import life.bareun.diary.global.notification.exception.NotificationErrorCode;
import life.bareun.diary.global.notification.exception.NotificationException;
import life.bareun.diary.global.notification.repository.NotificationRepository;
import life.bareun.diary.global.notification.repository.NotificationTokenRepository;
import life.bareun.diary.global.security.util.AuthUtil;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final NotificationTokenRepository notificationTokenRepository;

    private final MemberRepository memberRepository;

    @Override
    public void createToken(NotificationReqDto notificationReqDto) {
        if (notificationReqDto.notificationToken() == null) {
            throw new NotificationException(NotificationErrorCode.NOT_VALID_NOTIFICATION_TOKEN);
        }
        Long id = AuthUtil.getMemberIdFromAuthentication();
        notificationTokenRepository.save(
            NotificationToken.builder().id(id).token(notificationReqDto.notificationToken())
                .build());
    }

    @Override
    public void sendNotification() {
        String content = "알림입니다.";
        String id = "notificationToken:" + AuthUtil.getMemberIdFromAuthentication();
        NotificationTokenDto notificationTokenDto = notificationTokenRepository.findNotificationTokenById(
            id);
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

    @Override
    public NotificationListResDto findAllNotification() {
        Member member = memberRepository.findById(AuthUtil.getMemberIdFromAuthentication())
            .orElseThrow(() -> new NotificationException(NotificationErrorCode.NOT_FOUND_MEMBER));
        List<Notification> notificationList = notificationRepository
            .findAllByMember_OrderByCreatedDatetimeDescIdDesc(member);
        List<NotificationDto> notificationDtoList = new ArrayList<>();

        for (Notification notification : notificationList) {
            // 알림 100개 제한
            if (notificationDtoList.size() > 100) {
                break;
            }
            notificationDtoList.add(NotificationDto.builder().notificationId(notification.getId())
                .content(notification.getContent())
                .icon(notification.getNotificationCategory().getIcon())
                .isRead(notification.getIsRead()).createdAt(notification.getCreatedDatetime())
                .build());
        }

        // 확인한 알림은 모두 읽음 처리
        for (Notification notification : notificationList) {
            if (Boolean.TRUE.equals(notification.getIsRead())) {
                break;
            }
            notificationRepository.modifyIsRead(
                NotificationStatusModifyDto.builder().notificationId(notification.getId())
                    .status(true).build());
        }

        return NotificationListResDto.builder().notificationList(notificationDtoList).build();
    }
}
