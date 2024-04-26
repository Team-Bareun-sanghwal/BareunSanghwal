package life.bareun.diary.global.notification.controller;

import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.global.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<BaseResponse<String>> createNotification(@RequestBody String token) {
        notificationService.createToken(token);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(BaseResponse.success(HttpStatus.CREATED.value(), "알림 생성이 완료되었습니다.", null));
    }

}
