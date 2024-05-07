package life.bareun.diary.global.notification.dto;

import lombok.Builder;

@Builder
public record NotificationDateDto(

    int year,

    int month,

    int day

) { }
