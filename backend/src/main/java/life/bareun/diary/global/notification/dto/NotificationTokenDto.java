package life.bareun.diary.global.notification.dto;

import lombok.Builder;

@Builder
public record NotificationTokenDto(

    Long id,

    String token

) { }
