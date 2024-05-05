package life.bareun.diary.global.auth.embed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum MemberStatus {
    NEW(HttpStatus.CREATED.value()),
    OLD(HttpStatus.OK.value()),
    NUL(HttpStatus.NO_CONTENT.value());


    private int code;
}
