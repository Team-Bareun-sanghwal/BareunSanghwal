package life.bareun.diary.global.security.embed;

import life.bareun.diary.member.entity.embed.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public class OAuth2MemberAuthority implements GrantedAuthority {

    private final Role role;

    @Override
    public String getAuthority() {
        return role.name();
    }
}

