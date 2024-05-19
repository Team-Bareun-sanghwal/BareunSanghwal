package life.bareun.diary.product.service;


import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import life.bareun.diary.global.auth.embed.OAuth2Provider;
import life.bareun.diary.global.auth.token.AuthToken;
import life.bareun.diary.global.auth.token.AuthTokenProvider;
import life.bareun.diary.member.dto.MemberRegisterDto;
import life.bareun.diary.member.entity.DailyPhrase;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.entity.MemberDailyPhrase;
import life.bareun.diary.member.entity.MemberRecovery;
import life.bareun.diary.member.entity.Tree;
import life.bareun.diary.member.entity.embed.Role;
import life.bareun.diary.member.repository.MemberDailyPhraseRepository;
import life.bareun.diary.member.repository.MemberRecoveryRepository;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.member.repository.TreeRepository;
import life.bareun.diary.product.entity.StreakColor;
import life.bareun.diary.product.entity.StreakColorGrade;
import life.bareun.diary.product.entity.TreeColor;
import life.bareun.diary.product.entity.TreeColorGrade;
import life.bareun.diary.product.exception.ProductErrorCode;
import life.bareun.diary.product.exception.ProductException;
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
public class ProductServiceTest {

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
    private ProductService productService;

    // 테스트 용 데이터
    private Member testMember;
    private Tree testTree;
    private TreeColorGrade testTreeColorGrade;
    private TreeColor testTreeColor;
    private List<String> treeColorNameList;
    private List<String> streakColorNameList;
    private StreakColorGrade testStreakColorGrade;
    private StreakColor testStreakColor;
    private Integer testStreakColorPrice;
    private Integer testTreeColorPrice;
    private Integer testRecoveryPrice;
    private MemberTotalStreak testMemberTotalStreak;
    private MemberRecovery testMemberRecovery;
    private MemberDailyStreak testMemberDailyStreak;
    private MemberDailyPhrase testMemberDailyPhrase;
    private String accessToken;


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

        // 상품 가격 설정
        testStreakColorPrice = productRepository.findByProductKey("gotcha_streak")
            .orElseThrow(
                () -> new ProductException(ProductErrorCode.NO_SUCH_PRODUCT)
            )
            .getPrice();
        testTreeColorPrice = productRepository.findByProductKey("gotcha_tree")
            .orElseThrow(
                () -> new ProductException(ProductErrorCode.NO_SUCH_PRODUCT)
            )
            .getPrice();
        testRecoveryPrice = productRepository.findByProductKey("recovery")
            .orElseThrow(
                () -> new ProductException(ProductErrorCode.NO_SUCH_PRODUCT)
            )
            .getPrice();

        // 테스트용 나무 색상 데이터 구축
        treeColorNameList = treeColorRepository.findAll().stream()
            .sorted(Comparator.comparingInt(TreeColor::getId))
            .map(TreeColor::getName)
            .toList();

        testTreeColorGrade = treeColorGradeRepository.findById(1)
            .orElseThrow(
                () -> new AssertionError("초기 스트릭 색상 등급 데이터 세팅 실패")
            );

        // 테스트 멤버 생성용 나무 색상 데이터 생성
        testTreeColor = treeColorRepository.findById(1)
            .orElseThrow(
                () -> new AssertionError("초기 나무 색상 데이터 세팅 실패")
            );

        // 테스트용 스트릭 색상 데이터 구축
        streakColorNameList = streakColorRepository.findAll().stream()
            .sorted(Comparator.comparingInt(StreakColor::getId))
            .map(StreakColor::getName)
            .toList();

        // 테스트 멤버 생성용 스트릭 색상 데이터 생성
        testStreakColorGrade = streakColorGradeRepository.findById(1L)
            .orElseThrow(
                () -> new AssertionError("초기 스트릭 색상 등급 데이터 세팅 실패")
            );

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
        accessToken = authTokenProvider.createAccessToken(
            new Date(),
            Long.toString(testMember.getId()),
            Role.ROLE_USER.name()
        );

