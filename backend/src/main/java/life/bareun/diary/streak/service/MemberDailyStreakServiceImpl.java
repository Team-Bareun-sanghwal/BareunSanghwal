package life.bareun.diary.streak.service;

import java.time.LocalDate;
import life.bareun.diary.global.security.util.AuthUtil;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.streak.entity.MemberDailyStreak;
import life.bareun.diary.streak.entity.embed.AchieveType;
import life.bareun.diary.streak.exception.StreakErrorCode;
import life.bareun.diary.streak.exception.StreakException;
import life.bareun.diary.streak.repository.MemberDailyStreakRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberDailyStreakServiceImpl implements MemberDailyStreakService {

    private final MemberDailyStreakRepository memberDailyStreakRepository;
    private final MemberRepository memberRepository;

    @Override
    public void createMemberDailyStreak(int trackerCount, AchieveType achieveType) {
        Member member = getCurrentMember();

        memberDailyStreakRepository.findByMemberAndCreatedDate(member, LocalDate.now()).ifPresent(memberDailyStreak -> {
            throw new StreakException(StreakErrorCode.ALREADY_EXISTED_MEMBER_DAILY_STREAK);
        });

        memberDailyStreakRepository.findByMemberAndCreatedDate(member, LocalDate.now().minusDays(1))
            .ifPresentOrElse(memberDailyStreakYesterday -> {
                memberDailyStreakRepository.save(MemberDailyStreak.builder()
                    .member(member)
                    .createdDate(LocalDate.now())
                    .totalTrackerCount(memberDailyStreakYesterday.getTotalTrackerCount() + trackerCount)
                    .achieveType(achieveType)
                    .currentStreak(memberDailyStreakYesterday.getCurrentStreak())
                    .build()
                );
            }, () -> {
                memberDailyStreakRepository.save(MemberDailyStreak.builder()
                    .member(member)
                    .createdDate(LocalDate.now())
                    .totalTrackerCount(trackerCount)
                    .achieveType(achieveType)
                    .currentStreak(0)
                    .build()
                );
            });
    }

    private Member getCurrentMember() {
        return memberRepository.findById(AuthUtil.getMemberIdFromAuthentication())
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }
}
