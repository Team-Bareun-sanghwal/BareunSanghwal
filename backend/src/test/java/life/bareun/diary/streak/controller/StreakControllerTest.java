package life.bareun.diary.streak.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import life.bareun.diary.global.auth.embed.OAuth2Provider;
import life.bareun.diary.global.auth.token.AuthToken;
import life.bareun.diary.global.auth.token.AuthTokenProvider;
import life.bareun.diary.habit.dto.request.HabitCreateReqDto;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.habit.repository.MemberHabitRepository;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.entity.embed.Role;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.member.service.MemberService;
import life.bareun.diary.streak.dto.request.StreakRecoveryReqDto;
import life.bareun.diary.streak.service.StreakService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
class StreakControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StreakService streakService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberHabitRepository memberHabitRepository;

    @Autowired
    private AuthTokenProvider authTokenProvider;

    private String accessToken;

    private Member member;

    private MemberHabit memberHabit1;

    private MemberHabit memberHabit2;

    @BeforeEach
    void setup() throws Exception {
        memberService.loginOrRegister("testUser", OAuth2Provider.GOOGLE);
        member = memberRepository.findBySub("testUser")
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
        accessToken = authTokenProvider.createAccessToken(
            new Date(), String.valueOf(member.getId()), Role.ROLE_USER.name());
        AuthToken authToken = authTokenProvider.tokenToAuthToken(accessToken);
        SecurityContextHolder.getContext()
            .setAuthentication(authTokenProvider.getAuthentication(authToken));

        HabitCreateReqDto habitCreateReqDto1 = HabitCreateReqDto.builder()
            .habitId(1L)
            .alias("TestHabit1")
            .icon("test1")
            .dayOfWeek(List.of(1, 2, 3, 4, 5, 6, 7))
            .period(null)
            .build();

        HabitCreateReqDto habitCreateReqDto2 = HabitCreateReqDto.builder()
            .habitId(2L)
            .alias("TestHabit2")
            .icon("test2")
            .dayOfWeek(null)
            .period(2)
            .build();

        mockMvc.perform(
            post("/habits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(habitCreateReqDto1))
                .header("Authorization", accessToken)
        );

        mockMvc.perform(
            post("/habits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(habitCreateReqDto2))
                .header("Authorization", accessToken)
        );

        List<MemberHabit> memberHabitList = memberHabitRepository.findAllByIsDeletedAndMember_OrderByCreatedDatetimeDesc(false, member);
        memberHabit1 = memberHabitList.get(0);
        memberHabit2 = memberHabitList.get(1);

        // Day 1
        streakService.createDailyStreak(member, LocalDate.now().plusDays(1));
        streakService.achieveStreak(memberHabit1, LocalDate.now().plusDays(1));
        streakService.achieveStreak(memberHabit2, LocalDate.now().plusDays(1));

        // Day 2
        streakService.createDailyStreak(member, LocalDate.now().plusDays(2));

        // Day 3
        streakService.createDailyStreak(member, LocalDate.now().plusDays(3));
        streakService.achieveStreak(memberHabit2, LocalDate.now().plusDays(3));
    }

    @Test
    @DisplayName("사용자 스트릭 조회 API 테스트")
    void findMemberStreakCountTest() throws Exception {

        mockMvc.perform(
                get("/streaks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", accessToken)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.totalStreakCount").value(3))
            .andExpect(jsonPath("$.data.achieveStreakCount").value(2))
            .andExpect(jsonPath("$.data.starCount").value(1))
            .andExpect(jsonPath("$.data.longestStreakCount").value(1));
    }

    @Test
    @DisplayName("월간 해빗 스트릭 정보 조회 API 테스트")
    void findAllMemberStreakTest() throws Exception {
        mockMvc.perform(
                get("/streaks/2024-05")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", accessToken)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.achieveProportion").value(60.0))
            .andExpect(jsonPath("$.data.dayInfo[1].achieveType").value("ACHIEVE"))
            .andExpect(jsonPath("$.data.dayInfo[1].achieveCount").value(2))
            .andExpect(jsonPath("$.data.dayInfo[1].totalCount").value(2));
    }

    @Test
    @DisplayName("주어진 멤버 해빗에 해당되는 월간 해빗 스트릭 정보 조회 API 테스트")
    void findAllMemberStreakByHabitTest() throws Exception {
        mockMvc.perform(
                get("/streaks/2024-05/" + memberHabit1.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", accessToken)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.achieveProportion").value(33.0))
            .andExpect(jsonPath("$.data.dayInfo[1].achieveType").value("ACHIEVE"))
            .andExpect(jsonPath("$.data.dayInfo[1].achieveCount").value(1))
            .andExpect(jsonPath("$.data.dayInfo[1].totalCount").value(1));
    }

    @Test
    @DisplayName("스트릭 리커버리 API 테스트")
    void recoveryStreakTest() throws Exception {
        StreakRecoveryReqDto recoveryReqDto = StreakRecoveryReqDto.builder()
            .date(LocalDate.now().plusDays(2))
            .build();

        mockMvc.perform(
                patch("/streaks/recovery")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(recoveryReqDto))
                    .header("Authorization", accessToken)
            )
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.message").value("스트릭에 접근할 수 없는 날짜입니다."));
    }

    @Test
    @DisplayName("스트릭 리커버리 사용 시 정보 조회 API 테스트")
    void recoveryStreakInfoTest() throws Exception {
        mockMvc.perform(
                get("/streaks/recovery/" + LocalDate.now().plusDays(2).toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", accessToken)
            )
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.message").value("스트릭에 접근할 수 없는 날짜입니다."));
    }
}