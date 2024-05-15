package life.bareun.diary.member.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Date;
import life.bareun.diary.global.auth.config.SecurityConfig;
import life.bareun.diary.global.auth.embed.OAuth2Provider;
import life.bareun.diary.global.auth.token.AuthToken;
import life.bareun.diary.global.auth.token.AuthTokenProvider;
import life.bareun.diary.global.auth.util.GsonUtil;
import life.bareun.diary.member.dto.MemberRegisterDto;
import life.bareun.diary.member.dto.request.MemberUpdateReqDto;
import life.bareun.diary.member.dto.response.MemberInfoResDto;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.entity.MemberRecovery;
import life.bareun.diary.member.entity.Tree;
import life.bareun.diary.member.entity.embed.Gender;
import life.bareun.diary.member.entity.embed.Job;
import life.bareun.diary.member.entity.embed.Role;
import life.bareun.diary.member.repository.MemberRecoveryRepository;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.member.repository.TreeRepository;
import life.bareun.diary.product.entity.StreakColor;
import life.bareun.diary.product.entity.StreakColorGrade;
import life.bareun.diary.product.entity.TreeColor;
import life.bareun.diary.product.repository.StreakColorGradeRepository;
import life.bareun.diary.product.repository.StreakColorRepository;
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
@ExtendWith(MockitoExtension.class)     // Junit5 - Mockito 연동...인데 없어도 되나? 이거의 역할이 뭐여
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // 레포지토리
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private StreakColorRepository streakColorRepository;
    @Autowired
    private TreeRepository treeRepository;
    @Autowired
    private TreeColorRepository treeColorRepository;
    @Autowired
    private StreakColorGradeRepository streakColorGradeRepository;
    @Autowired
    private MemberTotalStreakRepository memberTotalStreakRepository;
    @Autowired
    private MemberRecoveryRepository memberRecoveryRepository;

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

    private StreakColor testStreakColor;
    private TreeColor testTreeColor;

    private MemberTotalStreak testMemberTotalStreak;
    private MemberRecovery testMemberRecovery;
    private MemberDailyStreak testMemberDailyStreak;

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

        // 테스트용 나무 색상 데이터 생성
        testTreeColor = treeColorRepository.save(
            new TreeColor(
                1,
                "TEST_TREE_COLOR"
            )
        );

        // 테스트용 스트릭 색상 데이터 생성
        testStreakColor = streakColorRepository.save(
            new StreakColor(
                1,
                "TEST_STREAK_COLOR",
                streakColorGradeRepository.save(
                    new StreakColorGrade(
                        1L,
                        "TEST_STREAK_GRADE",
                        1.0F
                    )
                )
            )
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
    public void testInfo() throws Exception {
        // given
        MemberInfoResDto targetMemberInfo = new MemberInfoResDto(
            testMember.getNickname(),
            testMember.getGender(),
            testMember.getJob(),
            testMember.getBirth()
        );

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.get("/members")
                .header(SecurityConfig.ACCESS_TOKEN_HEADER, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(GsonUtil.toJson(testMember))
        );

        // then
        when.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("$.message").value("사용자 정보를 읽어왔습니다."))
            .andExpect(
                jsonPath("$.data.nickname")
                    .value(targetMemberInfo.getNickname())
            )
            .andExpect(
                jsonPath("$.data.gender")
                    .value(targetMemberInfo.getGender())
            )
            .andExpect(
                jsonPath("$.data.job")
                    .value(targetMemberInfo.getJob())
            )
            .andExpect(
                jsonPath("$.data.birthDate")
                    .value(targetMemberInfo.getBirthDate())
            );
    }

    @Test
    @DisplayName("회원 정보 수정")
    public void testUpdate() throws Exception {
        // given
        MemberUpdateReqDto targetMember = new MemberUpdateReqDto(
            testMember.getNickname(),
            testMember.getBirth(),
            testMember.getGender(),
            testMember.getJob()
        );
        String testNickname = "testnickname";
        LocalDate testBirthDate = LocalDate.now();
        Gender testGender = Gender.N;
        Job testJob = Job.JOB_SEEKER;
        MemberUpdateReqDto newMember = new MemberUpdateReqDto(
            testNickname,
            testBirthDate,
            testGender,
            testJob
        );

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.patch("/members")
                .header(SecurityConfig.ACCESS_TOKEN_HEADER, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(GsonUtil.toJson(newMember))
        );
        // then
        when.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(jsonPath("$.message").value("사용자 정보가 수정되었습니다"))
            .andExpect(jsonPath("$.data").isEmpty());

        // targetMember의 정보가 testMember의 것으로 바뀌어 있어야 한다.

        Assertions.assertThat(testMember.getNickname()).isEqualTo(testNickname);
        Assertions.assertThat(testMember.getBirth()).isEqualTo(testBirthDate);
        Assertions.assertThat(testMember.getGender()).isEqualTo(testGender);
        Assertions.assertThat(testMember.getJob()).isEqualTo(testJob);
    }

    @Test
    @DisplayName("사용자 포인트 정보 조회 테스트")
    public void testPoint() throws Exception {
        // given
        Integer point = testMember.getPoint();
        Boolean isHarvestedToday = LocalDate.now().equals(testMember.getLastHarvestedDate());

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.get("/members/point")
                .header(SecurityConfig.ACCESS_TOKEN_HEADER, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        when.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
            .andExpect(
                jsonPath("$.message")
                    .value("사용자의 현재 보유 포인트 정보를 읽어왔습니다.")
            )
            .andExpect(jsonPath("$.data.point").value(point))
            .andExpect(jsonPath("$.data.isHarvestedToday").value(isHarvestedToday));
    }

    @Test
    @DisplayName("사용자 스트릭 색상 조회 테스트")
    public void testStreakColor() throws Exception {
        // given
        // 초기 데이터 설정 확인
        Assertions.assertThat(testMember.getCurrentStreakColorId())
            .isEqualTo(testStreakColor.getId());
        String streakColor = testStreakColor.getName();

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.get("/members/streak/color")
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
                    .value("사용자의 현재 스트릭 색상 정보를 읽어왔습니다.")
            )
            .andExpect(
                jsonPath("$.data.streakName")
                    .value(streakColor)
            );
    }

    @Test
    @DisplayName("사용자 나무 정보 조회 테스트")
    public void testTree() throws Exception {
        // given
        // 초기 데이터 설정 확인
        Integer treeLevel = testTree.getLevel();
        String treeColor = testTreeColor.getName();

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.get("/members/tree")
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
                    .value("사용자의 현재 나무 색상 정보를 읽어왔습니다.")
            )
            .andExpect(
                jsonPath("$.data.treeLevel")
                    .value(
                        treeLevel
                    )
            )
            .andExpect(
                jsonPath("$.data.treeColor")
                    .value(
                        treeColor
                    )
            );
    }

    @Test
    @DisplayName("사용자 스트릭 정보 조회 테스트")
    public void testStreakInfo() throws Exception {
        // given
        int longestStreak = testMemberTotalStreak.getLongestStreak();
        int currentStreak = testMemberDailyStreak.getCurrentStreak();

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.get("/members/streak")
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
                    .value("사용자의 현재 스트릭 정보를 읽어왔습니다.")
            )
            .andExpect(
                jsonPath("$.data.longestStreak")
                    .value(longestStreak)
            )
            .andExpect(
                jsonPath("$.data.currentStreak")
                    .value(currentStreak)
            );
    }

    @Test
    @DisplayName("사용자 리커버리 정보 조회 테스트")
    public void testRecoveryCount() throws Exception {
        // given
        // Assertions.assertThat(testMemberRecovery.getMember()).isEqualTo(testMember);
        Integer freeRecoveryCount = testMemberRecovery.getFreeRecoveryCount();
        Integer paidRecoveryCount = testMember.getPaidRecoveryCount();

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.get("/members/recovery-count")
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
                    .value("사용자의 리커버리 갯수 보유 정보를 읽어왔습니다.")
            )
            .andExpect(
                jsonPath("$.data.total")
                    .value(freeRecoveryCount + paidRecoveryCount)
            )
            .andExpect(
                jsonPath("$.data.free")
                    .value(freeRecoveryCount)
            );
    }
}

