package life.bareun.diary.global.security.embed;

import org.springframework.security.core.GrantedAuthority;

import life.bareun.diary.member.entity.embed.Role;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OAuth2MemberAuthority implements GrantedAuthority {
    private final Role role;

    @Override
    public String getAuthority() {
        return role.name();
    }
}

