package life.bareun.diary.global.security.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthAccessTokenRes {
    private String accessToken;
}
