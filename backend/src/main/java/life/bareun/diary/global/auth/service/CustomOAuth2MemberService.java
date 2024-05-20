package life.bareun.diary.global.auth.service;

import java.util.List;
import java.util.Map;
import life.bareun.diary.global.auth.embed.OAuth2Provider;
import life.bareun.diary.global.auth.exception.AuthException;
import life.bareun.diary.global.auth.exception.AuthErrorCode;
import life.bareun.diary.global.auth.factory.OAuth2MemberPrincipalFactory;
import life.bareun.diary.global.auth.principal.MemberPrincipal;
import life.bareun.diary.global.auth.principal.OAuth2MemberPrincipal;
import life.bareun.diary.member.entity.embed.Role;
import life.bareun.diary.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

/*
 여기서는 Resource server로부터 인증 정보를 받아오고
 그 정보를 Security Context에 등록하는 역할을 수행한다.
 여기서 인증 성공시 CustomOAuth2SuccessHandler로,
 실패 시 CustomOAuth2FailureHandler로 이동한다.
 이 때 Security Context에 등록되는 내용은 OAuth2User의 구현체이므로
 OAuth2User의 구현체를 override함으로써 등록되는 정보를 customize할 수 있다.
 */
@RequiredArgsConstructor
public class CustomOAuth2MemberService extends DefaultOAuth2UserService {

    private final MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
        throws OAuth2AuthenticationException {
        // 유저 정보 생성
        OAuth2User oAuth2User = super.loadUser(userRequest);

        return process(userRequest, oAuth2User);
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String provider = userRequest.getClientRegistration().getClientName();

        // 이상한 provider
        if (!OAuth2Provider.validate(provider)) {
            throw new AuthException(AuthErrorCode.BAD_OAUTH_INFO);
        }

        OAuth2Provider oAuth2Provider = OAuth2Provider.valueOf(provider);
        Map<String, Object> attrs = oAuth2User.getAttributes();


        // 역할
        // List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(
        //     Role.ROLE_USER.name()
        // );

        String sub = switch (oAuth2Provider) {
            case GOOGLE -> (String) attrs.get("sub");
            case KAKAO -> Long.toString((Long) attrs.get("id"));
            case PROTECTED -> OAuth2Provider.PROTECTED.getValue();
        };

        MemberPrincipal memberPrincipal = loginOrRegister(sub, oAuth2Provider);

        // 여기서 반환된 정보가 SecurityContext에 등록된다.
        OAuth2MemberPrincipal oAuth2MemberPrincipal = OAuth2MemberPrincipalFactory.authForLogin(
            memberPrincipal,
            (DefaultOAuth2User) oAuth2User
        );

        return oAuth2MemberPrincipal;
    }

    // 로그인 또는 회원가입
    public MemberPrincipal loginOrRegister(String sub, OAuth2Provider oAuth2Provider) {
        return memberService.loginOrRegister(sub, oAuth2Provider);
    }
}
