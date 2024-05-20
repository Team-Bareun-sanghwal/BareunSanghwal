package life.bareun.diary.global.notification.dto;

import life.bareun.diary.member.entity.Member;
import lombok.Builder;

@Builder
public record NotificationResultTokenDto(

    Member member,

    String token,

    String content

) { }
