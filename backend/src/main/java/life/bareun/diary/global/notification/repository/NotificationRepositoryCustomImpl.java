package life.bareun.diary.global.notification.repository;

import static life.bareun.diary.global.notification.entity.QNotification.notification;

import com.querydsl.jpa.impl.JPAQueryFactory;
import life.bareun.diary.global.notification.dto.NotificationStatusModifyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class NotificationRepositoryCustomImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public void modifyIsRead(NotificationStatusModifyDto notificationStatusModifyDto) {
        queryFactory.update(notification)
            .set(notification.isRead, notificationStatusModifyDto.status())
            .where(notification.id.eq(notificationStatusModifyDto.notificationId())).execute();
    }
}
