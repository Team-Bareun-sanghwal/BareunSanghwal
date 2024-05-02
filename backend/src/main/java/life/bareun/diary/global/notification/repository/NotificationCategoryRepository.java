package life.bareun.diary.global.notification.repository;

import life.bareun.diary.global.notification.entity.NotificationCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationCategoryRepository extends JpaRepository<NotificationCategory, Long> {

}
