package life.bareun.diary.habit.repository;

import static life.bareun.diary.habit.entity.QMemberHabit.memberHabit;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import life.bareun.diary.member.dto.MemberHabitsDto;
import life.bareun.diary.habit.dto.MemberHabitModifyDto;
import life.bareun.diary.member.dto.MemberPracticeCountPerHourDto;
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

    @Override
    public List<MemberHabitsDto> findAllByMemberIdOrderByCreatedDatetime(Long id) {
        return queryFactory.select(
                Projections.constructor(
                    MemberHabitsDto.class,
                    memberHabit.habit.name.as("name"),
                    memberHabit.alias.as("alias"),
                    memberHabit.id.as("memberHabitId"),
                    memberHabit.icon.as("icon"),
                    memberHabit.createdDatetime.as("createdAt")
                )
            )
            .from(memberHabit)
            .where(memberHabit.member.id.eq(id))
            .orderBy(
                memberHabit.createdDatetime.desc()
            )
            .fetch();

    }
}