        // SecurityContext에 테스트용 Authentication 등록
        AuthToken authToken = authTokenProvider.tokenToAuthToken(accessToken);
        Authentication authentication = authTokenProvider.getAuthentication(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("나무 색상 변경권 구매 서비스 테스트")
    public void 나무_색상_변경권_구매() throws Exception {
        // 10000번까지 호출하면서 양 끝 값이 나오는지 테스트해본다.
        // 사용자의 포인트가 정확히 감소하는지 확인한다.
        // 사용자의 나무 색상이 변경되는지 확인한다.
        // 잔액 부족 시 구매 불가는 ControllerTest에서 확인했으므로 skip

        // given
        testMember.addPoint(-testMember.getPoint());
        final int limit = 10000;
        int initialPoint = (testTreeColorPrice * limit * 2) + 1;
        testMember.addPoint(initialPoint);
        ;
        int countA = 1;
        int countB = 1;
        String treeColorNameA = treeColorNameList.get(0);
        String treeColorNameB = treeColorNameList.get(treeColorNameList.size() - 1);

        // when
        while (
            countA++ <= limit
                && !productService.buyTreeGotcha().treeColorName().equals(treeColorNameA)
        ) {
            // Thread.sleep(500L);
        }

        int afterPointA = testMember.getPoint();

        while (
            countB++ <= limit
                && !productService.buyTreeGotcha().treeColorName().equals(treeColorNameB)
        ) {
            // Thread.sleep(500L);
        }
        int afterPointB = testMember.getPoint();

        String BoughtTreeColorName = productService.buyTreeGotcha().treeColorName();

        // then
        Assertions.assertThat(countA).isLessThanOrEqualTo(limit);
        Assertions.assertThat(afterPointA)
            .isEqualTo(initialPoint - ((countA - 1) * testTreeColorPrice));

        Assertions.assertThat(countB).isLessThanOrEqualTo(limit);
        Assertions.assertThat(afterPointB)
            .isEqualTo(afterPointA - ((countB - 1) * testTreeColorPrice));
        Integer BoughtTreeColorId = null;
        for (int i = 0; i < treeColorNameList.size(); ++i) {
            if (treeColorNameList.get(i).equals(BoughtTreeColorName)) {
                BoughtTreeColorId = i + 1;
                break;
            }
        }
        Integer currentTreeColorId = testMember.getCurrentTreeColorId();
        Assertions.assertThat(currentTreeColorId).isEqualTo(BoughtTreeColorId);
    }

    @Test
    @DisplayName("스트릭 색상 변경권 구매 서비스 테스트")
    public void 스트릭_색상_변경권_구매() throws Exception {
        // 10000번까지 호출하면서 양 끝 값이 나오는지 테스트해본다.
        // 사용자의 포인트가 정확히 감소하는지 확인한다.
        // 사용자의 스트릭 색상이 변경되는지 확인한다.
        // 잔액 부족 시 구매 불가는 ControllerTest에서 확인했으므로 skip

        // given
        testMember.addPoint(-testMember.getPoint());
        final int limit = 10000;
        int initialPoint = (testStreakColorPrice * limit * 2) + 1;
        testMember.addPoint(initialPoint);
        int countA = 1;
        int countB = 1;
        String streakColorNameA = streakColorNameList.get(0);
        String streakColorNameB = streakColorNameList.get(streakColorNameList.size() - 1);

        // when
        while (
            countA++ <= limit
                && !productService.buyStreakGotcha().streakColorName().equals(streakColorNameA)
        ) {
            // 너무 빠르게 호출 시 DB에서 리스트를 읽어오지 못해
            // RANDOM.nextInt() 에서 bound가 0이 되는 문제가 발생할 수 있다.
            // 이런 경우 Thread.sleep()을 설정한 후 재테스트한다.
            // Thread.sleep(500L);
        }
        int afterPointA = testMember.getPoint();
        System.out.println("A clear!");

        while (
            countB++ <= limit
                && !productService.buyStreakGotcha().streakColorName().equals(streakColorNameB)
        ) {
            // Thread.sleep(500L);
        }
        int afterPointB = testMember.getPoint();

        String BoughtStreakColorName = productService.buyStreakGotcha().streakColorName();

        // then
        Assertions.assertThat(countA).isLessThanOrEqualTo(limit);
        Assertions.assertThat(afterPointA)
            .isEqualTo(initialPoint - ((countA - 1) * testStreakColorPrice));

        Assertions.assertThat(countB).isLessThanOrEqualTo(limit);
        Assertions.assertThat(afterPointB)
            .isEqualTo(afterPointA - ((countB - 1) * testStreakColorPrice));

        Integer BoughtStreakColorId = null;
        for (int i = 0; i < streakColorNameList.size(); ++i) {
            if (streakColorNameList.get(i).equals(BoughtStreakColorName)) {
                BoughtStreakColorId = i + 1;
                break;
            }
        }
        Integer currentStreakColorId = testMember.getCurrentStreakColorId();
        Assertions.assertThat(currentStreakColorId).isEqualTo(BoughtStreakColorId);
    }

    @Test
    @DisplayName("스트릭 리커버리 변경권 구매 서비스 테스트")
    public void 스트릭_리커버리_변경권_구매() throws Exception {
        // 사용자의 포인트가 정확히 감소하는지 확인한다.
        // 구매 후 MemberRecovery에서 가격이 두 배가 되는지 확인한다.
        // 사용자의 보유 갯수가 정확히 증가하는지 확인한다.
        // 잔액 부족 시 구매 불가는 ControllerTest에서 확인했으므로 skip

        // given
        testMember.addPoint(-testMember.getPoint());
        testMember.addPoint(testRecoveryPrice);
        Integer initialPaidRecoveryCount = testMember.getPaidRecoveryCount();

        // when
        productService.buyRecovery();

        // then
        Assertions.assertThat(testMember.getPoint()).isEqualTo(0);
        Assertions.assertThat(testMemberRecovery.getCurrentRecoveryPrice())
            .isEqualTo(testRecoveryPrice*2);
        Assertions.assertThat(testMember.getPaidRecoveryCount())
            .isEqualTo(initialPaidRecoveryCount + 1);
    }
}
