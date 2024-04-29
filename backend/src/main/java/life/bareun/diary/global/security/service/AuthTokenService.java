package life.bareun.diary.global.security.service;

// Redis에는 로그아웃한 사용자의 refresh token만 저장한다.
// 즉 access token 발급 요청에 포함된 refresh token이
// Redis에 있으면 에러를 응답한다.
public interface AuthTokenService {

    void revoke(Long id, String value);

    boolean isRevoked(String refreshToken);
}
