package life.bareun.diary.member.repository;

import static life.bareun.diary.habit.entity.QHabitTracker.habitTracker;
import static life.bareun.diary.member.entity.QMember.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import life.bareun.diary.global.notification.dto.NotificationDateDto;
import life.bareun.diary.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Member> findAllUnaccompaniedMember(NotificationDateDto notificationDateDto) {
        return queryFactory.select(member).from(member).join(habitTracker)
            .on(member.eq(habitTracker.member)).where(habitTracker.succeededTime.isNull()
                .and(habitTracker.createdYear.eq(notificationDateDto.year()))
                .and(habitTracker.createdMonth.eq(notificationDateDto.month()))
                .and(habitTracker.createdDay.eq(notificationDateDto.day())))
            .groupBy(member).fetch();
    }
}
