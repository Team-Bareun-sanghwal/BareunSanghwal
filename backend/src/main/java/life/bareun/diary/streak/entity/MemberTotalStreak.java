package life.bareun.diary.streak.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import life.bareun.diary.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class MemberTotalStreak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "longest_streak")
    @NotNull
    private int longestStreak;

    @Column(name = "achieve_streak_count")
    @NotNull
    private int achieveStreakCount;

    @Column(name = "total_streak_count")
    @NotNull
    private int totalStreakCount;

    @Column(name = "achieve_tracker_count")
    @NotNull
    private int achieveTrackerCount;

    @Column(name = "total_tracker_count")
    @NotNull
    private int totalTrackerCount;

    @Column(name = "star_count")
    @NotNull
    private int starCount;

    @Builder
    public MemberTotalStreak(Member member) {
        this.member = member;
    }

    public void modifyLongestStreak(int streakCount) {
        this.longestStreak = streakCount;
    }

    public void increaseAchieveStreakCountByOne() {
        this.achieveTrackerCount++;
    }

    public void increaseTotalStreakCountByOne() {
        this.totalTrackerCount++;
    }

    public void increaseAchieveTrackerCountByOne() {
        this.achieveStreakCount++;
    }

    public void increaseTotalTrackerCount(int trackerCount) {
        this.totalStreakCount += trackerCount;
    }

    public void addStarCount() {
        this.starCount++;
    }
}