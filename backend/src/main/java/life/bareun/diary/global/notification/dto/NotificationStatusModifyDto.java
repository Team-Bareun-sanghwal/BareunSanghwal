package life.bareun.diary.global.notification.dto;

import lombok.Builder;

@Builder
public record NotificationStatusModifyDto(

    Long notificationId,

    Boolean status

) { }
