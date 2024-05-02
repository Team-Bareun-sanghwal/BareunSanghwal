package life.bareun.diary.global.notification.repository;

import java.util.List;
import life.bareun.diary.global.notification.entity.Notification;
import life.bareun.diary.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long>,
    NotificationRepositoryCustom {

    List<Notification> findAllByMember_OrderByCreatedDatetimeDescIdDesc(Member member);
}
