package life.bareun.diary.streak.repository;

import static life.bareun.diary.habit.entity.QMemberHabit.memberHabit;
import static life.bareun.diary.member.entity.QMember.member;
import static life.bareun.diary.streak.entity.QHabitDailyStreak.habitDailyStreak;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import life.bareun.diary.streak.dto.StreakInfoByDayDto;
import life.bareun.diary.streak.entity.QHabitDailyStreak;
import life.bareun.diary.streak.entity.embed.AchieveType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class HabitDailyStreakRepositoryCustomImpl implements HabitDailyStreakRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StreakInfoByDayDto> findStreakDayInfoByMemberHabitId(LocalDate firstDayOfMonth,
        LocalDate lastDayOfMonth, Long memberHabitId) {
        QHabitDailyStreak subHabitDailyStreak = new QHabitDailyStreak("sub");

        return jpaQueryFactory.select(
                Projections.constructor(
                    StreakInfoByDayDto.class,
                    habitDailyStreak.createdDate.dayOfMonth(),
                    ExpressionUtils.as(
                        JPAExpressions.select(subHabitDailyStreak.id.count())
                            .from(subHabitDailyStreak)
                            .where(subHabitDailyStreak.createdDate.eq(habitDailyStreak.createdDate)
                                .and(subHabitDailyStreak.achieveType.eq(AchieveType.ACHIEVE))
                                .and(subHabitDailyStreak.memberHabit.id.eq(memberHabitId))
                            ),
                        "achieveCount"
                    ),
                    ExpressionUtils.as(
                        JPAExpressions.select(subHabitDailyStreak.id.count())
                            .from(subHabitDailyStreak)
                            .where(subHabitDailyStreak.createdDate.eq(habitDailyStreak.createdDate)
                                .and(
                                    subHabitDailyStreak.achieveType.eq(AchieveType.ACHIEVE)
                                        .or(subHabitDailyStreak.achieveType.eq(AchieveType.NOT_ACHIEVE))
                                ).and(subHabitDailyStreak.memberHabit.id.eq(memberHabitId))
                            ),
                        "totalCount"
                    )
                )
            ).from(habitDailyStreak)
            .join(habitDailyStreak.memberHabit, memberHabit)
            .join(memberHabit.member, member)
            .where(
                habitDailyStreak.memberHabit.id.eq(memberHabitId),
                isCreatedDateInMonth(firstDayOfMonth, lastDayOfMonth)
            )
            .groupBy(habitDailyStreak.createdDate)
            .fetch();
    }

    @Override
    public List<StreakInfoByDayDto> findStreakDayInfoByMemberId(LocalDate firstDayOfMonth, LocalDate lastDayOfMonth,
        Long memberId) {
        QHabitDailyStreak subHabitDailyStreak = new QHabitDailyStreak("sub");

        return jpaQueryFactory.select(
                Projections.constructor(
                    StreakInfoByDayDto.class,
                    habitDailyStreak.createdDate.dayOfMonth(),
                    ExpressionUtils.as(
                        JPAExpressions.select(subHabitDailyStreak.id.count())
                            .from(subHabitDailyStreak)
                            .where(subHabitDailyStreak.createdDate.eq(habitDailyStreak.createdDate)
                                .and(subHabitDailyStreak.achieveType.eq(AchieveType.ACHIEVE))),
                        "achieveCount"
                    ),
                    ExpressionUtils.as(
                        JPAExpressions.select(subHabitDailyStreak.id.count())
                            .from(subHabitDailyStreak)
                            .where(subHabitDailyStreak.createdDate.eq(habitDailyStreak.createdDate)
                                .and(
                                    subHabitDailyStreak.achieveType.eq(AchieveType.ACHIEVE)
                                        .or(subHabitDailyStreak.achieveType.eq(AchieveType.NOT_ACHIEVE))
                                )
                            ),
                        "totalCount"
                    )
                )
            ).from(habitDailyStreak)
            .join(habitDailyStreak.memberHabit, memberHabit)
            .join(memberHabit.member, member)
            .where(
                member.id.eq(memberId),
                isCreatedDateInMonth(firstDayOfMonth, lastDayOfMonth)
            )
            .groupBy(habitDailyStreak.createdDate)
            .fetch();
    }

    private BooleanExpression isCreatedDateInMonth(LocalDate firstDay, LocalDate lastDay) {
        return habitDailyStreak.createdDate.between(firstDay, lastDay);
    }
}
