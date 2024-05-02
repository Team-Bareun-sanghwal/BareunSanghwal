package life.bareun.diary.global.notification.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record NotificationDto(

    Long notificationId,

    String icon,

    String content,

    Boolean isRead,

    LocalDateTime createdAt

) { }
