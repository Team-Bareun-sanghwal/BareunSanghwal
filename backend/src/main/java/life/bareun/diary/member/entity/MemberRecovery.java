package life.bareun.diary.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "member_recovery")
public class MemberRecovery {

    private static final int MAX_PRICE = 2_000_000_000;
    private static final int MAX_FREE_RECOVERY_COUNT = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "free_recovery_count")
    private Integer freeRecoveryCount;

    @Column(name = "current_recovery_price")
    private Integer currentRecoveryPrice;

    private MemberRecovery(Member member, Integer freeRecoveryCount) {
        this.member = member;
        this.freeRecoveryCount = freeRecoveryCount;
    }

    public static MemberRecovery create(Member member) {
        return new MemberRecovery(member, 1);
    }

    public void afterPurchaseRecovery(int factor) {
        // 나머지는 버리므로 <가 아닌 <=를 사용한다.
        if (currentRecoveryPrice <= Integer.MAX_VALUE / factor) {
            currentRecoveryPrice *= factor;
        } else {
            currentRecoveryPrice = MAX_PRICE;
        }
    }

    public void grantFreeRecovery() {
        if (freeRecoveryCount < MAX_FREE_RECOVERY_COUNT) {
            freeRecoveryCount += 1;
        }
    }
}
