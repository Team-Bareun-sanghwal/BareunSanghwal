package life.bareun.diary.recap.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import life.bareun.diary.habit.entity.MemberHabit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "recap_habit_accomplished")
public class RecapHabitAccomplished {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recap_id")
    private Recap recap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_habit_id")
    private MemberHabit memberHabit;

    @Column(name = "created_year")
    private int createdYear;

    @Column(name = "created_month")
    private int createdMonth;

    @Column(name = "is_best")
    private Boolean isBest;

    @Column(name = "action_count")
    private int actionCount;

    @Column(name = "miss_count")
    private int missCount;

    @Column(name = "achievement_rate")
    private int achievementRate;

    @Builder
    public RecapHabitAccomplished(Recap recap, MemberHabit memberHabit, int createdYear,
        int createdMonth,
        Boolean isBest, int actionCount, int missCount, int achievementRate) {
        this.recap = recap;
        this.memberHabit = memberHabit;
        this.createdYear = createdYear;
        this.createdMonth = createdMonth;
        this.isBest = isBest;
        this.actionCount = actionCount;
        this.missCount = missCount;
        this.achievementRate = achievementRate;
    }
}
