package life.bareun.diary.member.dto;

import life.bareun.diary.global.security.embed.OAuth2Provider;
import life.bareun.diary.member.entity.embed.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberPrincipal {
	private Long id;
	private Role role;
	private OAuth2Provider provider;
}
