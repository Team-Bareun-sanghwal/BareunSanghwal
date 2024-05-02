package life.bareun.diary.habit.repository;

import static life.bareun.diary.habit.entity.QHabitTracker.habitTracker;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import life.bareun.diary.habit.dto.HabitTrackerCountDto;
import life.bareun.diary.habit.dto.HabitTrackerDeleteDto;
import life.bareun.diary.habit.dto.HabitTrackerLastDto;
import life.bareun.diary.habit.dto.HabitTrackerModifyDto;
import life.bareun.diary.habit.dto.HabitTrackerTodayDto;
import life.bareun.diary.habit.dto.HabitTrackerTodayFactorDto;
import life.bareun.diary.habit.entity.HabitTracker;
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
    public List<HabitTrackerTodayDto> findAllTodayHabitTracker(HabitTrackerTodayFactorDto habitTrackerTodayFactorDto) {
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
                    .and(habitTracker.createdYear.eq(habitTrackerCountDto.date().getDayOfYear()))
                    .and(habitTracker.createdMonth.eq(habitTrackerCountDto.date().getMonthValue()))
                    .and(habitTracker.createdDay.eq(habitTrackerCountDto.date().getDayOfMonth()))
            ).fetchFirst();
    }
}
