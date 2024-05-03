package life.bareun.diary.global.auth.principal;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import life.bareun.diary.global.auth.embed.OAuth2MemberAuthority;
import life.bareun.diary.global.auth.embed.OAuth2Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@AllArgsConstructor
@ToString
public class OAuth2MemberPrincipal implements OAuth2User {

    private final Long memberId;
    private final List<OAuth2MemberAuthority> authorities;
    @Getter
    private final OAuth2Provider oAuth2Provider;
    @Getter
    private final boolean isNewMember;

    private Map<String, Object> attributes;


    // 로그인 이후에만 쓰이는 생성자
    public OAuth2MemberPrincipal(
        Long memberId,
        List<OAuth2MemberAuthority> authorities
    ) {
        this.memberId = memberId;
        this.authorities = authorities;
        this.oAuth2Provider = null;
        this.isNewMember = true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.copyOf(authorities); // shallow copy
    }

    @Override
    public String getName() {
        return Long.toString(memberId);
    }

    // 각 사용자가 여러 개의 권한을 가질 수 있다
    // ...고 가정하고 모든 권한을 "|"로 구분해 얻을 수 있다.
    public String getAuthority() {
        return authorities.stream()
            .map(OAuth2MemberAuthority::getAuthority)
            .collect(
                Collectors.joining("|")
            );
    }
}