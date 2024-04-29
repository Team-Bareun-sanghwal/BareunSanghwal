package life.bareun.diary.streak.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHabitTotalStreak is a Querydsl query type for HabitTotalStreak
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHabitTotalStreak extends EntityPathBase<HabitTotalStreak> {

    private static final long serialVersionUID = 688023813L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHabitTotalStreak habitTotalStreak = new QHabitTotalStreak("habitTotalStreak");

    public final NumberPath<Integer> achieveTrackerCount = createNumber("achieveTrackerCount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> longestStreak = createNumber("longestStreak", Integer.class);

    public final life.bareun.diary.habit.entity.QMemberHabit memberHabit;

    public final NumberPath<Integer> totalTrackerCount = createNumber("totalTrackerCount", Integer.class);

    public QHabitTotalStreak(String variable) {
        this(HabitTotalStreak.class, forVariable(variable), INITS);
    }

    public QHabitTotalStreak(Path<? extends HabitTotalStreak> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHabitTotalStreak(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHabitTotalStreak(PathMetadata metadata, PathInits inits) {
        this(HabitTotalStreak.class, metadata, inits);
    }

    public QHabitTotalStreak(Class<? extends HabitTotalStreak> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberHabit = inits.isInitialized("memberHabit") ? new life.bareun.diary.habit.entity.QMemberHabit(forProperty("memberHabit"), inits.get("memberHabit")) : null;
    }

}

