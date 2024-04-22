package life.bareun.diary.member.service;

import life.bareun.diary.global.security.embed.OAuth2Provider;
import life.bareun.diary.member.dto.MemberPrincipal;
import life.bareun.diary.member.entity.Member;

public interface MemberService {
    // String login(String sub);
    MemberPrincipal register(String sub, OAuth2Provider oAuth2Provider);

    void update();

}
