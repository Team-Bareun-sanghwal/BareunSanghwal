package life.bareun.diary.global.security.token;

import life.bareun.diary.global.security.principal.OAuth2MemberPrincipal;
import org.springframework.security.core.Authentication;

// 토큰 검증, Authentication 객체 생성
public interface AuthTokenProvider {

    String createToken(OAuth2MemberPrincipal oauth2MemberPrincipal);

    boolean validate(AuthToken authToken);

    Authentication getOAuth2AuthenticationToken(AuthToken authToken);
}
