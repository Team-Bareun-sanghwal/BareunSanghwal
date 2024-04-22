package life.bareun.diary.habit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import life.bareun.diary.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "habit_tracker")
public class HabitTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_habit_id")
    private MemberHabit memberHabit;

    @Column(name = "succeeded_time")
    private LocalDateTime succeededTime;

    @Column(name = "content", length = 127)
    @Size(min = 1, max = 127)
    private String content;

    @Column(name = "image", length = 255)
    @Size(min = 1, max = 255)
    private String image;

    @Column(name = "day")
    @Max(7)
    private int day;

    @Column(name = "created_year")
    @Max(9999)
    private int createdYear;

    @Column(name = "created_month")
    @Max(12)
    private int createdMonth;

    @Column(name = "created_day")
    @Max(31)
    private int createdDay;

    @Builder
    public HabitTracker(Member member, MemberHabit memberHabit, String content, String image,
        int day, int createdYear, int createdMonth, int createdDay) {
        this.member = member;
        this.memberHabit = memberHabit;
        this.content = content;
        this.image = image;
        this.day = day;
        this.createdYear = createdYear;
        this.createdMonth = createdMonth;
        this.createdDay = createdDay;
    }

}
