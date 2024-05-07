package life.bareun.diary.member.service;

import java.util.List;
import life.bareun.diary.global.security.embed.OAuth2Provider;
import life.bareun.diary.global.security.principal.MemberPrincipal;
import life.bareun.diary.member.dto.request.MemberUpdateReqDto;
import life.bareun.diary.member.dto.response.MemberInfoResDto;
import life.bareun.diary.member.dto.response.MemberStreakColorResDto;
import life.bareun.diary.member.dto.response.MemberTreeColorResDto;
import life.bareun.diary.member.entity.Member;

public interface MemberService {

    MemberPrincipal loginOrRegister(String sub, OAuth2Provider oAuth2Provider);

    void update(MemberUpdateReqDto memberUpdateReqDto);

    void delete();

    MemberInfoResDto info();

    MemberStreakColorResDto streakColor();

    MemberTreeColorResDto treeColor();

    List<Member> findAllMember();
}
