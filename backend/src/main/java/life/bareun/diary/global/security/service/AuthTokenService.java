package life.bareun.diary.global.security.service;

import java.time.Duration;

// Redis에는 로그아웃한 사용자의 refresh token만 저장한다.
// 즉 access token 발급 요청에 포함된 refresh token이
// Redis에 있으면 에러를 응답한다.
public interface AuthTokenService {

    void add(Long id, String value, Duration expiry);

    boolean isExpired(Long id);
}
