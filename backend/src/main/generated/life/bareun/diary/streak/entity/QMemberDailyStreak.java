package life.bareun.diary.streak.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberDailyStreak is a Querydsl query type for MemberDailyStreak
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberDailyStreak extends EntityPathBase<MemberDailyStreak> {

    private static final long serialVersionUID = -844520538L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberDailyStreak memberDailyStreak = new QMemberDailyStreak("memberDailyStreak");

    public final NumberPath<Integer> achieveTrackerCount = createNumber("achieveTrackerCount", Integer.class);

    public final EnumPath<life.bareun.diary.streak.entity.embed.AchieveType> achieveType = createEnum("achieveType", life.bareun.diary.streak.entity.embed.AchieveType.class);

    public final DatePath<java.time.LocalDate> createdDate = createDate("createdDate", java.time.LocalDate.class);

    public final NumberPath<Integer> currentStreak = createNumber("currentStreak", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isStared = createBoolean("isStared");

    public final life.bareun.diary.member.entity.QMember member;

    public final NumberPath<Integer> totalTrackerCount = createNumber("totalTrackerCount", Integer.class);

    public QMemberDailyStreak(String variable) {
        this(MemberDailyStreak.class, forVariable(variable), INITS);
    }

    public QMemberDailyStreak(Path<? extends MemberDailyStreak> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberDailyStreak(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberDailyStreak(PathMetadata metadata, PathInits inits) {
        this(MemberDailyStreak.class, metadata, inits);
    }

    public QMemberDailyStreak(Class<? extends MemberDailyStreak> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new life.bareun.diary.member.entity.QMember(forProperty("member"), inits.get("member")) : null;
    }

}

