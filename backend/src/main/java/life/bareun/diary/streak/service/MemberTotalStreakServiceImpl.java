package life.bareun.diary.streak.service;

import life.bareun.diary.global.security.util.AuthUtil;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.streak.dto.response.MemberStreakResponseDto;
import life.bareun.diary.streak.entity.MemberTotalStreak;
import life.bareun.diary.streak.exception.MemberTotalStreakErrorCode;
import life.bareun.diary.streak.exception.StreakException;
import life.bareun.diary.streak.repository.MemberTotalStreakRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberTotalStreakServiceImpl implements MemberTotalStreakService {

    private final MemberTotalStreakRepository memberTotalStreakRepository;
    private final MemberRepository memberRepository;

    @Override
    public void createMemberTotalStreak() {
        Member member = getCurrentMember();

        MemberTotalStreak memberTotalStreak = MemberTotalStreak
            .builder()
            .member(member)
            .build();

        memberTotalStreakRepository.save(memberTotalStreak);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberTotalStreak findMemberTotalStreak() {
        Member member = getCurrentMember();

        return memberTotalStreakRepository.findByMember(member)
            .orElseThrow(() ->
                new StreakException(MemberTotalStreakErrorCode.NOT_FOUND_MEMBER_TOTAL_STREAK));
    }

    @Override
    public MemberStreakResponseDto getMemberStreakResponseDto() {
        MemberTotalStreak memberTotalStreak = findMemberTotalStreak();

        return MemberStreakResponseDto
            .builder()
            .totalStreakCount(memberTotalStreak.getTotalStreakCount())
            .achieveStreakCount(memberTotalStreak.getAchieveStreakCount())
            .starCount(memberTotalStreak.getStarCount())
            .longestStreakCount(memberTotalStreak.getLongestStreak())
            .build();
    }

    @Override
    public void modifyMemberTotalStreakTotalField(int trackerCount) {
        MemberTotalStreak memberTotalStreak = findMemberTotalStreak();

        memberTotalStreak.addTotalStreakCount();
        memberTotalStreak.modifyTotalTrackerCount(trackerCount);
    }

    @Override
    public void modifyMemberTotalStreakAchieveField(boolean streakFlag, boolean starFlag) {
        MemberTotalStreak memberTotalStreak = findMemberTotalStreak();

        memberTotalStreak.addAchieveTrackerCount();
        if (streakFlag) {
            memberTotalStreak.addAchieveStreakCount();
        }

        if (starFlag) {
            memberTotalStreak.addStarCount();
        }
    }

    @Override
    public void modifyMemberTotalStreakStar(int streakCount) {
        MemberTotalStreak memberTotalStreak = findMemberTotalStreak();

        memberTotalStreak.modifyLongestStreak(streakCount);
    }

    private Member getCurrentMember() {
        return memberRepository
            .findById(AuthUtil.getMemberIdFromAuthentication())
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }
}
