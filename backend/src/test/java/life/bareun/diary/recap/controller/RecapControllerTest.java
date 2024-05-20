package life.bareun.diary.recap.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Date;
import life.bareun.diary.global.auth.embed.OAuth2Provider;
import life.bareun.diary.global.auth.token.AuthToken;
import life.bareun.diary.global.auth.token.AuthTokenProvider;
import life.bareun.diary.habit.entity.Habit;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.habit.entity.embed.MaintainWay;
import life.bareun.diary.habit.repository.HabitRepository;
import life.bareun.diary.habit.repository.MemberHabitRepository;
import life.bareun.diary.member.dto.MemberRegisterDto;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.entity.embed.Role;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.member.repository.TreeRepository;
import life.bareun.diary.recap.entity.Recap;
import life.bareun.diary.recap.entity.RecapHabitAccomplished;
import life.bareun.diary.recap.entity.RecapHabitRatio;
import life.bareun.diary.recap.entity.embed.Occasion;
import life.bareun.diary.recap.repository.RecapHabitAccomplishedRepository;
import life.bareun.diary.recap.repository.RecapHabitRatioRepository;
import life.bareun.diary.recap.repository.RecapRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RecapControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthTokenProvider authTokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TreeRepository treeRepository;

    @Autowired
    private RecapRepository recapRepository;

    @Autowired
    private RecapHabitAccomplishedRepository recapHabitAccomplishedRepository;

    @Autowired
    private RecapHabitRatioRepository recapHabitRatioRepository;

    @Autowired
    private MemberHabitRepository memberHabitRepository;

    @Autowired
    private HabitRepository habitRepository;

    private String accessToken;

    private Member member;

    @BeforeEach
    void eachSetup() {
        member = memberRepository.save(Member.create(
            MemberRegisterDto.builder().sub("mockMember").oAuth2Provider(OAuth2Provider.KAKAO)
                .defaultStreakColorId(1).defaultTree(treeRepository.findById(1L).orElseThrow())
                .defaultTreeColorId(1).build()));
        accessToken = authTokenProvider.createAccessToken(new Date(),
            String.valueOf(member.getId()),
            Role.ROLE_USER.name());
        AuthToken authToken = authTokenProvider.tokenToAuthToken(accessToken);
        SecurityContextHolder.getContext()
            .setAuthentication(authTokenProvider.getAuthentication(authToken));
    }

    @Test
    @DisplayName("리캡 리스트 조회")
    void findAllRecap() throws Exception {
        recapRepository.save(Recap.builder().member(member).maxHabitImage("imageUrl")
            .mostFrequencyTime(Occasion.DAWN).wholeStreak(1).mostFrequencyWord("word").build());

        mockMvc.perform(get("/recaps")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("리캡 리스트 조회를 성공하였습니다."))
            .andExpect(jsonPath("$.data.yearList[0]").value(LocalDate.now().getYear()))
            .andExpect(
                jsonPath("$.data.recapGroupList[0].year").value(LocalDate.now().getYear()))
            .andExpect(jsonPath("$.data.recapGroupList[0].recapList[0].image").value("imageUrl"))
            .andExpect(jsonPath("$.data.recapGroupList[0].recapList[0].recapId").isNotEmpty())
            .andExpect(jsonPath("$.data.recapGroupList[0].recapList[0].period").isNotEmpty());
    }

    @Test
    @DisplayName("리캡 상세조회")
    void findDetailRecap() throws Exception {
        LocalDate now = LocalDate.now();

        Recap recap = recapRepository.save(Recap.builder().member(member).maxHabitImage("imageUrl")
            .mostFrequencyTime(Occasion.DAWN).wholeStreak(1).mostFrequencyWord("word").build());


        // memberHabit 1

        Habit habit1 = habitRepository.findById(1L).orElseThrow();

        MemberHabit memberHabit1 = memberHabitRepository.save(
            MemberHabit.builder().habit(habit1).member(member).alias("임시").icon("tempIcon")
                .maintainAmount(0).maintainWay(
                    MaintainWay.DAY).isDeleted(false).build());

        recapHabitAccomplishedRepository.save(
            RecapHabitAccomplished.builder().memberHabit(memberHabit1).recap(recap)
                .achievementRate(75).missCount(1).actionCount(3).createdMonth(now.getMonthValue())
                .createdYear(now.getYear()).isBest(false).build());

        recapHabitRatioRepository.save(
            RecapHabitRatio.builder().recap(recap).habit(habit1).createdMonth(now.getMonthValue()).ratio(40)
                .createdYear(now.getYear()).build());

        // memberHabit 2
        Habit habit2 = habitRepository.findById(2L).orElseThrow();

        MemberHabit memberHabit2 = memberHabitRepository.save(
            MemberHabit.builder().habit(habit2).member(member).alias("임시!").icon("tempIcon!")
                .maintainAmount(0).maintainWay(
                    MaintainWay.DAY).isDeleted(false).build());

        recapHabitAccomplishedRepository.save(
            RecapHabitAccomplished.builder().memberHabit(memberHabit2).recap(recap)
                .achievementRate(50).missCount(3).actionCount(3).createdMonth(now.getMonthValue())
                .createdYear(now.getYear()).isBest(true).build());

        recapHabitRatioRepository.save(
            RecapHabitRatio.builder().recap(recap).habit(habit2).createdMonth(now.getMonthValue()).ratio(60)
                .createdYear(now.getYear()).build());

        System.out.println(habit1.getName());
        System.out.println(habit2.getName());

        mockMvc.perform(get("/recaps/" + recap.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("리캡 상세 조회를 성공하였습니다."))
            .andExpect(jsonPath("$.data.year").value(LocalDate.now().getYear()))
            .andExpect(jsonPath("$.data.month").value(LocalDate.now().getMonthValue()))
            .andExpect(jsonPath("$.data.mostSubmitTime").value("DAWN"))
            .andExpect(jsonPath("$.data.collectedStar").value(1))
            .andExpect(jsonPath("$.data.myKeyWord").value("word"))
            .andExpect(jsonPath("$.data.image").value("imageUrl"))
            .andExpect(jsonPath("$.data.mostSucceededHabit").value(habit2.getName()))
            .andExpect(jsonPath("$.data.mostSucceededMemberHabit").value(memberHabit2.getAlias()))
            .andExpect(jsonPath("$.data.averageRateByMemberHabit").value(62.5))
            .andExpect(jsonPath("$.data.rateByMemberHabitList[0].name").value(memberHabit2.getAlias()))
            .andExpect(jsonPath("$.data.rateByMemberHabitList[0].missCount").value(3))
            .andExpect(jsonPath("$.data.rateByMemberHabitList[0].actionCount").value(3))
            .andExpect(jsonPath("$.data.rateByMemberHabitList[0].ratio").value(50))
            .andExpect(jsonPath("$.data.rateByMemberHabitList[1].name").value(memberHabit1.getAlias()))
            .andExpect(jsonPath("$.data.rateByMemberHabitList[1].missCount").value(1))
            .andExpect(jsonPath("$.data.rateByMemberHabitList[1].actionCount").value(3))
            .andExpect(jsonPath("$.data.rateByMemberHabitList[1].ratio").value(75))
            .andExpect(jsonPath("$.data.rateByHabitList[0].name").value(habit2.getName()))
            .andExpect(jsonPath("$.data.rateByHabitList[0].ratio").value(60))
            .andExpect(jsonPath("$.data.rateByHabitList[1].name").value(habit1.getName()))
            .andExpect(jsonPath("$.data.rateByHabitList[1].ratio").value(40))
            .andExpect(jsonPath("$.data.rateByHabitList[2].name").value("기타"))
            .andExpect(jsonPath("$.data.rateByHabitList[2].ratio").value(0));
    }

}