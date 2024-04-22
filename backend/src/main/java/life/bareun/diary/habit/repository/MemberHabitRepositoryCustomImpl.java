package life.bareun.diary.habit.repository;

import static life.bareun.diary.habit.entity.QMemberHabit.memberHabit;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberHabitRepositoryCustomImpl implements MemberHabitRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void modifyStatus(Long memberHabitId) {
        queryFactory.update(memberHabit).set(memberHabit.isDeleted, true)
            .where(memberHabit.id.eq(memberHabitId)).execute();
    }
}
