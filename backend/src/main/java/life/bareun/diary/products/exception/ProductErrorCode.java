package life.bareun.diary.products.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode {

    INSUFFICIENT_BALANCE(HttpStatus.UNPROCESSABLE_ENTITY, "잔액이 부족합니다."),
    INVALID_PRODUCT_KEY(HttpStatus.NOT_FOUND, "상품 정보가 없습니다.");

    private final HttpStatus status;
    private final String message;
}
