package life.bareun.diary.member.service;

import java.util.List;
import life.bareun.diary.global.security.embed.OAuth2Provider;
import life.bareun.diary.global.security.principal.MemberPrincipal;
import life.bareun.diary.member.dto.request.MemberUpdateReqDto;
import life.bareun.diary.member.dto.response.MemberHabitTrackersResDto;
import life.bareun.diary.member.dto.response.MemberHabitsResDto;
import life.bareun.diary.member.dto.response.MemberInfoResDto;
import life.bareun.diary.member.dto.response.MemberLongestStreakResDto;
import life.bareun.diary.member.dto.response.MemberPointResDto;
import life.bareun.diary.member.dto.response.MemberStatisticResDto;
import life.bareun.diary.member.dto.response.MemberStreakColorResDto;
import life.bareun.diary.member.dto.response.MemberStreakRecoveryCountResDto;
import life.bareun.diary.member.dto.response.MemberTreeColorResDto;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.dto.response.MemberTreePointResDto;

public interface MemberService {

    MemberPrincipal loginOrRegister(String sub, OAuth2Provider oAuth2Provider);

    void logout(String refreshToken);

    void update(MemberUpdateReqDto memberUpdateReqDto);

    void delete();

    MemberInfoResDto info();

    MemberStreakColorResDto streakColor();

    MemberTreeColorResDto treeColor();

    List<Member> findAllMember();

    void grantFreeRecoveryToAllMembers();

    MemberPointResDto point();

    MemberLongestStreakResDto longestStreak();

    MemberStreakRecoveryCountResDto streakRecoveryCount();

    MemberStatisticResDto statistic();

    MemberHabitsResDto habits();

    MemberTreePointResDto treePoint();

    MemberHabitTrackersResDto habitTrackers(String memberHabitId);
}
