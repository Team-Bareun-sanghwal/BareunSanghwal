package life.bareun.diary.global.notification.dto;

import life.bareun.diary.member.entity.DailyPhrase;
import lombok.Builder;

@Builder
public record NotificationPhraseDto(

    Long memberDailyPhraseId,

    DailyPhrase dailyPhrase

) { }
