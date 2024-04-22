package life.bareun.diary.global.security.token;

import io.jsonwebtoken.ExpiredJwtException;
import life.bareun.diary.global.security.principal.OAuth2MemberPrincipal;
import life.bareun.diary.member.entity.embed.Role;
import org.springframework.security.core.Authentication;

// 토큰 검증, Authentication 객체 생성
public interface AuthTokenProvider {

    String createToken(Long id, Role role);

    String createToken(OAuth2MemberPrincipal oauth2MemberPrincipal);

    boolean validate(AuthToken authToken) throws ExpiredJwtException;

    Long getMemberIdFromAuthToken(AuthToken authToken);

    Authentication getOAuth2AuthenticationToken(AuthToken authToken);
}
