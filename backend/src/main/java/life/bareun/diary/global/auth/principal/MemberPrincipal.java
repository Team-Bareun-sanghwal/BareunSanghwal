package life.bareun.diary.global.auth.principal;

import life.bareun.diary.global.auth.embed.OAuth2Provider;
import life.bareun.diary.member.entity.embed.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberPrincipal {

    private Long id;
    private Role role;
    private OAuth2Provider provider;
    private boolean isNewMember;
}
