package life.bareun.diary.streak.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.streak.entity.embed.AchieveType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class HabitDailyStreak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_habit_id")
    private MemberHabit memberHabit;

    @Column(name = "achieve_type")
    @NotNull
    @Enumerated(EnumType.STRING)
    private AchieveType achieveType;

    @Column(name = "current_streak")
    @NotNull
    private int currentStreak;

    @Column(name = "create_date")
    @NotNull
    private LocalDate createdDate;

    @Builder
    public HabitDailyStreak(MemberHabit memberHabit, AchieveType achieveType, int currentStreak,
        LocalDate createdDate) {
        this.memberHabit = memberHabit;
        this.achieveType = achieveType;
        this.currentStreak = currentStreak;
        this.createdDate = createdDate;
    }

    public void modifyCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public void modifyAchieveType(AchieveType achieveType) {
        this.achieveType = achieveType;
    }
}
