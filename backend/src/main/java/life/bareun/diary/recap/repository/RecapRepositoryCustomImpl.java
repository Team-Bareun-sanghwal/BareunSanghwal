package life.bareun.diary.recap.repository;

import static life.bareun.diary.habit.entity.QHabitTracker.habitTracker;
import static life.bareun.diary.recap.entity.QRecap.recap;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import life.bareun.diary.recap.dto.RecapMemberDto;
import life.bareun.diary.recap.dto.RecapMemberHabitDto;
import life.bareun.diary.recap.dto.RecapMemberMonthDto;
import life.bareun.diary.recap.dto.RecapModifyDto;
import life.bareun.diary.recap.dto.RecapMonthDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RecapRepositoryCustomImpl implements RecapRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<RecapMemberDto> findAllAppropriateMember(RecapMonthDto recapMonthDto) {
        return queryFactory.selectDistinct(
                Projections.constructor(RecapMemberDto.class, habitTracker.memberHabit.member))
            .from(habitTracker).join(habitTracker.memberHabit)
            .where(habitTracker.succeededTime.isNotNull()
                .and(habitTracker.createdYear.eq(recapMonthDto.year()))
                .and(habitTracker.createdMonth.eq(recapMonthDto.month()))
                .and(habitTracker.memberHabit.isDeleted.eq(false)))
            .groupBy(habitTracker.memberHabit)
            .having(habitTracker.countDistinct().gt(1L))
            .fetch();
    }

    @Override
    public List<RecapMemberHabitDto> findAllAppropriateMemberHabit(
        RecapMemberMonthDto recapMemberMonthDto) {
        return queryFactory.select(Projections.constructor(RecapMemberHabitDto.class,
                habitTracker.memberHabit, habitTracker.countDistinct()))
            .from(habitTracker).join(habitTracker.memberHabit)
            .where(habitTracker.succeededTime.isNotNull()
                .and(habitTracker.memberHabit.isDeleted.eq(false))
                .and(habitTracker.createdYear.eq(recapMemberMonthDto.year()))
                .and(habitTracker.createdMonth.eq(recapMemberMonthDto.month()))
                .and(habitTracker.memberHabit.member.eq(recapMemberMonthDto.member())))
            .groupBy(habitTracker.memberHabit)
            .having(habitTracker.countDistinct().gt(1L))
            .orderBy(habitTracker.countDistinct().desc())
            .fetch();
    }

    @Override
    public void modifyRecap(RecapModifyDto recapModifyDto) {
        queryFactory.update(recap).set(recap.wholeStreak, recapModifyDto.wholeStreak())
            .set(recap.maxHabitImage, recapModifyDto.maxHabitImage())
            .set(recap.mostFrequencyWord, recapModifyDto.mostFrequencyWord())
            .set(recap.mostFrequencyTime, recapModifyDto.mostFrequencyTime())
            .where(recap.eq(recapModifyDto.recap())).execute();
    }
}
