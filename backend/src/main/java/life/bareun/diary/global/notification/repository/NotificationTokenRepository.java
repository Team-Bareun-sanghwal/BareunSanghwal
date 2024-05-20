package life.bareun.diary.global.notification.repository;

import life.bareun.diary.global.notification.entity.NotificationToken;
import org.springframework.data.repository.CrudRepository;

public interface NotificationTokenRepository extends CrudRepository<NotificationToken, String>,
    NotificationTokenRepositoryCustom {

}
