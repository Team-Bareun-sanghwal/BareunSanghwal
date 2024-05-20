package life.bareun.diary.streak.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHabitDailyStreak is a Querydsl query type for HabitDailyStreak
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHabitDailyStreak extends EntityPathBase<HabitDailyStreak> {

    private static final long serialVersionUID = 1176516634L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHabitDailyStreak habitDailyStreak = new QHabitDailyStreak("habitDailyStreak");

    public final EnumPath<life.bareun.diary.streak.entity.embed.AchieveType> achieveType = createEnum("achieveType", life.bareun.diary.streak.entity.embed.AchieveType.class);

    public final DatePath<java.time.LocalDate> createdDate = createDate("createdDate", java.time.LocalDate.class);

    public final NumberPath<Integer> currentStreak = createNumber("currentStreak", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final life.bareun.diary.habit.entity.QMemberHabit memberHabit;

    public QHabitDailyStreak(String variable) {
        this(HabitDailyStreak.class, forVariable(variable), INITS);
    }

    public QHabitDailyStreak(Path<? extends HabitDailyStreak> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHabitDailyStreak(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHabitDailyStreak(PathMetadata metadata, PathInits inits) {
        this(HabitDailyStreak.class, metadata, inits);
    }

    public QHabitDailyStreak(Class<? extends HabitDailyStreak> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberHabit = inits.isInitialized("memberHabit") ? new life.bareun.diary.habit.entity.QMemberHabit(forProperty("memberHabit"), inits.get("memberHabit")) : null;
    }

}

