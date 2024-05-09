package life.bareun.diary.streak.repository;

import static life.bareun.diary.habit.entity.QMemberHabit.memberHabit;
import static life.bareun.diary.streak.entity.QHabitDailyStreak.habitDailyStreak;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import life.bareun.diary.streak.dto.MonthStreakInfoDto;
import life.bareun.diary.streak.entity.QHabitDailyStreak;
import life.bareun.diary.streak.entity.embed.AchieveType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class HabitDailyStreakRepositoryCustomImpl implements HabitDailyStreakRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MonthStreakInfoDto> findStreakDayInfoByMemberHabitId(Long memberHabitId, LocalDate firstDayOfMonth,
        LocalDate lastDayOfMonth) {

        QHabitDailyStreak subHabitDailyStreak = new QHabitDailyStreak("sub");

        return jpaQueryFactory.select(
                Projections.constructor(
                    MonthStreakInfoDto.class,
                    habitDailyStreak.createdDate.dayOfMonth(),
                    habitDailyStreak.achieveType,
                    new CaseBuilder()
                        .when(habitDailyStreak.achieveType.eq(AchieveType.ACHIEVE))
                        .then(1)
                        .otherwise(0).as("achieveCount"),
                    new CaseBuilder()
                        .when(habitDailyStreak.achieveType.eq(AchieveType.NOT_EXISTED))
                        .then(0)
                        .otherwise(1).as("totalCount")
                )
            ).from(habitDailyStreak)
            .join(habitDailyStreak.memberHabit, memberHabit)
            .where(
                habitDailyStreak.memberHabit.id.eq(memberHabitId),
                isCreatedDateInRange(firstDayOfMonth, lastDayOfMonth)
            )
            .orderBy(habitDailyStreak.createdDate.asc())
            .fetch();
    }

    private BooleanExpression isCreatedDateInRange(LocalDate firstDay, LocalDate lastDay) {
        return habitDailyStreak.createdDate.between(firstDay, lastDay);
    }
}
