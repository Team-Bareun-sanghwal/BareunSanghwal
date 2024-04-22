package life.bareun.diary.global.security.factory;

import java.util.List;
import life.bareun.diary.global.security.embed.OAuth2MemberAuthority;
import life.bareun.diary.global.security.principal.OAuth2MemberPrincipal;
import life.bareun.diary.member.dto.MemberPrincipal;
import life.bareun.diary.member.entity.embed.Role;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class OAuth2MemberPrincipalFactory {

    // 신규 사용자
    public static OAuth2MemberPrincipal firstAuth(
        MemberPrincipal memberPrincipal,
        DefaultOAuth2User defaultOAuth2User
    ) {
        return new OAuth2MemberPrincipal(
            memberPrincipal.getId(),
            List.of(new OAuth2MemberAuthority(memberPrincipal.getRole())),
            memberPrincipal.getProvider(),
            defaultOAuth2User.getAttributes()
        );
    }

    public static OAuth2MemberPrincipal of(
        Long id,
        Role role
    ) {
        return new OAuth2MemberPrincipal(
            id,
            List.of(new OAuth2MemberAuthority(role))
        );
    }


}