package life.bareun.diary.streak.service;

import java.time.LocalDate;
import life.bareun.diary.global.security.util.AuthUtil;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.streak.entity.MemberDailyStreak;
import life.bareun.diary.streak.entity.embed.AchieveType;
import life.bareun.diary.streak.exception.MemberDailyStreakErrorCode;
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
    public void createMemberDailyStreakInit() {
        Member member = getCurrentMember();

        memberDailyStreakRepository.findByMemberAndCreatedDate(member, LocalDate.now())
            .ifPresentOrElse(memberDailyStreakToday -> {
                throw new StreakException(MemberDailyStreakErrorCode.ALREADY_EXISTED_MEMBER_DAILY_STREAK_TODAY);
            }, () -> {
                memberDailyStreakRepository.save(MemberDailyStreak.builder()
                    .createdDate(LocalDate.now())
                    .totalTrackerCount(0)
                    .achieveType(AchieveType.NOT_EXISTED)
                    .currentStreak(0)
                    .build()
                );
            });
    }

    @Override
    public void createMemberDailyStreakSchedule(int trackerCount, AchieveType achieveType) {
        Member member = getCurrentMember();

        memberDailyStreakRepository.findByMemberAndCreatedDate(member, LocalDate.now().plusDays(1))
            .ifPresent(memberDailyStreak -> {
                throw new StreakException(MemberDailyStreakErrorCode.ALREADY_EXISTED_MEMBER_DAILY_STREAK_TOMORROW);
            });

        memberDailyStreakRepository.findByMemberAndCreatedDate(member, LocalDate.now())
            .ifPresentOrElse(memberDailyStreakToday -> {
                memberDailyStreakRepository.save(MemberDailyStreak.builder()
                    .member(member)
                    .createdDate(LocalDate.now().plusDays(1))
                    .totalTrackerCount(memberDailyStreakToday.getTotalTrackerCount() + trackerCount)
                    .achieveType(achieveType)
                    .currentStreak(memberDailyStreakToday.getCurrentStreak())
                    .build()
                );
            }, () -> {
                throw new StreakException(MemberDailyStreakErrorCode.NOT_FOUND_MEMBER_DAILY_STREAK_TODAY);
            });
    }

    private Member getCurrentMember() {
        return memberRepository.findById(AuthUtil.getMemberIdFromAuthentication())
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }
}
