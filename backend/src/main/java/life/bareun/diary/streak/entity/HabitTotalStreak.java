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
import life.bareun.diary.habit.entity.MemberHabit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class HabitTotalStreak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_habit_id")
    private MemberHabit memberHabit;

    @Column(name = "longest_streak")
    @NotNull
    private int longestStreak;

    @Column(name = "achieve_tracker_count")
    @NotNull
    private int achieveTrackerCount;

    @Column(name = "total_tracker_count")
    @NotNull
    private int totalTrackerCount;

    @Builder
    public HabitTotalStreak(MemberHabit memberHabit) {
        this.memberHabit = memberHabit;
    }

    public void modifyLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    public void modifyAchieveTrackerCount(int achieveTrackerCount) {
        this.achieveTrackerCount = achieveTrackerCount;
    }

    public void modifyTotalTrackerCount(int totalTrackerCount) {
        this.totalTrackerCount = totalTrackerCount;
    }
}
