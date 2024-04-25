package life.bareun.diary.habit.repository;

import static life.bareun.diary.habit.entity.QMemberHabit.memberHabit;

import com.querydsl.jpa.impl.JPAQueryFactory;
import life.bareun.diary.habit.dto.MemberHabitModifyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberHabitRepositoryCustomImpl implements MemberHabitRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void modifyStatus(MemberHabitModifyDto memberHabitModifyDto) {
        queryFactory.update(memberHabit).set(memberHabit.isDeleted, true)
            .set(memberHabit.succeededDatetime, memberHabitModifyDto.succeededTime())
            .where(memberHabit.id.eq(memberHabitModifyDto.memberHabitId())).execute();
    }
}
