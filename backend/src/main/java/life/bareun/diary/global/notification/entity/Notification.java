package life.bareun.diary.global.notification.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import life.bareun.diary.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_category_id")
    private NotificationCategory notificationCategory;

    @Column(name = "content")
    private String content;

    @Column(name = "is_read")
    private Boolean isRead;

    @CreationTimestamp
    @Column(name = "created_datetime")
    private LocalDateTime createdDatetime;

    @Builder
    public Notification(Member member, NotificationCategory notificationCategory, String content, Boolean isRead,
        LocalDateTime createdDatetime) {
        this.member = member;
        this.notificationCategory = notificationCategory;
        this.content = content;
        this.isRead = isRead;
        this.createdDatetime = createdDatetime;
    }
}
