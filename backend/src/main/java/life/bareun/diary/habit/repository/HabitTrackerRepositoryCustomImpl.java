package life.bareun.diary.habit.repository;

import static life.bareun.diary.habit.entity.QHabitTracker.habitTracker;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import life.bareun.diary.habit.dto.HabitTrackerCountDto;
import life.bareun.diary.habit.dto.HabitTrackerDeleteDto;
import life.bareun.diary.habit.dto.HabitTrackerDto;
import life.bareun.diary.habit.dto.HabitTrackerLastDto;
import life.bareun.diary.habit.dto.HabitTrackerModifyDto;
import life.bareun.diary.habit.dto.HabitTrackerScheduleDto;
import life.bareun.diary.habit.dto.HabitTrackerTodayDto;
import life.bareun.diary.habit.dto.HabitTrackerTodayFactorDto;
import life.bareun.diary.habit.dto.response.HabitPracticeCountPerDayOfWeekDto;
import life.bareun.diary.habit.entity.HabitTracker;
import life.bareun.diary.member.dto.MemberHabitTrackerDto;
import life.bareun.diary.member.dto.MemberPracticeCountPerHourDto;
import life.bareun.diary.member.dto.MemberTopHabitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class HabitTrackerRepositoryCustomImpl implements HabitTrackerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteAfterHabitTracker(HabitTrackerDeleteDto habitTrackerDeleteDto) {
        queryFactory.delete(habitTracker).where(
            habitTracker.memberHabit.eq(habitTrackerDeleteDto.memberHabit())
                .and(habitTracker.createdYear.goe(habitTrackerDeleteDto.year()))
                .and(habitTracker.createdMonth.goe(habitTrackerDeleteDto.month()))
                .and(habitTracker.createdDay.goe(habitTrackerDeleteDto.day()))).execute();
    }

    @Override
    public void modifyHabitTracker(HabitTrackerModifyDto habitTrackerModifyDto) {
        queryFactory.update(habitTracker).set(habitTracker.content, habitTrackerModifyDto.content())
            .set(habitTracker.image, habitTrackerModifyDto.image())
            .where(habitTracker.id.eq(habitTrackerModifyDto.habitTrackerId())).execute();
    }

    @Override
    public List<HabitTrackerTodayDto> findAllTodayHabitTracker(
        HabitTrackerTodayFactorDto habitTrackerTodayFactorDto) {
        return queryFactory.select(
                Projections.constructor(HabitTrackerTodayDto.class,
                    habitTracker.memberHabit.habit.name, habitTracker.memberHabit.alias,
                    habitTracker.memberHabit.id, habitTracker.id, habitTracker.memberHabit.icon,
                    habitTracker.succeededTime, habitTracker.day))
            .from(habitTracker)
            .where(habitTracker.memberHabit.member.id.eq(habitTrackerTodayFactorDto.memberId())
                .and(habitTracker.createdYear.eq(habitTrackerTodayFactorDto.createdYear()))
                .and(habitTracker.createdMonth.eq(habitTrackerTodayFactorDto.createdMonth()))
                .and(habitTracker.createdDay.eq(habitTrackerTodayFactorDto.createdDay()))).fetch();
    }

    @Override
    public HabitTracker findLastHabitTracker(HabitTrackerLastDto habitTrackerLastDto) {
        return queryFactory.selectFrom(habitTracker)
            .where(habitTracker.createdDay.eq(
                queryFactory.select(habitTracker.createdDay.max())
                    .from(habitTracker)
                    .where(habitTracker.memberHabit.eq(habitTrackerLastDto.memberHabit()))
            ).and(habitTracker.memberHabit.eq(habitTrackerLastDto.memberHabit())))
            .fetchOne();
    }

    @Override
    public Long getHabitTrackerCountByMemberAndDate(HabitTrackerCountDto habitTrackerCountDto) {
        return queryFactory.select(habitTracker.id.count())
            .from(habitTracker)
            .where(
                habitTracker.member.eq(habitTrackerCountDto.member())
                    .and(habitTracker.createdYear.eq(habitTrackerCountDto.date().getYear()))
                    .and(habitTracker.createdMonth.eq(habitTrackerCountDto.date().getMonthValue()))
                    .and(habitTracker.createdDay.eq(habitTrackerCountDto.date().getDayOfMonth()))
            ).fetchFirst();
    }

    @Override
    public Long getHabitTrackerCountByMemberHabitAndDate(
        HabitTrackerScheduleDto habitTrackerScheduleDto) {
        return queryFactory.select(habitTracker.id.count())
            .from(habitTracker)
            .where(
                habitTracker.memberHabit.eq(habitTrackerScheduleDto.memberHabit())
                    .and(habitTracker.createdYear.eq(habitTrackerScheduleDto.date().getYear()))
                    .and(habitTracker.createdMonth.eq(
                        habitTrackerScheduleDto.date().getMonthValue()))
                    .and(habitTracker.createdDay.eq(habitTrackerScheduleDto.date().getDayOfMonth()))
            ).fetchFirst();
    }

    @Override
    public List<MemberTopHabitDto> findTopHabits(Long memberId) {
        Double countAll = queryFactory
            .select(habitTracker.id.count().doubleValue())
            .from(habitTracker)
            .where(habitTracker.member.id.longValue().eq(memberId))
            .fetchOne();

        return queryFactory
            .select(
                Projections.constructor(
                    MemberTopHabitDto.class,
                    habitTracker.memberHabit.alias.as("habit"),
                    habitTracker.id.count()
                        .multiply(100)
                        .divide(countAll)
                        .round()
                        .intValue()
                        .as("value")
                    // Expressions.numberTemplate(
                    //     Double.class,
                    //     "ROUND({0}*100.0, 2)",
                    //     habitTracker.id.count()
                    //         .divide(countAll)
                    // ).as("value")
                    // habitTracker.id.count().intValue().as("value")
                )
            )
            .from(habitTracker)
            .where(
                habitTracker.memberHabit.member.id.longValue().eq(memberId)
                .and(habitTracker.succeededTime.isNotNull()))
            .groupBy(habitTracker.memberHabit.id)
            .orderBy(habitTracker.id.count().desc())
            .limit(5)
            .fetch();
    }

    @Override
    public Long countByMemberId(Long memberId) {
        return queryFactory
            .select(
                habitTracker.count()
            )
            .from(habitTracker)
            .where(habitTracker.memberHabit.member.id.longValue().eq(memberId))
            .fetchOne();
    }

    @Override
    public List<HabitPracticeCountPerDayOfWeekDto> countPracticedHabitsPerDayOfWeek(Long memberId) {
        return queryFactory
            .select(
                Projections.constructor(
                    HabitPracticeCountPerDayOfWeekDto.class,
                    habitTracker.day.intValue().as("day"),
                    habitTracker.day.count().intValue().as("value")
                )
            )
            .from(habitTracker)
            .where(habitTracker.member.id.eq(memberId))
            .groupBy(habitTracker.day)
            .orderBy(habitTracker.day.asc())
            .fetch();
    }

    @Override
    public List<MemberPracticeCountPerHourDto> countPracticedHabitsPerHour(Long memberId) {
        NumberTemplate<Integer> hour = Expressions.numberTemplate(
            Integer.class,
            "HOUR({0})",
            habitTracker.succeededTime
        );

        return queryFactory
            .select(
                Projections.constructor(
                    MemberPracticeCountPerHourDto.class,
                    hour.intValue().as("time"),
                    habitTracker.count().intValue().as("value")
                )
            )
            .from(habitTracker)
            .where(habitTracker.member.id.eq(memberId))
            .groupBy(hour)
            .orderBy(hour.asc())
            .fetch();
    }

    @Override
    public List<Integer> findAllCreatedYearByMemberHabitId(Long memberId, Long memberHabitId) {
        return queryFactory
            .selectDistinct(habitTracker.createdYear.intValue())
            .from(habitTracker)
            .where(
                habitTracker.memberHabit.id.eq(memberHabitId)
                    .and(habitTracker.memberHabit.member.id.eq(memberId))
            )
            .orderBy(habitTracker.createdYear.desc())
            .fetch();
    }

    @Override
    public MemberHabitTrackerDto findAllHabitTrackerByYearAndMemberHabitId(
        Integer year,
        Long memberId,
        Long memberHabitId
    ) {
        List<HabitTrackerDto> habitTrackerList = queryFactory
            .select(
                Projections.constructor(
                    HabitTrackerDto.class,
                    habitTracker.id,
                    habitTracker.succeededTime,
                    habitTracker.content,
                    habitTracker.image,
                    habitTracker.createdMonth,
                    habitTracker.createdDay
                )
            )
            .from(habitTracker)
            .where(
                habitTracker.memberHabit.id.eq(memberHabitId)
                    .and(habitTracker.memberHabit.member.id.eq(memberId))
                    .and(habitTracker.createdYear.eq(year))
            )
            .fetch();

        return new MemberHabitTrackerDto(
            year,
            habitTrackerList
        );
    }
}
