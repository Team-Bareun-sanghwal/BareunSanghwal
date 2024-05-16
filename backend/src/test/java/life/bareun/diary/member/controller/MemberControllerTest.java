package life.bareun.diary.member.controller;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import life.bareun.diary.global.auth.config.SecurityConfig;
import life.bareun.diary.global.auth.embed.OAuth2Provider;
import life.bareun.diary.global.auth.token.AuthToken;
import life.bareun.diary.global.auth.token.AuthTokenProvider;
import life.bareun.diary.global.auth.util.GsonUtil;
import life.bareun.diary.habit.entity.Habit;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.habit.entity.embed.MaintainWay;
import life.bareun.diary.habit.repository.HabitRepository;
import life.bareun.diary.habit.repository.MemberHabitRepository;
import life.bareun.diary.member.dto.MemberHabitListElementDto;
import life.bareun.diary.member.dto.MemberRegisterDto;
import life.bareun.diary.member.dto.request.MemberUpdateReqDto;
import life.bareun.diary.member.dto.response.MemberInfoResDto;
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
    @Autowired
    private MemberDailyPhraseRepository memberDailyPhraseRepository;
    @Autowired
    private MemberHabitRepository memberHabitRepository;
    @Autowired
    private HabitRepository habitRepository;

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

    @Test
    @DisplayName("사용자 나무 포인트 수령 테스트")
    public void testTreePoint() throws Exception {
        // given
        // 이 범위에 있는 포인트가 반환되어야 한다.
        Integer rangeFrom = testTree.getRangeFrom();
        Integer rangeTo = testTree.getRangeTo();

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.get("/members/tree/point")
                .header(SecurityConfig.ACCESS_TOKEN_HEADER, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        // ResponseEntity는 역직렬화가 안된다..
        String res = when.andReturn().getResponse().getContentAsString();
        Integer point = Integer.parseInt(
            res.substring(
                res.lastIndexOf(':') + 1,
                res.indexOf('}')
            )
        );

        // 포인트 범위 체크(닫힌 구간)
        Assertions.assertThat(point).isBetween(rangeFrom, rangeTo);

        when.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.status")
                    .value(HttpStatus.OK.value())
            )
            .andExpect(
                jsonPath("$.message")
                    .value(String.format("%d 포인트를 획득했습니다.", point))
            )
            .andExpect( // 셀프 역직렬화가 잘 됐는지 체크
                jsonPath("$.data.point")
                    .value(point)
            );
    }

    @Test
    @DisplayName("사용자 정보 삭제 테스트 코드")
    public void testLogout() throws Exception {
        // given
        String id = testMember.getId().toString();
        String refreshToken = authTokenProvider.createRefreshToken(new Date(), id);

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.post("/members/logout")
                .header(SecurityConfig.ACCESS_TOKEN_HEADER, accessToken)
                .header(SecurityConfig.REFRESH_TOKEN_HEADER, refreshToken) // 로그아웃은 둘 다 있어야 한다.
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
                    .value("로그아웃되었습니다.")
            )
            .andExpect(
                jsonPath("$.data").isEmpty()
            );
    }

    @Test
    @DisplayName("사용자 로그아웃 테스트 코드")
    public void testDelete() throws Exception {
        // given

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.delete("/members")
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
                    .value("사용자 정보가 삭제되었습니다")
            )
            .andExpect(
                jsonPath("$.data").isEmpty()
            );
    }

    @Test
    @DisplayName("사용자 오늘의 문구 조회 테스트 코드")
    public void testDailyPhrase() throws Exception {
        // given
        String phrase = testMemberDailyPhrase.getDailyPhrase().getPhrase();

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.get("/members/daily-phrase")
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
                    .value("오늘의 문구를 읽어 왔습니다.")
            )
            .andExpect(
                jsonPath("$.data.phrase")
                    .value(phrase)
            );
    }

    @Test
    @DisplayName("사용자 해빗 리스트 테스트 코드")
    public void testHabits() throws Exception {
        // given
        Habit habit1 = habitRepository.save(Habit.builder().name("TEST_HABIT_01").build());
        String testAlias1 = "TEST_ALIAS_01";
        String testIcon1 = "TEST_ICON_01";
        MemberHabit memberHabit1 = memberHabitRepository.save(
            MemberHabit.builder()
                .habit(habit1)
                .member(testMember)
                .alias(testAlias1)
                .icon(testIcon1)
                .maintainWay(MaintainWay.PERIOD)
                .maintainAmount(1)
                .succeededDatetime(LocalDateTime.now())
                .isDeleted(false)
                .build()
        );

        Thread.sleep(5000);

        Habit habit2 = habitRepository.save(Habit.builder().name("TEST_HABIT_02").build());
        String testAlias2 = "TEST_ALIAS_02";
        String testIcon2 = "TEST_ICON_02";
        MemberHabit memberHabit2 = memberHabitRepository.save(
            MemberHabit.builder()
                .habit(habit2)
                .member(testMember)
                .alias(testAlias2)
                .icon(testIcon2)
                .maintainWay(MaintainWay.PERIOD)
                .maintainAmount(2)
                .succeededDatetime(LocalDateTime.now())
                .isDeleted(false)
                .build()
        );

        Thread.sleep(5000);

        Habit habit3 = habitRepository.save(Habit.builder().name("TEST_HABIT_03").build());
        String testAlias3 = "TEST_ALIAS_03";
        String testIcon3 = "TEST_ICON_03";
        MemberHabit memberHabit3 = memberHabitRepository.save(
            MemberHabit.builder()
                .habit(habit3)
                .member(testMember)
                .alias(testAlias3)
                .icon(testIcon3)
                .maintainWay(MaintainWay.PERIOD)
                .maintainAmount(3)
                .succeededDatetime(LocalDateTime.now())
                .isDeleted(false)
                .build()
        );

        // when
        ResultActions when = mockMvc.perform(
            MockMvcRequestBuilders.get("/members/habits")
                .header(SecurityConfig.ACCESS_TOKEN_HEADER, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        );
        System.out.println("habit1: " + memberHabit1);
        System.out.println("habit2: " + memberHabit2);
        System.out.println("habit3: " + memberHabit3);

        // then
        when.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.status")
                    .value(HttpStatus.OK.value())
            )
            .andExpect(
                jsonPath("$.message")
                    .value("사용자의 해빗 목록을 읽어왔습니다.")
            )
            .andExpect(
                jsonPath("$.data.habitList")
                    .isArray()
            )
            .andExpect(
                jsonPath("$.data.habitList[0].memberHabitId")
                    .value(memberHabit3.getId())
            )
            .andExpect(
                jsonPath("$.data.habitList[1].memberHabitId")
                    .value(memberHabit2.getId())
            )
            .andExpect(
                jsonPath("$.data.habitList[2].memberHabitId")
                    .value(memberHabit1.getId())
            );
    }
}

