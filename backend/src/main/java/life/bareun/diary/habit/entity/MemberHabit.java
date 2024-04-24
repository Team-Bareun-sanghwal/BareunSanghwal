package life.bareun.diary.habit.entity;

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
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import life.bareun.diary.habit.entity.embed.MaintainWay;
import life.bareun.diary.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_habit")
public class MemberHabit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id")
    private Habit habit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "alias", length = 63)
    @Size(min = 1, max = 63)
    private String alias;

    @CreationTimestamp
    @Column(name = "created_datetime", updatable = false)
    private LocalDateTime createdDatetime;

    @Column(name = "icon", length = 63)
    @Size(min = 1, max = 63)
    private String icon;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "maintain_way")
    @Enumerated(EnumType.STRING)
    private MaintainWay maintainWay;

    @Column(name = "maintain_amount")
    private int maintainAmount;

    @UpdateTimestamp
    @Column(name = "succeeded_datetime")
    private LocalDateTime succeededDatetime;

    @Builder
    public MemberHabit(Habit habit, Member member, String alias, String icon, Boolean isDeleted,
        MaintainWay maintainWay, int maintainAmount) {
        this.habit = habit;
        this.member = member;
        this.alias = alias;
        this.icon = icon;
        this.isDeleted = isDeleted;
        this.maintainWay = maintainWay;
        this.maintainAmount = maintainAmount;
    }

}
