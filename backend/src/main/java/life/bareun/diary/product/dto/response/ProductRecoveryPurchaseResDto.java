package life.bareun.diary.product.dto.response;

import lombok.Builder;

@Builder
public record ProductRecoveryPurchaseResDto(
    int paidRecoveryCount
) {

}
