package life.bareun.diary.member.service;

import java.util.List;
import life.bareun.diary.global.auth.embed.OAuth2Provider;
import life.bareun.diary.global.auth.principal.MemberPrincipal;
import life.bareun.diary.member.dto.request.MemberUpdateReqDto;
import life.bareun.diary.member.dto.response.MemberDailyPhraseResDto;
import life.bareun.diary.member.dto.response.MemberHabitListResDto;
import life.bareun.diary.member.dto.response.MemberHabitTrackersResDto;
import life.bareun.diary.member.dto.response.MemberInfoResDto;
import life.bareun.diary.member.dto.response.MemberPointResDto;
import life.bareun.diary.member.dto.response.MemberStatisticResDto;
import life.bareun.diary.member.dto.response.MemberStreakColorResDto;
import life.bareun.diary.member.dto.response.MemberStreakInfoResDto;
import life.bareun.diary.member.dto.response.MemberStreakRecoveryCountResDto;
import life.bareun.diary.member.dto.response.MemberTreeInfoResDto;
import life.bareun.diary.member.dto.response.MemberTreePointResDto;
import life.bareun.diary.member.entity.Member;

public interface MemberService {

    MemberPrincipal loginOrRegister(String sub, OAuth2Provider oAuth2Provider);

    void logout(String accessToken, String refreshToken);

    void update(MemberUpdateReqDto memberUpdateReqDto);

    void delete();

    MemberInfoResDto info();

    MemberStreakColorResDto streakInfo();

    MemberTreeInfoResDto treeInfo();

    List<Member> findAllMember();

    void initStreakRecoveryForAllMembersMonthly();

    MemberPointResDto point();

    MemberStreakInfoResDto streak();

    MemberStreakRecoveryCountResDto streakRecoveryCount();

    MemberStatisticResDto statistic();

    MemberHabitListResDto habits();

    MemberTreePointResDto treePoint();

    MemberHabitTrackersResDto habitTrackers(String memberHabitId);

    MemberDailyPhraseResDto dailyPhrase();

    void updateMemberDailyPhraseForAllMembersDaily();
}
