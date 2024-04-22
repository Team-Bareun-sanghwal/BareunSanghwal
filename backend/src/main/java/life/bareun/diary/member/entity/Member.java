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
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;
import life.bareun.diary.global.security.embed.OAuth2Provider;
import life.bareun.diary.member.entity.embed.Gender;
import life.bareun.diary.member.entity.embed.Job;
import life.bareun.diary.member.entity.embed.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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

    @Column(name = "current_theme_id")
    @Min(0)
    private Integer currentThemeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_tree_id")
    private Tree tree;

    @Column(name = "daily_point")
    @Min(0)
    private Integer dailyPoint;

    @Column(name = "current_tree_point")
    @Min(0)
    private Integer currentTreePoint;

    @Column(name = "created_datetime", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @Column(name = "updated_datetime")
    @UpdateTimestamp
    private LocalDateTime updatedDateTime;

    private Member(String sub, OAuth2Provider oAuth2Provider) {
        this.sub = sub;
        this.provider = oAuth2Provider;

        this.role = Role.ROLE_USER;
        this.point = 0;
        this.currentThemeId = 1;
        this.currentTreePoint = 0;
        this.dailyPoint = 0;
    }

    public static Member create(
        String sub,
        OAuth2Provider oAuth2Provider
    ) {
        return new Member(sub, oAuth2Provider);
    }
}
