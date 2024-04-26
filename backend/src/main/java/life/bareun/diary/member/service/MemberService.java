package life.bareun.diary.member.service;

import life.bareun.diary.global.security.embed.OAuth2Provider;
import life.bareun.diary.global.security.principal.MemberPrincipal;
import life.bareun.diary.member.dto.request.MemberUpdateReq;
import life.bareun.diary.member.dto.response.MemberInfoRes;
import life.bareun.diary.member.dto.response.MemberStreakColorRes;
import life.bareun.diary.member.dto.response.MemberTreeColorRes;

public interface MemberService {

    MemberPrincipal loginOrRegister(String sub, OAuth2Provider oAuth2Provider);

    void update(MemberUpdateReq memberUpdateReq);

    void delete();

    MemberInfoRes info();

    MemberStreakColorRes streakColor();

    MemberTreeColorRes treeColor();
}
