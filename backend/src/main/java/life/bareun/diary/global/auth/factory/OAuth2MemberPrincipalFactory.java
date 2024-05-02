package life.bareun.diary.global.auth.factory;

import java.util.List;
import life.bareun.diary.global.auth.embed.OAuth2MemberAuthority;
import life.bareun.diary.global.auth.principal.MemberPrincipal;
import life.bareun.diary.global.auth.principal.OAuth2MemberPrincipal;
import life.bareun.diary.member.entity.embed.Role;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class OAuth2MemberPrincipalFactory {

    // 처음 로그인에 쓰이는 팩토리
    public static OAuth2MemberPrincipal firstAuth(
        MemberPrincipal memberPrincipal,
        DefaultOAuth2User defaultOAuth2User
    ) {
        return new OAuth2MemberPrincipal(
            memberPrincipal.getId(),
            List.of(new OAuth2MemberAuthority(memberPrincipal.getRole())),
            memberPrincipal.getProvider(),
            memberPrincipal.isNewMember(),
            defaultOAuth2User.getAttributes()
        );
    }

    // 로그인 이후에 쓰이는 팩토리
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