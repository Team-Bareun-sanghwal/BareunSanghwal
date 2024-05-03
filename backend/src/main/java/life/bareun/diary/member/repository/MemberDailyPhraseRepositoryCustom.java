package life.bareun.diary.member.repository;

import life.bareun.diary.global.notification.dto.NotificationPhraseDto;

public interface MemberDailyPhraseRepositoryCustom {
    void modifyMemberDailyPhrase(NotificationPhraseDto notificationPhraseDto);
}
