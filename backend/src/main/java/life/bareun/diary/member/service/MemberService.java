package life.bareun.diary.member.service;

import life.bareun.diary.global.security.embed.OAuth2Provider;
import life.bareun.diary.global.security.principal.MemberPrincipal;
import life.bareun.diary.member.dto.request.MemberUpdateDtoReq;

public interface MemberService {

    MemberPrincipal loginOrRegister(String sub, OAuth2Provider oAuth2Provider);

    void update(MemberUpdateDtoReq memberUpdateDtoReq);

    void delete();

}
