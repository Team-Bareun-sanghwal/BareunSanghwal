package life.bareun.diary.streak.repository;

import static life.bareun.diary.streak.entity.QMemberDailyStreak.memberDailyStreak;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import life.bareun.diary.streak.dto.MonthStreakInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberDailyStreakRepositoryCustomImpl implements MemberDailyStreakRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MonthStreakInfoDto> findStreakDayInfoByMemberId(Long memberId, LocalDate firstDayOfMonth,
        LocalDate lastDayOfMonth) {

        return jpaQueryFactory.select(
                Projections.constructor(
                    MonthStreakInfoDto.class,
                    memberDailyStreak.createdDate.dayOfMonth(),
                    memberDailyStreak.achieveType,
                    memberDailyStreak.achieveTrackerCount,
                    memberDailyStreak.totalTrackerCount
                ))
            .from(memberDailyStreak)
            .where(memberDailyStreak.member.id.eq(memberId),
                isCreatedDateInRange(firstDayOfMonth, lastDayOfMonth))
            .orderBy(memberDailyStreak.createdDate.asc())
            .fetch();
    }

    private BooleanExpression isCreatedDateInRange(LocalDate firstDay, LocalDate lastDay) {
        return memberDailyStreak.createdDate.between(firstDay, lastDay);
    }
}
