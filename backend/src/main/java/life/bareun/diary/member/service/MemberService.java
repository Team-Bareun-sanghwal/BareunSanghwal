package life.bareun.diary.member.service;

import life.bareun.diary.global.auth.embed.OAuth2Provider;
import life.bareun.diary.global.auth.principal.MemberPrincipal;
import life.bareun.diary.member.dto.request.MemberUpdateReqDto;
import life.bareun.diary.member.dto.response.MemberInfoResDto;
import life.bareun.diary.member.dto.response.MemberStatisticResDto;
import life.bareun.diary.member.dto.response.MemberStreakColorResDto;
import life.bareun.diary.member.dto.response.MemberTreeColorResDto;

public interface MemberService {

    MemberPrincipal loginOrRegister(String sub, OAuth2Provider oAuth2Provider);

    void logout(String refreshToken);

    void update(MemberUpdateReqDto memberUpdateReqDto);

    void delete();

    MemberInfoResDto info();

    MemberStreakColorResDto streakColor();

    MemberTreeColorResDto treeColor();

    void grantFreeRecoveryToAllMembers();

    MemberStatisticResDto statistic();
}
