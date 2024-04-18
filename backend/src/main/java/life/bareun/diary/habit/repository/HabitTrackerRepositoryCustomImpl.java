package life.bareun.diary.habit.repository;

import static life.bareun.diary.habit.entity.QHabitTracker.habitTracker;

import com.querydsl.jpa.impl.JPAQueryFactory;
import life.bareun.diary.habit.dto.HabitTrackerDeleteDto;
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
}
