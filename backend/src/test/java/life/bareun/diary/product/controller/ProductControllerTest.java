package life.bareun.diary.product.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import life.bareun.diary.global.auth.config.SecurityConfig;
import life.bareun.diary.global.auth.embed.OAuth2Provider;
import life.bareun.diary.global.auth.token.AuthToken;
import life.bareun.diary.global.auth.token.AuthTokenProvider;
import life.bareun.diary.habit.repository.MemberHabitRepository;
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
import life.bareun.diary.product.dto.ProductDto;
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
import life.bareun.diary.product.service.ProductService;
import life.bareun.diary.streak.entity.MemberDailyStreak;
import life.bareun.diary.streak.entity.MemberTotalStreak;
import life.bareun.diary.streak.repository.MemberTotalStreakRepository;
import life.bareun.diary.streak.service.MemberStreakService;
import life.bareun.diary.streak.service.StreakService;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    @DisplayName("상품 목록 조회 테스트 코드")
    public void testProductList() throws Exception {
        // given
        ProductDto gotchaStreak = new ProductDto();
        gotchaStreak.setKey("gotcha_streak");
        gotchaStreak.setName("알쏭달쏭 스트릭");
        gotchaStreak.setIntroduction("스트릭 색상을 랜덤으로 바꿔요");
        gotchaStreak.setDescription(
            "사용하면 프로필의 스트릭 색상을 "
                + "12가지 색상과 일부 특별한 색상 중 하나로 바꿔줘요. "
                + "아이템을 구매하는 순간 스트릭 색상이 바뀌며 영구적으로 적용되고,"
                + " 현재 색상은 사라져요."
        );
        gotchaStreak.setPrice(80);

        ProductDto gotchaTree = new ProductDto();
        gotchaTree.setKey("gotcha_tree");
        gotchaTree.setName("알쏭달쏭 나무");
        gotchaTree.setIntroduction("나무 색상을 랜덤으로 바꿔요");
        gotchaTree.setDescription(
            "사용하면 나무의 색상이 12가지 색상 중 하나로 바뀌어요."
                + " 아이템을 구매하는 순간 스트릭 색상이 바뀌며 영구적으로 적용되고, "
                + "현재 색상은 사라져요."
        );
        gotchaTree.setPrice(80);

        ProductDto recovery = new ProductDto();
        recovery.setKey("recovery");
        recovery.setName("스트릭 리커버리");
        recovery.setIntroduction("최근 한 달 중 하나의 스트릭을 복구할 수 있어요");
        recovery.setDescription(
            "최근 한 달 이내의 스트릭 하나를 복구할 수 있어요. 복구된 결과는 리캡에는 포함되지 않아요."
        );
        recovery.setPrice(150);

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.get("/products")
                .header(SecurityConfig.ACCESS_TOKEN_HEADER, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        when.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("$.message").value("상품 목록을 읽어왔습니다."))
            .andExpect(
                jsonPath("$.data.products").isNotEmpty()
            )
            .andExpect(
                jsonPath("$.data.products").isArray()
            )
            .andExpect(
                jsonPath("$.data.products", Matchers.hasSize(3))
            )
            .andExpect(
                jsonPath("$.data.products[0]").isNotEmpty()
            )
            .andExpect(
                jsonPath("$.data.products[0].key")
                    .value(gotchaStreak.getKey())
            )
            .andExpect(
                jsonPath("$.data.products[0].name")
                    .value(gotchaStreak.getName())
            )
            .andExpect(
                jsonPath("$.data.products[0].introduction")
                    .value(gotchaStreak.getIntroduction())
            )
            .andExpect(
                jsonPath("$.data.products[0].description")
                    .value(gotchaStreak.getDescription())
            )
            .andExpect(
                jsonPath("$.data.products[0].price")
                    .value(gotchaStreak.getPrice())
            )
            .andExpect(
                jsonPath("$.data.products[0]")
                    .isNotEmpty()
            )
            .andExpect(
                jsonPath("$.data.products[0].key")
                    .value(gotchaStreak.getKey())
            )
            .andExpect(
                jsonPath("$.data.products[0].name")
                    .value(gotchaStreak.getName())
            )
            .andExpect(
                jsonPath("$.data.products[0].introduction")
                    .value(gotchaStreak.getIntroduction())
            )
            .andExpect(
                jsonPath("$.data.products[0].description")
                    .value(gotchaStreak.getDescription())
            )
            .andExpect(
                jsonPath("$.data.products[0].price")
                    .value(gotchaStreak.getPrice())
            )
            .andExpect(
                jsonPath("$.data.products[1]").isNotEmpty()
            )
            .andExpect(
                jsonPath("$.data.products[1].key")
                    .value(gotchaTree.getKey())
            )
            .andExpect(
                jsonPath("$.data.products[1].name")
                    .value(gotchaTree.getName())
            )
            .andExpect(
                jsonPath("$.data.products[1].introduction")
                    .value(gotchaTree.getIntroduction())
            )
            .andExpect(
                jsonPath("$.data.products[1].description")
                    .value(gotchaTree.getDescription())
            )
            .andExpect(
                jsonPath("$.data.products[1].price")
                    .value(gotchaTree.getPrice())
            )
            .andExpect(
                jsonPath("$.data.products[2]")
                    .isNotEmpty()
            )
            .andExpect(
                jsonPath("$.data.products[2].key")
                    .value(recovery.getKey())
            )
            .andExpect(
                jsonPath("$.data.products[2].name")
                    .value(recovery.getName())
            )
            .andExpect(
                jsonPath("$.data.products[2].introduction")
                    .value(recovery.getIntroduction())
            )
            .andExpect(
                jsonPath("$.data.products[2].description")
                    .value(recovery.getDescription())
            )
            .andExpect(
                jsonPath("$.data.products[2].price")
                    .value(recovery.getPrice())
            );

    }

    @Test
    @DisplayName("스트릭 색상 변경권 구매 테스트 코드")
    public void testPurchaseStreakColor() throws Exception {
        // given
        Integer point = 10000;
        Integer initialGotchaStreakPrice = 80;
        testMember.addPoint(10000); // 포인트는 충분히 있다고 전제한다.

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.patch("/products/color/streak")
                .header(SecurityConfig.ACCESS_TOKEN_HEADER, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        when.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(
                jsonPath("$.message")
                    .value("스트릭 색상 변경권을 구매했습니다.")
            )
            .andExpect(
                jsonPath("$.data").isNotEmpty()
            )
            .andExpect(
                jsonPath("$.data.streakColorName", Matchers.in(streakColorNameList))
            );

        Assertions
            .assertThat(testMember.getPoint())
            .isEqualTo(point - initialGotchaStreakPrice);
    }

    @Test
    @DisplayName("잔액 부족인 경우의 스트릭 색상 변경권 구매 테스트 코드")
    public void testPurchaseStreakColorWhenNoPoint() throws Exception {
        // given
        testMember.addPoint(-testMember.getPoint());

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.patch("/products/color/streak")
                .header(SecurityConfig.ACCESS_TOKEN_HEADER, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        when.andExpect(status().isUnprocessableEntity())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.status")
                    .value(HttpStatus.UNPROCESSABLE_ENTITY.value())
            )
            .andExpect(
                jsonPath("$.message")
                    .value("잔액이 부족합니다.")
            )
            .andExpect(
                jsonPath("$.data").isEmpty()
            );
    }


    @Test
    @DisplayName("나무 색상 변경권 구매 테스트 코드")
    public void testPurchaseTreeColor() throws Exception {
        // given
        Integer point = 10000;
        Integer initialGotchaTreePrice = 80;
        testMember.addPoint(10000); // 포인트는 충분히 있다고 전제한다.

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.patch("/products/color/tree")
                .header(SecurityConfig.ACCESS_TOKEN_HEADER, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        when.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(
                jsonPath("$.message")
                    .value("나무 색상 변경권을 구매했습니다.")
            )
            .andExpect(
                jsonPath("$.data").isNotEmpty()
            )
            .andExpect(
                jsonPath("$.data.treeColorName", Matchers.in(treeColorNameList))
            );
        Assertions
            .assertThat(testMember.getPoint())
            .isEqualTo(point - initialGotchaTreePrice);
    }

    @Test
    @DisplayName("잔액 부족인 경우의 스트릭 색상 변경권 구매 테스트 코드")
    public void testPurchaseTreeColorWhenNoPoint() throws Exception {
        // given
        testMember.addPoint(-testMember.getPoint());

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.patch("/products/color/tree")
                .header(SecurityConfig.ACCESS_TOKEN_HEADER, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        when.andExpect(status().isUnprocessableEntity())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.status")
                    .value(HttpStatus.UNPROCESSABLE_ENTITY.value())
            )
            .andExpect(
                jsonPath("$.message")
                    .value("잔액이 부족합니다."))
            .andExpect(
                jsonPath("$.data").isEmpty()
            );
    }

    @Test
    @DisplayName("스트릭 리커버리 구매 테스트 코드")
    public void testPurchaseStreakRecovery() throws Exception {
        // given
        Integer point = 10000;
        Integer initialRecoveryPrice = 150;
        testMember.addPoint(point);
        Integer currentRecoveryPrice = testMemberRecovery.getCurrentRecoveryPrice();
        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.patch("/products/recovery")
                .header(SecurityConfig.ACCESS_TOKEN_HEADER, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        when.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.status")
                    .value(HttpStatus.OK.value())
            )
            .andExpect(
                jsonPath("$.message")
                    .value("스트릭 리커버리를 구매했습니다.")
            )
            .andExpect(
                jsonPath("$.data").isNotEmpty()
            )
            .andExpect(
                jsonPath("$.data.paidRecoveryCount").value(1)
            );
        Assertions
            .assertThat(testMemberRecovery.getCurrentRecoveryPrice())
            .isEqualTo(currentRecoveryPrice * 2);
        Assertions
            .assertThat(testMember.getPoint())
            .isEqualTo(point - initialRecoveryPrice);
    }

    @Test
    @DisplayName("잔액 부족인 경우의 리커버리 구매 테스트 코드")
    public void testPurchaseRecoveryWhenNoPoint() throws Exception {
        // given
        testMember.addPoint(-testMember.getPoint());

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.patch("/products/color/tree")
                .header(SecurityConfig.ACCESS_TOKEN_HEADER, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        when.andExpect(status().isUnprocessableEntity())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.status")
                    .value(HttpStatus.UNPROCESSABLE_ENTITY.value())
            )
            .andExpect(
                jsonPath("$.message")
                    .value("잔액이 부족합니다.")
            )
            .andExpect(
                jsonPath("$.data").isEmpty()
            );
    }
}
