package life.bareun.diary.member.service;

import java.time.LocalDate;
import java.util.Date;
import life.bareun.diary.global.auth.embed.OAuth2Provider;
import life.bareun.diary.global.auth.service.AuthTokenService;
import life.bareun.diary.global.auth.token.AuthToken;
import life.bareun.diary.global.auth.token.AuthTokenProvider;
import life.bareun.diary.member.dto.MemberRegisterDto;
import life.bareun.diary.member.dto.request.MemberUpdateReqDto;
import life.bareun.diary.member.entity.DailyPhrase;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.entity.MemberDailyPhrase;
import life.bareun.diary.member.entity.MemberRecovery;
import life.bareun.diary.member.entity.Tree;
import life.bareun.diary.member.entity.embed.Gender;
import life.bareun.diary.member.entity.embed.Job;
import life.bareun.diary.member.entity.embed.Role;
import life.bareun.diary.member.repository.MemberDailyPhraseRepository;
import life.bareun.diary.member.repository.MemberRecoveryRepository;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.member.repository.TreeRepository;
import life.bareun.diary.product.entity.StreakColor;
import life.bareun.diary.product.entity.TreeColor;
import life.bareun.diary.product.repository.ProductRepository;
import life.bareun.diary.product.repository.StreakColorGradeRepository;
import life.bareun.diary.product.repository.StreakColorRepository;
import life.bareun.diary.product.repository.TreeColorGradeRepository;
import life.bareun.diary.product.repository.TreeColorRepository;
import life.bareun.diary.streak.entity.MemberDailyStreak;
import life.bareun.diary.streak.entity.MemberTotalStreak;
import life.bareun.diary.streak.repository.MemberTotalStreakRepository;
import life.bareun.diary.streak.service.MemberStreakService;
import life.bareun.diary.streak.service.StreakService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StreakColorGradeRepository streakColorGradeRepository;
    @Autowired
    private StreakColorRepository streakColorRepository;
    @Autowired
    private TreeRepository treeRepository;
    @Autowired
    private TreeColorGradeRepository treeColorGradeRepository;
    @Autowired
    private TreeColorRepository treeColorRepository;
    @Autowired
    private MemberRecoveryRepository memberRecoveryRepository;
    @Autowired
    private MemberDailyPhraseRepository memberDailyPhraseRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberTotalStreakRepository memberTotalStreakRepository;

    // 토큰
    @Autowired
    private AuthTokenProvider authTokenProvider;

    // 서비스
    @Autowired
    private StreakService streakService;
    @Autowired
    private MemberStreakService memberStreakService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthTokenService authTokenService;

    // 테스트 용 데이터
    private Member testMember;
    private Tree testTree;
    private TreeColor testTreeColor;
    private StreakColor testStreakColor;
    private MemberTotalStreak testMemberTotalStreak;
    private MemberRecovery testMemberRecovery;
    private MemberDailyStreak testMemberDailyStreak;
    private MemberDailyPhrase testMemberDailyPhrase;
    private String accessToken;
    private String refreshToken;


    /**
     * @BeforeAll로 설정하면 매 테스트 수행 후 rollback이 안된다. 즉 일관적인 테스트가 어려울 수 있다.
     */
    @BeforeEach
    void setUp() {
        // 테스트용 나무 데이터 생성
        testTree = treeRepository.save(
            new Tree(
                1,
                1,
                10,
                100
            )
        );

        // 테스트 멤버 생성용 나무 색상 데이터 생성
        testTreeColor = treeColorRepository.findById(1)
            .orElseThrow(
                () -> new AssertionError("초기 나무 색상 데이터 세팅 실패")
            );

        // 테스트 멤버 생성용 스트릭 색상 데이터 생성
        testStreakColor = streakColorRepository.findById(1)
            .orElseThrow(
                () -> new AssertionError("초기 스트릭 색상 데이터 세팅 실패")
            );

        // 테스트용 사용자 생성
        testMember = memberRepository.save(
            Member.create(
                MemberRegisterDto.builder()
                    .sub("testsub")
                    .oAuth2Provider(OAuth2Provider.GOOGLE)
                    .defaultTree(testTree)
                    .defaultTreeColorId(testTreeColor.getId())
                    .defaultStreakColorId(testStreakColor.getId())
                    .build()
            )
        );

        // 초기 스트릭 데이터 생성
        streakService.initialMemberStreak(testMember);
        testMemberTotalStreak = memberTotalStreakRepository.findByMember(testMember)
            .orElseThrow(
                () -> new AssertionError("초기 스트릭 세팅 실패")
            );

        // 초기 사용자 리커버리 데이터 생성
        testMemberRecovery = memberRecoveryRepository.save(MemberRecovery.create(testMember));

        // 사용자 스트릭 현황 데이터 생성
        testMemberDailyStreak = memberStreakService.findMemberDailyStreak(
                testMember,
                LocalDate.now()
            )
            .orElseThrow(
                () -> new AssertionError("초기 스트릭 현황 세팅 실패")
            );

        // 사용자 오늘의 문구 데이터 생성
        testMemberDailyPhrase = memberDailyPhraseRepository.save(
            MemberDailyPhrase.create(
                testMember,
                new DailyPhrase(1L, "TEST_DAILY_PHRASE")
            )
        );

        // 테스트용 인증 토큰 생성
        Date currDate = new Date();
        accessToken = authTokenProvider.createAccessToken(
            currDate,
            Long.toString(testMember.getId()),
            Role.ROLE_USER.name()
        );

        refreshToken = authTokenProvider.createRefreshToken(
            currDate,
            Long.toString(testMember.getId())
        );

        // SecurityContext에 테스트용 Authentication 등록
        AuthToken authToken = authTokenProvider.tokenToAuthToken(accessToken);
        Authentication authentication = authTokenProvider.getAuthentication(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    public void 회원_정보_수정() throws Exception {
        // given
        // Member 생성시 정보는 모두 null이므로 중복 검사는 불필요하다.
        String newNickName = "TEST_NICKNAME";
        LocalDate newBirthDate = LocalDate.now();
        Gender newGender = Gender.F;
        Job newJob = Job.JOB_SEEKER;

        // when
        memberService.update(
            new MemberUpdateReqDto(
                newNickName,
                newBirthDate,
                newGender,
                newJob
            )
        );

        // then
        Assertions.assertThat(testMember.getNickname()).isEqualTo(newNickName);
        Assertions.assertThat(testMember.getBirth()).isEqualTo(newBirthDate);
        Assertions.assertThat(testMember.getGender()).isEqualTo(newGender);
        Assertions.assertThat(testMember.getJob()).isEqualTo(newJob);

    }


    @Test
    @DisplayName("회원 탈퇴 테스트")
    public void 회원_탈퇴() throws Exception {
        // given
        // testMember

        // null check만 수행하므로 아무 값이나 넣어도 된다.
        memberService.update(
            new MemberUpdateReqDto(
                "TEST_NICKNAME",
                LocalDate.now(),
                Gender.M,
                Job.JOB_SEEKER
            )
        );

        // when
        memberService.delete();

        // then
        Assertions.assertThat(testMember.getSub()).matches("DeletedUser\\d+");
        Assertions.assertThat(testMember.getNickname()).isNull();
        Assertions.assertThat(testMember.getBirth()).isNull();
        Assertions.assertThat(testMember.getGender()).isNull();
        Assertions.assertThat(testMember.getJob()).isNull();
        Assertions.assertThat(testMember.getIsDeleted()).isEqualTo(true);
    }

    @Test
    @DisplayName("로그아웃 테스트")
    public void 로그아웃() throws Exception {
        // given
        // accessToken, refreshToken

        // when
        memberService.logout(accessToken, refreshToken);

        // then
        Assertions
            .assertThat(
                authTokenService.isRevokedAccessToken(
                    authTokenProvider.tokenToAuthToken(accessToken)
                )
            )
            .isTrue();

        Assertions
            .assertThat(
                authTokenService.isRevokedRefreshToken(
                    authTokenProvider.tokenToAuthToken(refreshToken)
                )
            )
            .isTrue();
    }

}
