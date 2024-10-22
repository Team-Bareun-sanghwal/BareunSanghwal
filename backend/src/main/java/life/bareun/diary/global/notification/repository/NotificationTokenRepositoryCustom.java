package life.bareun.diary.global.notification.repository;

import java.util.Map;
import life.bareun.diary.global.notification.dto.NotificationTokenDto;
import life.bareun.diary.member.entity.Member;

public interface NotificationTokenRepositoryCustom {
    NotificationTokenDto findNotificationTokenByMemberId(Long memberId);
    Map<Long, String> findAllNotificationToken();
}
