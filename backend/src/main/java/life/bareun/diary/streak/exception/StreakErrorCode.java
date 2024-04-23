package life.bareun.diary.streak.exception;

import org.springframework.http.HttpStatus;

public interface StreakErrorCode {

    HttpStatus getStatus();

    String getMessage();
}
