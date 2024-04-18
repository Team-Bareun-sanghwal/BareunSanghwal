package life.bareun.diary.streak.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import life.bareun.diary.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class MemberDailyStreak {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    @NotNull
    private Date createdDate;

    @Column
    @NotNull
    private int achieveTrackerCount;

    @Column
    @NotNull
    private int totalTrackerCount;

    @Column
    @NotNull
    private boolean isStared;

    /**
     * TODO
     * boolean to enum
     */
    @Column
    private boolean isAchieved;

    @Column
    @NotNull
    private int currentStreak;

    public MemberDailyStreak(Member member, Date createdDate, int achieveTrackerCount,
        int totalTrackerCount, boolean isAchieved, int currentStreak) {
        this.member = member;
        this.createdDate = createdDate;
        this.achieveTrackerCount = 0;
        this.totalTrackerCount = totalTrackerCount;
        this.isStared = false;
        this.isAchieved = isAchieved;
        this.currentStreak = currentStreak;
    }
}
