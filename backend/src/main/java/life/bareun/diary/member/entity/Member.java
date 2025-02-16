package life.bareun.diary.member.entity;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import life.bareun.diary.global.auth.embed.OAuth2Provider;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.member.dto.MemberRegisterDto;
import life.bareun.diary.member.dto.request.MemberUpdateReqDto;
import life.bareun.diary.member.entity.embed.Gender;
import life.bareun.diary.member.entity.embed.Job;
import life.bareun.diary.member.entity.embed.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sub", nullable = false)
    private String sub;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "job")
    @Enumerated(EnumType.STRING)
    private Job job;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "point")
    @Min(0)
    private Integer point;

    @Column(name = "current_streak_color_id")
    @Min(0)
    private Integer currentStreakColorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_tree_id")
    private Tree tree;

    @Column(name = "current_tree_color_id")
    @Min(0)
    private Integer currentTreeColorId;

    @Column(name = "last_harvested_date")
    private LocalDate lastHarvestedDate;

    @Column(name = "paid_recovery_count")
    @Min(0)
    private Integer paidRecoveryCount;

    @Column(name = "created_datetime", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @Column(name = "updated_datetime")
    @UpdateTimestamp
    private LocalDateTime updatedDateTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    @Column(name = "member_habits")
    private List<MemberHabit> memberHabitList;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Builder
    private Member(
        String sub,
        OAuth2Provider oAuth2Provider,
        Tree defaultTree,
        Integer defaultStreakColorId,
        Integer defaultTreeColorId
    ) {
        this.sub = sub;
        this.provider = oAuth2Provider;
        this.role = Role.ROLE_USER;
        this.point = 0;
        this.currentStreakColorId = defaultStreakColorId;
        this.currentTreeColorId = defaultTreeColorId;
        this.tree = defaultTree;
        this.paidRecoveryCount = 0;
        this.isDeleted = false;
    }

    public static Member create(MemberRegisterDto memberRegisterDto) {
        return Member.builder()
            .sub(memberRegisterDto.sub())
            .oAuth2Provider(memberRegisterDto.oAuth2Provider())
            .defaultTree(memberRegisterDto.defaultTree())
            .defaultStreakColorId(memberRegisterDto.defaultStreakColorId())
            .defaultTreeColorId(memberRegisterDto.defaultTreeColorId())
            .build();
    }

    public void update(MemberUpdateReqDto memberUpdateReqDto) {
        if (memberUpdateReqDto == null) {
            return;
        }

        Optional.ofNullable(memberUpdateReqDto.nickname())
            .filter(newNickname -> !newNickname.isEmpty())
            .ifPresent(newNickname -> this.nickname = newNickname);

        Optional.ofNullable(memberUpdateReqDto.birthDate())
            .ifPresent(newBirthDate -> this.birth = newBirthDate);

        Optional.ofNullable(memberUpdateReqDto.gender())
            .ifPresent(newGender -> this.gender = newGender);

        Optional.ofNullable(memberUpdateReqDto.job())
            .ifPresent(newJob -> this.job = newJob);
    }

    public void usePoint(Integer amount) {
        this.point -= amount;
    }

    public void buyRecovery(Integer price) {
        usePoint(price);
        this.paidRecoveryCount += 1;
    }

    public void changeStreakColor(Integer streakColorId) {
        this.currentStreakColorId = streakColorId;
    }

    public void changeTreeColor(Integer treeColorId) {
        this.currentTreeColorId = treeColorId;
    }

    public void harvest(int point) {
        addPoint(point);
        this.lastHarvestedDate = LocalDate.now();
    }

    public void addPoint(Integer amount) {
        this.point += amount;
    }

    public void updateTree(Tree tree) {
        this.tree = tree;
    }

    public void delete() {
        sub = String.format("DeletedUser%d", id);
        isDeleted = true;
        deleteInfo();
    }

    private void deleteInfo() {
        this.nickname = null;
        this.birth = null;
        this.gender = null;
        this.job = null;
    }

    public boolean isPaidRecoveryAvailable() {
        return paidRecoveryCount > 0;
    }

    public void usePaidRecovery() {
        if (isPaidRecoveryAvailable()) {
            paidRecoveryCount -= 1;
        }
    }
}
