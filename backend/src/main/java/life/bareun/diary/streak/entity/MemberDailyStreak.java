package life.bareun.diary.streak.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.streak.entity.embed.AchieveType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class MemberDailyStreak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @Column(name = "member_id")
    private Member member;

    @Column(name = "created_date")
    @NotNull
    private LocalDate createdDate;

    @Column(name = "achieve_tracker_count")
    @NotNull
    private int achieveTrackerCount;

    @Column(name = "total_tracker_count")
    @NotNull
    private int totalTrackerCount;

    @Column(name = "is_stared")
    @NotNull
    private boolean isStared;

    @Column(name = "achieve_type")
    @Enumerated(EnumType.STRING)
    private AchieveType achieveType;

    @Column(name = "current_streak")
    @NotNull
    private int currentStreak;

    @Builder
    public MemberDailyStreak(Member member, LocalDate createdDate, int totalTrackerCount, AchieveType achieveType,
        int currentStreak) {
        this.member = member;
        this.createdDate = createdDate;
        this.achieveTrackerCount = 0;
        this.totalTrackerCount = totalTrackerCount;
        this.isStared = false;
        this.achieveType = achieveType;
        this.currentStreak = currentStreak;
    }

    public void increaseAchieveTrackerCountByOne() {
        this.achieveTrackerCount++;
    }

    public void changeIsStaredByTrue() {
        this.isStared = true;
    }

    public void changeAchieveTypeByAchieve() {
        this.achieveType = AchieveType.ACHIEVE;
    }

    public void increaseCurrentStreakByOne() {
        this.currentStreak++;
    }
}
