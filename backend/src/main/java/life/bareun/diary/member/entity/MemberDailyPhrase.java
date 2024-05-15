package life.bareun.diary.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "member_daily_phrase")
public class MemberDailyPhrase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_phrase_id")
    private DailyPhrase dailyPhrase;

    private MemberDailyPhrase(Member member, DailyPhrase dailyPhrase) {
        this.member = member;
        this.dailyPhrase = dailyPhrase;
    }

    public static MemberDailyPhrase create(Member member, DailyPhrase dailyPhrase) {
        return new MemberDailyPhrase(member, dailyPhrase);
    }

    public void updateDailyPhrase(DailyPhrase dailyPhrase) {
        this.dailyPhrase = dailyPhrase;
    }
}
