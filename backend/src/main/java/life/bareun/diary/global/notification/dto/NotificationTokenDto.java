package life.bareun.diary.global.notification.dto;

import lombok.Builder;

@Builder
public record NotificationTokenDto(

    String id,

    String token

) { }
