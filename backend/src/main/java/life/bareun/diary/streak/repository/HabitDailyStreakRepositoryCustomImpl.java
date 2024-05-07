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
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.streak.dto.StreakInfoByDayDto;
import life.bareun.diary.streak.entity.HabitDailyStreak;
import life.bareun.diary.streak.entity.QHabitDailyStreak;
import life.bareun.diary.streak.entity.embed.AchieveType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class HabitDailyStreakRepositoryCustomImpl implements HabitDailyStreakRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StreakInfoByDayDto> findStreakDayInfoByMemberHabitId(Long memberHabitId, LocalDate firstDayOfMonth,
        LocalDate lastDayOfMonth) {
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
                isCreatedDateInRange(firstDayOfMonth, lastDayOfMonth)
            )
            .groupBy(habitDailyStreak.createdDate)
            .fetch();
    }

    @Override
    public List<StreakInfoByDayDto> findStreakDayInfoByMemberId(Long memberId, LocalDate firstDayOfMonth,
        LocalDate lastDayOfMonth) {
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
                isCreatedDateInRange(firstDayOfMonth, lastDayOfMonth)
            )
            .groupBy(habitDailyStreak.createdDate)
            .fetch();
    }

    @Override
    public List<HabitDailyStreak> findAllHabitDailyStreakByMemberHabitIdBetweenStartDateAndEndDate(
        MemberHabit memberHabit, LocalDate startDate, LocalDate endDate) {
        return jpaQueryFactory.select(
                Projections.constructor(
                    HabitDailyStreak.class
                )
            )
            .from(habitDailyStreak)
            .where(
                habitDailyStreak.memberHabit.eq(memberHabit)
                    .and(isCreatedDateInRange(startDate, endDate)))
            .fetch();
    }

    private BooleanExpression isCreatedDateInRange(LocalDate firstDay, LocalDate lastDay) {
        return habitDailyStreak.createdDate.between(firstDay, lastDay);
    }
}
