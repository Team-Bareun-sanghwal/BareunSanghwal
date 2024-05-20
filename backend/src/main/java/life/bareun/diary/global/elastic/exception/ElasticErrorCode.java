package life.bareun.diary.global.elastic.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ElasticErrorCode {

    FAIL_COLLECT_ELASTIC_LOG(HttpStatus.INTERNAL_SERVER_ERROR, "로그 수집에 실패하였습니다."),

    FAIL_SCROLL_ELASTIC_LOG(HttpStatus.INTERNAL_SERVER_ERROR,
        "SCROLL 컨텍스트 정리에 실패하였습니다.");

    private final HttpStatus status;
    private final String message;
}
