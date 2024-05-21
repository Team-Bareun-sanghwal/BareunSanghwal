package life.bareun.diary.global.notification.repository;

import java.util.Map;
import life.bareun.diary.global.notification.dto.NotificationTokenDto;

public interface NotificationTokenRepositoryCustom {
    NotificationTokenDto findNotificationTokenByMemberId(String memberId);

    Map<Long, String> findAllNotificationToken();
}
