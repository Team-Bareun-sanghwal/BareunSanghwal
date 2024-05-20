package life.bareun.diary.global.notification.dto.request;

import lombok.Builder;

@Builder
public record NotificationReqDto(

    String notificationToken

) { }
