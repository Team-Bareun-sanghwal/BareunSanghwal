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
import life.bareun.diary.member.entity.embed.Gender;
import life.bareun.diary.member.entity.embed.Job;
import life.bareun.diary.member.entity.embed.Role;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.member.service.MemberService;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ExtendWith(MockitoExtension.class)     // Junit5 - Mockito 연동...인데 없어도 되나? 이거의 역할이 뭐여
public class MemberControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthTokenProvider authTokenProvider;

    @Autowired
    private MockMvc mockMvc;

    private Member testMember;

    private String accessToken;


    /**
     * @BeforeAll로 설정하면 매 테스트 수행 후 rollback이 안된다.
     * 즉 일관적인 테스트가 어려울 수 있다.
     */
    @BeforeEach
    void setUp() {
        testMember = Member.create(
            MemberRegisterDto.builder()
                .sub("testsub")
                .oAuth2Provider(OAuth2Provider.GOOGLE)
                .defaultTree(null)
                .defaultTreeColorId(1)
                .defaultStreakColorId(1)
                .build()
        );
        Member saved = memberRepository.save(testMember);
        accessToken = authTokenProvider.createAccessToken(
            new Date(),
            Long.toString(saved.getId()),
            Role.ROLE_USER.name()
        );

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
    @DisplayName("사용자 포인트 조회 테스트")
    public void testPoint() throws Exception {
        // given
        Integer point = testMember.getPoint();


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
            .andExpect(jsonPath("$.message").value(String.format("사용자의 현재 보유 포인트 정보를 읽어왔습니다.", point)))
            .andExpect(jsonPath("$.data.point").value(point))
            .andExpect(jsonPath("$.data.isHarvestedToday").isBoolean());
    }
}
