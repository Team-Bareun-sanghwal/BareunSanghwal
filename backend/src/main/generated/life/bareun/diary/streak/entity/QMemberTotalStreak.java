package life.bareun.diary.streak.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberTotalStreak is a Querydsl query type for MemberTotalStreak
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberTotalStreak extends EntityPathBase<MemberTotalStreak> {

    private static final long serialVersionUID = -1333013359L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberTotalStreak memberTotalStreak = new QMemberTotalStreak("memberTotalStreak");

    public final NumberPath<Integer> achieveStreakCount = createNumber("achieveStreakCount", Integer.class);

    public final NumberPath<Integer> achieveTrackerCount = createNumber("achieveTrackerCount", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> longestStreak = createNumber("longestStreak", Integer.class);

    public final life.bareun.diary.member.entity.QMember member;

    public final NumberPath<Integer> starCount = createNumber("starCount", Integer.class);

    public final NumberPath<Integer> totalStreakCount = createNumber("totalStreakCount", Integer.class);

    public final NumberPath<Integer> totalTrackerCount = createNumber("totalTrackerCount", Integer.class);

    public QMemberTotalStreak(String variable) {
        this(MemberTotalStreak.class, forVariable(variable), INITS);
    }

    public QMemberTotalStreak(Path<? extends MemberTotalStreak> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberTotalStreak(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberTotalStreak(PathMetadata metadata, PathInits inits) {
        this(MemberTotalStreak.class, metadata, inits);
    }

    public QMemberTotalStreak(Class<? extends MemberTotalStreak> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new life.bareun.diary.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

