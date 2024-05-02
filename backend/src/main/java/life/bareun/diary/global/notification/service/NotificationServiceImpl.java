package life.bareun.diary.global.notification.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import life.bareun.diary.global.notification.dto.NotificationDateDto;
import life.bareun.diary.global.notification.dto.NotificationDto;
import life.bareun.diary.global.notification.dto.NotificationResultTokenDto;
import life.bareun.diary.global.notification.dto.NotificationStatusModifyDto;
import life.bareun.diary.global.notification.dto.request.NotificationReqDto;
import life.bareun.diary.global.notification.dto.response.NotificationListResDto;
import life.bareun.diary.global.notification.entity.Notification;
import life.bareun.diary.global.notification.entity.NotificationCategory;
import life.bareun.diary.global.notification.entity.NotificationToken;
import life.bareun.diary.global.notification.exception.NotificationErrorCode;
import life.bareun.diary.global.notification.exception.NotificationException;
import life.bareun.diary.global.notification.repository.NotificationCategoryRepository;
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

    private final NotificationCategoryRepository notificationCategoryRepository;

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
    public void sendNotification(Long notificationCategoryId) {
        NotificationCategory notificationCategory = findNotificationCategory(
            notificationCategoryId);

        // 현재 Redis에 존재하는 토큰 목록
        Map<Long, String> notificationTokenDtoList = notificationTokenRepository
            .findAllNotificationToken();

        // 조건에 부합하는 사용자와 토큰, 내용 목록
        Map<Long, NotificationResultTokenDto> resultTokenDtoMap = verifyCondition(
            notificationCategoryId,
            notificationTokenDtoList, notificationCategory);

        if (resultTokenDtoMap == null) {
            throw new NotificationException(NotificationErrorCode.NOT_FOUND_NOTIFICATION_MEMBER);
        }

        for (Entry<Long, NotificationResultTokenDto> entry : resultTokenDtoMap.entrySet()) {
            log.info(entry.getKey() + " " + entry.getValue());

            NotificationResultTokenDto notificationResultTokenDto = entry.getValue();

            // 실제로 PWA 환경 배포 시 주석 해제
            // 알림 생성
            //            notificationRepository.save(
            //                Notification.builder().member(notificationResultTokenDto.member())
            //                    .content(notificationResultTokenDto.content())
            //                    .notificationCategory(notificationCategory).isRead(false).build());
            //
            //            Message message = Message.builder().setToken(notificationResultTokenDto.token())
            //                .setWebpushConfig(
            //                    WebpushConfig.builder().putHeader("ttl", "300")
            //                        .setNotification(
            //                            new WebpushNotification("bareun", notificationResultTokenDto.content()))
            //                        .build()).build();
            //            try {
            //                FirebaseMessaging.getInstance().sendAsync(message).get();
            //            } catch (Exception e) {
            //                throw new NotificationException(NotificationErrorCode.FAIL_SEND_NOTIFICATION);
            //            }
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

    private Map<Long, NotificationResultTokenDto> verifyCondition(Long notificationCategoryId,
        Map<Long, String> notificationTokenMap, NotificationCategory notificationCategory) {
        return switch (Math.toIntExact(notificationCategoryId)) {
            case 1 -> findAllNotificationLuckyPoint(notificationTokenMap, notificationCategory);
            case 2 -> findAllNotificationUnaccompanied(notificationTokenMap, notificationCategory);
            default -> null;
        };
    }

    private Map<Long, NotificationResultTokenDto> findAllNotificationUnaccompanied(
        Map<Long, String> notificationTokenMap, NotificationCategory notificationCategory) {

        // 하나라도 미수행한 해빗 트래커가 있는 사람의 목록
        LocalDate localDate = LocalDate.now();
        List<Member> unaccompaniedMemberList = memberRepository.findAllUnaccompaniedMember(
            NotificationDateDto.builder().year(localDate.getYear()).month(localDate.getMonthValue())
                .day(localDate.getDayOfMonth()).build());

        String content = notificationCategory.getContent();
        return refineMemberWithDefaultContent(unaccompaniedMemberList, notificationTokenMap, content);
    }

    private Map<Long, NotificationResultTokenDto> findAllNotificationLuckyPoint(
        Map<Long, String> notificationTokenMap, NotificationCategory notificationCategory) {

        // 아직 포인트를 미수확한 사용자 리스트
        List<Member> unharvestedMemberList = memberRepository
            .findAllByLastHarvestedDateIsNullOrLastHarvestedDateIsBefore(LocalDate.now());

        String content = notificationCategory.getContent();
        return refineMemberWithDefaultContent(unharvestedMemberList, notificationTokenMap, content);
    }

    private Map<Long, NotificationResultTokenDto> refineMemberWithDefaultContent(List<Member> memberList,
        Map<Long, String> notificationTokenMap, String content) {
        Map<Long, NotificationResultTokenDto> resultTokenMap = new ConcurrentHashMap<>();
        for (Member member : memberList) {
            if (notificationTokenMap.containsKey(member.getId())) {
                resultTokenMap.put(member.getId(),
                    NotificationResultTokenDto.builder().member(member)
                        .token(notificationTokenMap.get(member.getId())).content(content).build());
            }
        }
        return resultTokenMap;
    }

    private NotificationCategory findNotificationCategory(Long notificationCategoryId) {
        return notificationCategoryRepository.findById(
            notificationCategoryId).orElseThrow(
            () -> new NotificationException(NotificationErrorCode.NOT_VALID_NOTIFICATION_CATEGORY));
    }
}
