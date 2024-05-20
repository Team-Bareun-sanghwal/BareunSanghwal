package life.bareun.diary.product.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode {

    INSUFFICIENT_BALANCE(HttpStatus.UNPROCESSABLE_ENTITY, "잔액이 부족합니다."),
    NO_SUCH_PRODUCT(HttpStatus.NOT_FOUND, "상품 정보가 없습니다."),
    NO_SUCH_TREE_COLOR(HttpStatus.NOT_FOUND, "해당 나무 테마 정보가 없습니다."),
    NO_SUCH_STREAK_COLOR(HttpStatus.NOT_FOUND, "해당 나무 색상 정보가 없습니다.");


    private final HttpStatus status;
    private final String message;
}
