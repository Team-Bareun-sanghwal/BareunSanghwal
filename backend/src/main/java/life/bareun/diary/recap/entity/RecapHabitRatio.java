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
import life.bareun.diary.habit.entity.Habit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "recap_habit_ratio")
public class RecapHabitRatio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recap_id")
    private Recap recap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id")
    private Habit habit;

    @Column(name = "created_year")
    private int createdYear;

    @Column(name = "created_month")
    private int createdMonth;

    @Column(name = "ratio")
    private double ratio;

    @Builder
    public RecapHabitRatio(Recap recap, Habit habit, int createdYear, int createdMonth,
        double ratio) {
        this.recap = recap;
        this.habit = habit;
        this.createdYear = createdYear;
        this.createdMonth = createdMonth;
        this.ratio = ratio;
    }
}
