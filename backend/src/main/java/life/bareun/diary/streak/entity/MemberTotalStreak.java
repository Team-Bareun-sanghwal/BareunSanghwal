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
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    @NotNull
    private int longestStreak;

    @Column
    @NotNull
    private int achieveStreakCount;

    @Column
    @NotNull
    private int totalStreakCount;

    @Column
    @NotNull
    private int achieveTrackerCount;

    @Column
    @NotNull
    private int totalTrackerCount;

    @Column
    @NotNull
    private int starCount;

    @Builder
    public MemberTotalStreak(Member member) {
        this.member = member;
        // this.longestStreak = 0;
        // this.achieveStreakCount = 0;
        // this.totalStreakCount = 0;
        // this.achieveTrackerCount = 0;
        // this.totalTrackerCount = 0;
        // this.starCount = 0;
    }

    public void modifyLongestStreak(int streakCount) {
        this.longestStreak = streakCount;
    }

    public void modifyAchieveStreakCount() {
        this.achieveTrackerCount++;
    }

    public void modifyTotalStreakCount() {
        this.totalTrackerCount++;
    }

    public void modifyAchieveTrackerCount() {
        this.achieveStreakCount++;
    }

    public void modifyTotalTrackerCount(int trackerCount) {
        this.totalStreakCount += trackerCount;
    }

    public void modifyStarCount() {
        this.starCount++;
    }
}
