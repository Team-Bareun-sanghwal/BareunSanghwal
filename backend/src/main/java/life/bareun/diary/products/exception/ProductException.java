package life.bareun.diary.products.exception;

import lombok.Getter;

@Getter
public class ProductException extends RuntimeException {

    private final ProductErrorCode errorCode;
    private final String message;

    public ProductException(ProductErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
