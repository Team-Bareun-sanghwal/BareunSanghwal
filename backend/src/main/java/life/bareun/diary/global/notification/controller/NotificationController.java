package life.bareun.diary.global.notification.controller;

import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.global.notification.dto.request.NotificationReqDto;
import life.bareun.diary.global.notification.dto.response.NotificationListResDto;
import life.bareun.diary.global.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<BaseResponse<String>> createNotificationToken(
        @RequestBody NotificationReqDto notificationReqDto) {
        notificationService.createToken(notificationReqDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(BaseResponse.success(HttpStatus.CREATED.value(), "알림 토큰 생성이 완료되었습니다.", null));
    }

    @GetMapping("/send")
    public ResponseEntity<BaseResponse<String>> sendNotification() {
        notificationService.sendNotification(1L);
        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse.success(HttpStatus.OK.value(), "알림 생성이 완료되었습니다.", null));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<NotificationListResDto>> findAllNotification() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse.success(HttpStatus.OK.value(), "알림 목록 조회가 완료되었습니다.",
                notificationService.findAllNotification()));
    }

    @GetMapping("/read")
    public ResponseEntity<BaseResponse<String>> modifyNotificationStatus() {
        notificationService.modifyNotificationStatus();
        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse.success(HttpStatus.OK.value(), "알림 상태 변경이 완료되었습니다.",
                null));
    }

}
