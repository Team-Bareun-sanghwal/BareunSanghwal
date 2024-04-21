package life.bareun.diary.global.security.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import life.bareun.diary.global.security.embed.OAuth2Provider;
import life.bareun.diary.global.security.exception.CustomSecurityException;
import life.bareun.diary.global.security.exception.SecurityErrorCode;
import life.bareun.diary.global.security.factory.OAuth2MemberPrincipalFactory;
import life.bareun.diary.member.dto.MemberPrincipal;
import life.bareun.diary.member.entity.embed.Role;
import life.bareun.diary.member.service.MemberService;
import lombok.RequiredArgsConstructor;

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
		OAuth2Provider oAuth2Provider = null;
		try {
			oAuth2Provider = OAuth2Provider.valueOf(provider);
		} catch (IllegalArgumentException e) { // 이상한 provider
			throw new CustomSecurityException(SecurityErrorCode.BAD_AUTH_INFO);
		}

		System.out.println("Client name: " + provider);
		System.out.println("Scope: " + userRequest.getClientRegistration().getScopes());
		System.out.println("======OAuth2User START======");
		Map<String, Object> attrs = oAuth2User.getAttributes();
		attrs.keySet().forEach(
			(key) -> System.out.printf(
				"%s: %s, Type: %s\n",
				key,
				attrs.get(key).toString(),
				attrs.get(key).getClass()
			)
		);
		System.out.println("======OAuth2User END======");

		// 역할
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(Role.ROLE_USER.name());
		System.out.println(userRequest.getClientRegistration());

		String sub = switch (oAuth2Provider) {
			case GOOGLE -> (String) attrs.get("sub");
			case KAKAO -> Long.toString((Long) attrs.get("id"));
		};

		MemberPrincipal memberPrincipal = loginOrRegister(sub, oAuth2Provider);

		// 여기서 반환된 정보가 SecurityContext에 등록된다.
		return OAuth2MemberPrincipalFactory.firstAuth(
			memberPrincipal,
			(DefaultOAuth2User) oAuth2User
		);
	}

	// 로그인 또는 회원가입
	// 새로 등록된 회원 또는 이미 존재하는 회원의 memberId를 반환한다.
	public MemberPrincipal loginOrRegister(String sub, OAuth2Provider oAuth2Provider) {
		return memberService.register(sub, oAuth2Provider);
	}
}
