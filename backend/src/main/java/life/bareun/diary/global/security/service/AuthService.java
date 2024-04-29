package life.bareun.diary.global.security.service;

import life.bareun.diary.global.security.dto.response.AuthAccessTokenRes;

public interface AuthService {
    AuthAccessTokenRes issueAccessToken(String refreshToken);

}
