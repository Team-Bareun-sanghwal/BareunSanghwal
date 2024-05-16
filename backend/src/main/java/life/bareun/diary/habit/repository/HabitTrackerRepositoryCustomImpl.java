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
            .set(habitTracker.succeededTime, habitTrackerModifyDto.succeededTime())
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
    public List<MemberTopHabitDto> findAllTopHabit(Long memberId) {
        return queryFactory
            .select(
                Projections.constructor(
                    MemberTopHabitDto.class,
                    habitTracker.memberHabit.alias.as("habit"),
                    habitTracker.memberHabit.count().intValue().as("value")
                )
            )
            .from(habitTracker)
            .where(
                habitTracker.memberHabit.member.id.longValue().eq(memberId)
                    .and(habitTracker.succeededTime.isNotNull())
            )
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
            .where(
                habitTracker.memberHabit.member.id.longValue().eq(memberId)
                    .and(habitTracker.succeededTime.isNotNull())
            )
            .fetchOne();
    }


    @Override
    public List<HabitPracticeCountPerDayOfWeekDto> countPracticedHabitsPerDayOfWeek(Long memberId) {
        return queryFactory
            .select(
                Projections.constructor(
                    HabitPracticeCountPerDayOfWeekDto.class,
                    habitTracker.day.intValue().as("day"),
                    habitTracker.count().intValue().as("value")
                )
            )
            .from(habitTracker)
            .where(
                habitTracker.member.id.eq(memberId)
                    .and(habitTracker.succeededTime.isNotNull())
            )
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
            .where(
                habitTracker.member.id.eq(memberId)
                    .and(habitTracker.succeededTime.isNotNull())
            )
            .groupBy(hour)
            .orderBy(hour.asc())
            .fetch();
    }

    @Override
    public List<Integer> findAllSucceededYearByMemberHabitId(Long memberId, Long memberHabitId) {
        NumberTemplate<Integer> succeededYear = Expressions.numberTemplate(
            Integer.class,
            "YEAR({0})",
            habitTracker.succeededTime
        );

        return queryFactory
            .selectDistinct(succeededYear)
            .from(habitTracker)
            .where(
                habitTracker.succeededTime.isNotNull() // 실제로 수행했고
                    .and(
                        // 주어진 사용자 및 사용자 해빗 정보와 일치해야 한다.
                        habitTracker.memberHabit.id.eq(memberHabitId)
                            .and(habitTracker.memberHabit.member.id.eq(memberId))
                    )
            )
            .orderBy(succeededYear.desc())
            .fetch();
    }

    @Override
    public MemberHabitTrackerDto findAllHabitTrackerBySuceededYearAndMemberHabitOrderByCreatedDate(
        Integer year,
        Long memberId,
        Long memberHabitId
    ) {
        NumberTemplate<Integer> succeededYear = Expressions.numberTemplate(
            Integer.class,
            "YEAR({0})",
            habitTracker.succeededTime
        );

        NumberTemplate<Integer> succeededMonth = Expressions.numberTemplate(
            Integer.class,
            "MONTH({0})",
            habitTracker.succeededTime
        );

        NumberTemplate<Integer> succeededDay = Expressions.numberTemplate(
            Integer.class,
            "DAY({0})",
            habitTracker.succeededTime
        );

        List<HabitTrackerDto> habitTrackerList = queryFactory
            .select(
                Projections.constructor(
                    HabitTrackerDto.class,
                    habitTracker.id,
                    habitTracker.succeededTime,
                    habitTracker.content,
                    habitTracker.image,
                    succeededMonth.intValue(),
                    succeededDay.intValue()
                )
            )
            .from(habitTracker)
            .where(
                habitTracker.memberHabit.id.eq(memberHabitId)
                    .and(habitTracker.succeededTime.isNotNull())
                    .and(habitTracker.memberHabit.member.id.eq(memberId))
                    .and(succeededYear.eq(year))
                // .and(
                //     habitTracker.createdMonth.lt(month) // 월이 작거나
                //         .or(
                //             habitTracker.createdMonth.eq(month) //월이 같고 일이 작거나 같음
                //                 .and(habitTracker.createdDay.loe(day))
                //         )
                //     )
            )
            .orderBy(
                succeededMonth.desc(),
                succeededDay.desc()
            )
            .fetch();

        return new MemberHabitTrackerDto(
            year,
            habitTrackerList
        );
    }
}
