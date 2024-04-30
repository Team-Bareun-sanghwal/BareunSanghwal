package life.bareun.diary.global.elastic.exception;

import lombok.Getter;

@Getter
public class ElasticException extends RuntimeException {

    private final ElasticErrorCode errorCode;
    private final String message;

    public ElasticException(ElasticErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
