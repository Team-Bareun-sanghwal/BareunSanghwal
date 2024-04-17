package life.bareun.diary.member.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import life.bareun.diary.global.security.embed.OAuth2Provider;
import life.bareun.diary.member.entity.embed.Gender;
import life.bareun.diary.member.entity.embed.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tree_id")
    @Min(0)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tree_id")
    private Tree tree;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birth")
    private LocalDateTime birth;

    @Column(name = "provider", nullable = false)
    private OAuth2Provider provider;

    @Column(name = "sub", nullable = false)
    private String sub;

    @Column(name = "role")
    private Role role;

    @Column(name = "point")
    @Min(0)
    private Integer point;

    @Column(name = "current_theme_id")
    @Min(0)
    private Integer currentThemeId;

    @Column(name = "current_tree_id")
    @Min(0)
    private Integer currentTreeId;

    @Column(name = "daliy_point")
    @Min(0)
    private Integer daliyPoint;

    @Column(name = "current_tree_point")
    @Min(0)
    private Integer currentTreePoint;

    @Column(name = "created_datetime", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @Column(name = "updated_datetime")
    @UpdateTimestamp
    private LocalDateTime updatedDateTime;
}
