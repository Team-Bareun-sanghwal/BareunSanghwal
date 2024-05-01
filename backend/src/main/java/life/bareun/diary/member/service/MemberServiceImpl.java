package life.bareun.diary.member.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import life.bareun.diary.global.security.embed.OAuth2Provider;
import life.bareun.diary.global.security.exception.CustomSecurityException;
import life.bareun.diary.global.security.exception.SecurityErrorCode;
import life.bareun.diary.global.security.principal.MemberPrincipal;
import life.bareun.diary.global.security.service.AuthTokenService;
import life.bareun.diary.global.security.token.AuthToken;
import life.bareun.diary.global.security.token.AuthTokenProvider;
import life.bareun.diary.global.security.util.AuthUtil;
import life.bareun.diary.habit.dto.response.HabitTrackerWeekResDto;
import life.bareun.diary.habit.service.HabitTrackerService;
import life.bareun.diary.member.dto.PracticeCountPerDayOfWeekDto;
import life.bareun.diary.member.dto.embed.DayOfWeek;
import life.bareun.diary.member.dto.request.MemberUpdateReqDto;
import life.bareun.diary.member.dto.response.MemberInfoResDto;
import life.bareun.diary.member.dto.response.MemberStreakColorResDto;
import life.bareun.diary.member.dto.response.MemberTreeColorResDto;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.entity.MemberRecovery;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.mapper.MemberMapper;
import life.bareun.diary.member.repository.MemberRecoveryRepository;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.product.exception.ProductErrorCode;
import life.bareun.diary.product.exception.ProductException;
import life.bareun.diary.product.repository.StreakColorRepository;
import life.bareun.diary.product.repository.TreeColorRepository;
import life.bareun.diary.streak.service.StreakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private static final int COLOR_INDEX_MAX = 2;
    private static final int COLOR_INDEX_DEFAULT = 1;
    private static final int COLOR_INDEX_MIN = 0;

    private final AuthTokenProvider authTokenProvider;
    private final AuthTokenService authTokenService;
    private final StreakService streakService;
    private final HabitTrackerService habitTrackerService;

    private final MemberRepository memberRepository;
    private final MemberRecoveryRepository memberRecoveryRepository;
    private final StreakColorRepository streakColorRepository;
    private final TreeColorRepository treeColorRepository;

    @Transactional(readOnly = true)
    public boolean existsBySub(String sub) {
        return memberRepository.existsBySub(sub);
    }


    @Override
    @Transactional
    public MemberPrincipal loginOrRegister(String sub, OAuth2Provider oAuth2Provider) {
        AtomicBoolean isNewMember = new AtomicBoolean(false);

        Member member = memberRepository.findBySub(sub).orElseGet(
            () -> {
                isNewMember.set(true);
                Member savedMember = memberRepository.save(
                    Member.create(sub, oAuth2Provider)
                );
                memberRecoveryRepository.save(
                    MemberRecovery.create(savedMember)
                );

                streakService.createInitialMemberStreak(savedMember);
                return savedMember;
            }
        );

        return new MemberPrincipal(
            member.getId(),
            member.getRole(),
            member.getProvider(),
            isNewMember.get()
        );
    }

    @Override
    public void logout(String refreshToken) {
        AuthToken refreshAuthToken = authTokenProvider.tokenToAuthToken(refreshToken);
        Long id = AuthUtil.getMemberIdFromAuthentication();
        Long targetId = authTokenProvider.getMemberIdFromToken(refreshAuthToken);

        // Access token과 refresh token의 사용자 정보가 다르면 예외 발생
        if (!id.equals(targetId)) {
            throw new CustomSecurityException(SecurityErrorCode.UNMATCHED_AUTHENTICATION);
        }

        authTokenService.revoke(id, refreshToken);
    }

    @Override
    @Transactional
    public void update(MemberUpdateReqDto memberUpdateReqDto) {
        Long id = AuthUtil.getMemberIdFromAuthentication();

        Member member = memberRepository.findById(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER)
            );

        member.update(memberUpdateReqDto);
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void delete() {
        Long id = AuthUtil.getMemberIdFromAuthentication();

        // 제약조건으로 인해 member_recovery를 먼저 삭제해야 한다.
        memberRecoveryRepository.deleteByMemberId(id);
        memberRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberInfoResDto info() {
        Long id = AuthUtil.getMemberIdFromAuthentication();
        Member member = memberRepository.findById(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
            );

        return MemberMapper.INSTANCE.toMemberInfoRes(member);
    }

    @Override
    public MemberStreakColorResDto streakColor() {
        Long id = AuthUtil.getMemberIdFromAuthentication();
        Member member = memberRepository.findById(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
            );

        String streakColorName = streakColorRepository.findById(
                member.getCurrentStreakColorId()
            )
            .orElseThrow(
                () -> new ProductException(ProductErrorCode.NO_SUCH_STREAK_COLOR)
            )
            .getName();

        return new MemberStreakColorResDto(streakColorName);
    }

    @Override
    public MemberTreeColorResDto treeColor() {
        Long id = AuthUtil.getMemberIdFromAuthentication();
        Member member = memberRepository.findById(id)
            .orElseThrow(
                () -> new MemberException(MemberErrorCode.NO_SUCH_MEMBER)
            );

        String treeColorName = treeColorRepository.findById(
                member.getCurrentStreakColorId()
            )
            .orElseThrow(
                () -> new ProductException(ProductErrorCode.NO_SUCH_STREAK_COLOR)
            )
            .getName();

        return new MemberTreeColorResDto(treeColorName);
    }

    @Override
    @Transactional
    public void grantFreeRecoveryToAllMembers() {
        // 회원 탈퇴 시 MemberRecovery가 먼저 삭제되므로
        // memberRecovery의 memberId가 Member 테이블에 있는지 확인할 필요가 없다.
        List<MemberRecovery> memberRecoveries = memberRecoveryRepository.findAll();

        for (MemberRecovery memberRecovery : memberRecoveries) {
            memberRecovery.sendFreeRecovery();
            memberRecoveryRepository.save(memberRecovery);
        }
    }


    @Override
    public MemberStreakColorResDto statistic() {

        // 요일 별 달성 횟수
        // 최대 또는 최대값 중복 허용
        List<PracticeCountPerDayOfWeekDto> dataPerDayOfWeek = practiceCountPerDayOfWeek();

        return null;
    }


    @Transactional(readOnly = false)
    protected List<PracticeCountPerDayOfWeekDto> practiceCountPerDayOfWeek() {
        HabitTrackerWeekResDto weekly = habitTrackerService.findAllWeekHabitTracker();
        List<Integer> weeklyValues = Arrays.asList(
            weekly.monday(), weekly.tuesday(), weekly.wednesday(),
            weekly.thursday(), weekly.friday(),
            weekly.saturday(), weekly.sunday()
        );
        int maxValue = weeklyValues.stream().max(Integer::compare).get();
        int minValue = weeklyValues.stream().min(Integer::compare).get();

        List<PracticeCountPerDayOfWeekDto> practiceCountsPerDayOfWeek = new ArrayList<>(7);
        for (int i = 0; i < weeklyValues.size(); ++i) {
            int val = weeklyValues.get(i);
            int colorIndex;
            if (val == minValue) {
                colorIndex = COLOR_INDEX_MIN;
            } else if (val == maxValue) {
                colorIndex = COLOR_INDEX_MAX;
            } else {
                colorIndex = COLOR_INDEX_DEFAULT;
            }

            practiceCountsPerDayOfWeek.add(
                new PracticeCountPerDayOfWeekDto(
                    DayOfWeek.getValueByIndex(i),
                    val,
                    colorIndex
                )
            );
        }

        return practiceCountsPerDayOfWeek;
    }
}
