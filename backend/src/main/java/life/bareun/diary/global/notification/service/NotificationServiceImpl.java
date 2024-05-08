package life.bareun.diary.global.notification.service;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.ApsAlert;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import life.bareun.diary.global.auth.util.AuthUtil;
import life.bareun.diary.global.notification.dto.NotificationDateDto;
import life.bareun.diary.global.notification.dto.NotificationDto;
import life.bareun.diary.global.notification.dto.NotificationPhraseDto;
import life.bareun.diary.global.notification.dto.NotificationResultTokenDto;
import life.bareun.diary.global.notification.dto.NotificationStatusModifyDto;
import life.bareun.diary.global.notification.dto.NotificationTokenDto;
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
import life.bareun.diary.member.entity.DailyPhrase;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.entity.MemberDailyPhrase;
import life.bareun.diary.member.repository.DailyPhraseRepository;
import life.bareun.diary.member.repository.MemberDailyPhraseRepository;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.streak.entity.StreakPhrase;
import life.bareun.diary.streak.repository.StreakPhraseRepository;
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

    private final MemberDailyPhraseRepository memberDailyPhraseRepository;

    private final DailyPhraseRepository dailyPhraseRepository;

    private final StreakPhraseRepository streakPhraseRepository;

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
            2L);

        // 현재 Redis에 존재하는 토큰 목록
        Map<Long, String> notificationTokenMap = findAllExistMessageToken();

        // 조건에 부합하는 사용자와 토큰, 내용 목록
        Map<Long, NotificationResultTokenDto> resultTokenDtoMap = verifyCondition(
            notificationCategoryId,
            notificationTokenMap, notificationCategory);

        if (resultTokenDtoMap == null) {
            throw new NotificationException(NotificationErrorCode.NOT_FOUND_NOTIFICATION_MEMBER);
        }

        for (Entry<Long, NotificationResultTokenDto> entry : resultTokenDtoMap.entrySet()) {
            log.info(entry.getKey() + " " + entry.getValue());

            NotificationResultTokenDto notificationResultTokenDto = entry.getValue();

            // 알림 생성
            createNotification(notificationResultTokenDto, notificationCategory);
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

    @Override
    public void sendContinuousStreakMember(Member member, int continuousStreak) {
        NotificationTokenDto notificationTokenDto = notificationTokenRepository.findNotificationTokenByMemberId(
            member.getId());
        NotificationCategory notificationCategory = notificationCategoryRepository.findById(5L)
            .orElseThrow();
        String content = notificationCategory.getContent();

        // 랜덤 응원 문구
        SecureRandom secureRandom = new SecureRandom();
        StreakPhrase streakPhrase = streakPhraseRepository.findById(secureRandom.nextInt(10) + 1L)
            .orElseThrow(
                () -> new NotificationException(NotificationErrorCode.NOT_FOUND_STREAK_PHRASE));
        createNotification(NotificationResultTokenDto.builder().member(member)
                .content(String.format(content, continuousStreak, streakPhrase.getPhrase()))
                .token(notificationTokenDto.token()).build(),
            notificationCategory);
    }

    private Map<Long, NotificationResultTokenDto> verifyCondition(Long notificationCategoryId,
        Map<Long, String> notificationTokenMap, NotificationCategory notificationCategory) {
        return switch (Math.toIntExact(notificationCategoryId)) {
            case 1 -> findAllNotificationLuckyPoint(notificationTokenMap, notificationCategory);
            case 2 -> findAllNotificationUnaccompanied(notificationTokenMap, notificationCategory);
            default -> findAllNotificationDailyPhrase(notificationTokenMap, notificationCategory);
        };
    }

    private Map<Long, NotificationResultTokenDto> findAllNotificationDailyPhrase(
        Map<Long, String> notificationTokenMap, NotificationCategory notificationCategory) {

        // 오늘 문구 일괄 수정
        // 모든 사용자 가져오기
        List<MemberDailyPhrase> memberDailyPhraseList = memberDailyPhraseRepository.findAll();

        // 모든 오늘의 문구 목록 가져오기
        List<DailyPhrase> dailyPhraseList = dailyPhraseRepository.findAll();

        SecureRandom secureRandom = new SecureRandom();
        for (MemberDailyPhrase memberDailyPhrase : memberDailyPhraseList) {
            int randomId = secureRandom.nextInt(dailyPhraseList.size());
            DailyPhrase dailyPhrase = dailyPhraseList.get(randomId);
            memberDailyPhraseRepository.modifyMemberDailyPhrase(
                NotificationPhraseDto.builder().dailyPhrase(dailyPhrase)
                    .memberDailyPhraseId(memberDailyPhrase.getId()).build());
        }

        String content = notificationCategory.getContent();
        return refineMemberWithDailyPhraseContent(memberDailyPhraseList, notificationTokenMap,
            content);

    }

    private Map<Long, NotificationResultTokenDto> findAllNotificationUnaccompanied(
        Map<Long, String> notificationTokenMap, NotificationCategory notificationCategory) {

        // 하나라도 미수행한 해빗 트래커가 있는 사람의 목록
        LocalDate localDate = LocalDate.now();
        List<Member> unaccompaniedMemberList = memberRepository.findAllUnaccompaniedMember(
            NotificationDateDto.builder().year(localDate.getYear()).month(localDate.getMonthValue())
                .day(localDate.getDayOfMonth()).build());

        String content = notificationCategory.getContent();
        return refineMemberWithDefaultContent(unaccompaniedMemberList, notificationTokenMap,
            content);
    }

    private Map<Long, NotificationResultTokenDto> findAllNotificationLuckyPoint(
        Map<Long, String> notificationTokenMap, NotificationCategory notificationCategory) {

        // 아직 포인트를 미수확한 사용자 리스트
        List<Member> unharvestedMemberList = memberRepository
            .findAllByLastHarvestedDateIsNullOrLastHarvestedDateIsBefore(LocalDate.now());

        String content = notificationCategory.getContent();
        return refineMemberWithDefaultContent(unharvestedMemberList, notificationTokenMap, content);
    }

    private Map<Long, NotificationResultTokenDto> refineMemberWithDailyPhraseContent(
        List<MemberDailyPhrase> memberDailyPhraseList,
        Map<Long, String> notificationTokenMap, String content) {
        Map<Long, NotificationResultTokenDto> resultTokenMap = new ConcurrentHashMap<>();
        for (MemberDailyPhrase memberDailyPhrase : memberDailyPhraseList) {
            if (notificationTokenMap.containsKey(memberDailyPhrase.getMember().getId())) {
                resultTokenMap.put(memberDailyPhrase.getMember().getId(),
                    NotificationResultTokenDto.builder().member(memberDailyPhrase.getMember())
                        .token(notificationTokenMap.get(memberDailyPhrase.getMember().getId()))
                        .content(
                            String.format(content, memberDailyPhrase.getMember().getNickname()))
                        .build());
            }
        }
        return resultTokenMap;
    }

    private Map<Long, NotificationResultTokenDto> refineMemberWithDefaultContent(
        List<Member> memberList,
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

    // 만약 알림 전송에 실패하면, 알림 저장까지 롤백되기 때문에 noRollbackFor을 걸어줌
    @Override
    @Transactional(noRollbackFor = NotificationException.class)
    public void createNotification(NotificationResultTokenDto notificationResultTokenDto,
        NotificationCategory notificationCategory) {
        log.info(notificationResultTokenDto.toString());
        // 알림 만들기
        notificationRepository.save(
            Notification.builder().member(notificationResultTokenDto.member())
                .content(notificationResultTokenDto.content())
                .notificationCategory(notificationCategory).isRead(false).build());

        sendNotificationAsync(notificationResultTokenDto);
    }

    private void sendNotificationAsync(NotificationResultTokenDto notificationResultTokenDto) {
        Message message = Message.builder().setToken(notificationResultTokenDto.token())
            .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "86400")
                .setNotification(
                    new WebpushNotification("bareun", notificationResultTokenDto.content(), "icon-url"))
                .build())
            .setAndroidConfig(
                AndroidConfig.builder()
                    .setTtl(86400)
                    .setNotification(AndroidNotification.builder()
                        .setTitle("bareun")
                        .setBody(notificationResultTokenDto.content())
                        .setClickAction("https://bareun.life/notification").build()).build()
            )
            .setApnsConfig(
                ApnsConfig.builder()
                    .putHeader("apns-expiration",
                        String.valueOf((System.currentTimeMillis() / 1000) + 86400))
                    .setAps(Aps.builder()
                        .setAlert(ApsAlert.builder().setTitle("bareun")
                            .setBody(notificationResultTokenDto.content()).build())
                        .setCategory("https://bareun.life/notification").build()).build()
            )
            .putData("url", "http://localhost:3000/notification")
            .build();
        try {
            FirebaseMessaging.getInstance().sendAsync(message).get();
        } catch (Exception e) {
            throw new NotificationException(NotificationErrorCode.FAIL_SEND_NOTIFICATION);
        }
    }

    private Map<Long, String> findAllExistMessageToken() {
        // 현재 Redis에 존재하는 토큰 목록
        return notificationTokenRepository.findAllNotificationToken();
    }

    private NotificationCategory findNotificationCategory(Long notificationCategoryId) {
        return notificationCategoryRepository.findById(
            notificationCategoryId).orElseThrow(
            () -> new NotificationException(NotificationErrorCode.NOT_VALID_NOTIFICATION_CATEGORY));
    }
}
