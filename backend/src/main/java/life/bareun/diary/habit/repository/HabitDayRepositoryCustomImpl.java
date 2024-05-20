package life.bareun.diary.habit.repository;

import static life.bareun.diary.habit.entity.QHabitDay.habitDay;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import life.bareun.diary.habit.entity.MemberHabit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class HabitDayRepositoryCustomImpl implements HabitDayRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Integer> findAllDayByMemberHabit(MemberHabit memberHabit) {
        return queryFactory.select(habitDay.day).from(habitDay)
            .where(habitDay.memberHabit.eq(memberHabit)).fetch();
    }
}
