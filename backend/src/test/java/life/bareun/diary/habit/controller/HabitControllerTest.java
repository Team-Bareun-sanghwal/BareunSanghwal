package life.bareun.diary.habit.controller;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import life.bareun.diary.global.auth.embed.OAuth2Provider;
import life.bareun.diary.global.auth.token.AuthToken;
import life.bareun.diary.global.auth.token.AuthTokenProvider;
import life.bareun.diary.habit.dto.request.HabitCreateReqDto;
import life.bareun.diary.habit.dto.request.HabitDeleteReqDto;
import life.bareun.diary.habit.dto.request.HabitTrackerModifyReqDto;
import life.bareun.diary.habit.entity.Habit;
import life.bareun.diary.habit.entity.HabitTracker;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.habit.entity.embed.MaintainWay;
import life.bareun.diary.habit.repository.HabitRepository;
import life.bareun.diary.habit.repository.HabitTrackerRepository;
import life.bareun.diary.habit.repository.MemberHabitRepository;
import life.bareun.diary.member.dto.MemberHabitListElementDto;
import life.bareun.diary.member.dto.MemberRegisterDto;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.entity.embed.Role;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.member.repository.TreeRepository;
import life.bareun.diary.streak.entity.HabitDailyStreak;
import life.bareun.diary.streak.entity.MemberTotalStreak;
import life.bareun.diary.streak.entity.embed.AchieveType;
import life.bareun.diary.streak.repository.HabitDailyStreakRepository;
import life.bareun.diary.streak.repository.MemberTotalStreakRepository;
import life.bareun.diary.streak.service.MemberStreakService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class HabitControllerTest {

    @Autowired
    private MemberStreakService memberStreakService;

    @Autowired
    private HabitDailyStreakRepository habitDailyStreakRepository;

    @Autowired
    private MemberTotalStreakRepository memberTotalStreakRepository;

    @Autowired
    private MemberHabitRepository memberHabitRepository;

    @Autowired
    private HabitTrackerRepository habitTrackerRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private TreeRepository treeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    AuthTokenProvider authTokenProvider;

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
    @DisplayName("사용자 해빗 생성")
    void createMemberHabit() throws Exception {
        HabitCreateReqDto habitCreateReqDto = HabitCreateReqDto.builder().habitId(1L).alias("임시")
            .dayOfWeek(
                Arrays.asList(1, 2)).icon("tempIcon").period(null).build();

        String habitBody = objectMapper.writeValueAsString(habitCreateReqDto);

        mockMvc.perform(post("/habits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(habitBody)
                .header("Authorization", accessToken))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(201))
            .andExpect(jsonPath("$.message").value("해빗 생성이 완료되었습니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("사용자 해빗 삭제")
    void deleteMemberHabit() throws Exception {
        HabitCreateReqDto habitCreateReqDto = HabitCreateReqDto.builder().habitId(1L).alias("임시")
            .dayOfWeek(
                Arrays.asList(1, 2)).icon("tempIcon").period(null).build();

        String habitBody = objectMapper.writeValueAsString(habitCreateReqDto);

        mockMvc.perform(post("/habits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(habitBody)
                .header("Authorization", accessToken))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(201))
            .andExpect(jsonPath("$.message").value("해빗 생성이 완료되었습니다."))
            .andExpect(jsonPath("$.data").isEmpty());

        List<MemberHabitListElementDto> memberHabitList = memberHabitRepository.findAllByMemberIdOrderByCreatedDatetime(
            member.getId());

        // 해빗 삭제(유지)
        HabitDeleteReqDto habitDeleteReqDto = HabitDeleteReqDto.builder()
            .memberHabitId(memberHabitList.get(0).memberHabitId()).isDeleteAll(false).build();

        habitBody = objectMapper.writeValueAsString(habitDeleteReqDto);

        mockMvc.perform(post("/habits/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(habitBody).header("Authorization", accessToken))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("해빗 삭제가 완료되었습니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("해빗 트래커 완료")
    void modifyHabitTracker() throws Exception {
        HabitCreateReqDto habitCreateReqDto = HabitCreateReqDto.builder().habitId(1L).alias("임시")
            .dayOfWeek(null).icon("tempIcon").period(1).build();

        String habitBody = objectMapper.writeValueAsString(habitCreateReqDto);

        mockMvc.perform(post("/habits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(habitBody)
                .header("Authorization", accessToken))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(201))
            .andExpect(jsonPath("$.message").value("해빗 생성이 완료되었습니다."))
            .andExpect(jsonPath("$.data").isEmpty());

        List<MemberHabit> memberHabitList = memberHabitRepository.findAllByIsDeletedAndMember(false,
            member);

        LocalDate now = LocalDate.now();
        HabitTracker habitTracker = habitTrackerRepository.save(
            HabitTracker.builder().member(member).memberHabit(memberHabitList.get(0))
                .createdYear(now.getYear()).createdMonth(now.getMonthValue())
                .createdDay(now.getDayOfMonth()).day(now.getDayOfWeek().getValue()).build());

        HabitDailyStreak habitDailyStreak = habitDailyStreakRepository.findByMemberHabitAndCreatedDate(
            memberHabitList.get(0), LocalDate.now()).orElseThrow();
        habitDailyStreak.modifyAchieveType(AchieveType.NOT_ACHIEVE);
        habitDailyStreakRepository.save(habitDailyStreak);

        memberStreakService.createInitialMemberStreak(member);

        MemberTotalStreak memberTotalStreak = memberTotalStreakRepository.findByMember(member)
            .orElseThrow();
        memberTotalStreak.modifyTotalTrackerCount(1);
        memberTotalStreakRepository.save(memberTotalStreak);

        HabitTrackerModifyReqDto habitTrackerModifyReqDto = HabitTrackerModifyReqDto.builder()
            .habitTrackerId(habitTracker.getId()).content("트래커 완료 !").build();

        String modifyJson = objectMapper.writeValueAsString(habitTrackerModifyReqDto);

        File file = ResourceUtils.getFile("classpath:static/basic.png");
        byte[] fileContent = Files.readAllBytes(file.toPath());

        MockMultipartFile imageFile = new MockMultipartFile(
            "image", "basic.png", "image/png", fileContent);

        MockMultipartFile dtoPart = new MockMultipartFile(
            "HabitTrackerModifyReqDto", "", "application/json", modifyJson.getBytes());

        mockMvc.perform(multipart("/habits/completion").file(imageFile)
                .file(dtoPart)
                .header("Authorization", accessToken).with(request -> {
                    request.setMethod("PATCH");
                    return request;
                }))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("해빗 트래커를 완료하였습니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("오늘 해빗 트래커 리스트 조회")
    void findAllTodayHabitTracker() throws Exception {
        HabitCreateReqDto habitCreateReqDto = HabitCreateReqDto.builder().habitId(1L).alias("임시")
            .dayOfWeek(null).icon("tempIcon").period(1).build();

        String habitBody = objectMapper.writeValueAsString(habitCreateReqDto);

        mockMvc.perform(post("/habits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(habitBody)
                .header("Authorization", accessToken))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(201))
            .andExpect(jsonPath("$.message").value("해빗 생성이 완료되었습니다."))
            .andExpect(jsonPath("$.data").isEmpty());

        List<MemberHabit> memberHabitList = memberHabitRepository.findAllByIsDeletedAndMember(false,
            member);

        LocalDate now = LocalDate.now();
        HabitTracker habitTracker = habitTrackerRepository.save(
            HabitTracker.builder().member(member).memberHabit(memberHabitList.get(0))
                .createdYear(now.getYear()).createdMonth(now.getMonthValue())
                .createdDay(now.getDayOfMonth()).day(now.getDayOfWeek().getValue()).build());

        mockMvc.perform(get("/habits/today")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("오늘의 해빗 트래커 리스트 조회를 성공하였습니다."))
            .andExpect(jsonPath("$.data.habitTrackerTodayDtoList", hasSize(1)))
            .andExpect(jsonPath("$.data.habitTrackerTodayDtoList[0].name").value("걷기"))
            .andExpect(jsonPath("$.data.habitTrackerTodayDtoList[0].alias").value("임시"))
            .andExpect(jsonPath("$.data.habitTrackerTodayDtoList[0].memberHabitId").value(
                memberHabitList.get(0).getId()))
            .andExpect(jsonPath("$.data.habitTrackerTodayDtoList[0].habitTrackerId").value(
                habitTracker.getId()))
            .andExpect(jsonPath("$.data.habitTrackerTodayDtoList[0].icon").value("tempIcon"))
            .andExpect(jsonPath("$.data.habitTrackerTodayDtoList[0].day").value(
                now.getDayOfWeek().getValue()))
            .andExpect(jsonPath("$.data.habitTrackerTodayDtoList[0].succeededTime").isEmpty());
    }

    @Test
    @DisplayName("해빗 트래커 상세 조회")
    void findDetailHabitTracker() throws Exception {
        HabitCreateReqDto habitCreateReqDto = HabitCreateReqDto.builder().habitId(1L).alias("임시")
            .dayOfWeek(null).icon("tempIcon").period(1).build();

        String habitBody = objectMapper.writeValueAsString(habitCreateReqDto);

        mockMvc.perform(post("/habits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(habitBody)
                .header("Authorization", accessToken))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(201))
            .andExpect(jsonPath("$.message").value("해빗 생성이 완료되었습니다."))
            .andExpect(jsonPath("$.data").isEmpty());

        List<MemberHabit> memberHabitList = memberHabitRepository.findAllByIsDeletedAndMember(false,
            member);

        LocalDate now = LocalDate.now();
        HabitTracker habitTracker = habitTrackerRepository.save(
            HabitTracker.builder().member(member).memberHabit(memberHabitList.get(0))
                .createdYear(now.getYear()).createdMonth(now.getMonthValue())
                .createdDay(now.getDayOfMonth()).day(now.getDayOfWeek().getValue()).build());

        mockMvc.perform(get("/habits/" + habitTracker.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("오늘의 해빗 트래커 상세 조회를 성공하였습니다."))
            .andExpect(jsonPath("$.data.habitTrackerId").value(habitTracker.getId()))
            .andExpect(jsonPath("$.data.alias").value("임시"))
            .andExpect(jsonPath("$.data.day").value(now.getDayOfWeek().getValue()))
            .andExpect(jsonPath("$.data.createdAt").value(LocalDate.now().toString()))
            .andExpect(jsonPath("$.data.succeededTime").isEmpty());
    }

    @Test
    @DisplayName("요일 별 해빗 트래커 개수 리스트 조회")
    void findAllWeekHabitTracker() throws Exception {
        mockMvc.perform(get("/habits/day")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("요일 별 해빗 트래커 개수 조회를 성공하였습니다."))
            .andExpect(jsonPath("$.data.monday", greaterThanOrEqualTo(0)))
            .andExpect(jsonPath("$.data.tuesday", greaterThanOrEqualTo(0)))
            .andExpect(jsonPath("$.data.wednesday", greaterThanOrEqualTo(0)))
            .andExpect(jsonPath("$.data.thursday", greaterThanOrEqualTo(0)))
            .andExpect(jsonPath("$.data.friday", greaterThanOrEqualTo(0)))
            .andExpect(jsonPath("$.data.saturday", greaterThanOrEqualTo(0)))
            .andExpect(jsonPath("$.data.sunday", greaterThanOrEqualTo(0)));
    }

    @Test
    @DisplayName("이번 달에 한 번이라도 유지한 적이 있는 사용자 해빗 리스트 조회")
    void findAllMonthMemberHabit() throws Exception {
        HabitCreateReqDto habitCreateReqDto = HabitCreateReqDto.builder().habitId(1L).alias("임시")
            .dayOfWeek(null).icon("tempIcon").period(1).build();

        String habitBody = objectMapper.writeValueAsString(habitCreateReqDto);

        mockMvc.perform(post("/habits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(habitBody)
                .header("Authorization", accessToken))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(201))
            .andExpect(jsonPath("$.message").value("해빗 생성이 완료되었습니다."))
            .andExpect(jsonPath("$.data").isEmpty());

        List<MemberHabit> memberHabitList = memberHabitRepository.findAllByIsDeletedAndMember(false,
            member);

        LocalDate now = LocalDate.now();
        HabitTracker habitTracker = habitTrackerRepository.save(
            HabitTracker.builder().member(member).memberHabit(memberHabitList.get(0))
                .createdYear(now.getYear()).createdMonth(now.getMonthValue())
                .createdDay(now.getDayOfMonth()).day(now.getDayOfWeek().getValue()).build());

        HabitDailyStreak habitDailyStreak = habitDailyStreakRepository.findByMemberHabitAndCreatedDate(
            memberHabitList.get(0), LocalDate.now()).orElseThrow();
        habitDailyStreak.modifyAchieveType(AchieveType.NOT_ACHIEVE);
        habitDailyStreakRepository.save(habitDailyStreak);

        memberStreakService.createInitialMemberStreak(member);

        MemberTotalStreak memberTotalStreak = memberTotalStreakRepository.findByMember(member)
            .orElseThrow();
        memberTotalStreak.modifyTotalTrackerCount(1);
        memberTotalStreakRepository.save(memberTotalStreak);

        HabitTrackerModifyReqDto habitTrackerModifyReqDto = HabitTrackerModifyReqDto.builder()
            .habitTrackerId(habitTracker.getId()).content("트래커 완료 !").build();

        String modifyJson = objectMapper.writeValueAsString(habitTrackerModifyReqDto);

        File file = ResourceUtils.getFile("classpath:static/basic.png");
        byte[] fileContent = Files.readAllBytes(file.toPath());

        MockMultipartFile imageFile = new MockMultipartFile(
            "image", "basic.png", "image/png", fileContent);

        MockMultipartFile dtoPart = new MockMultipartFile(
            "HabitTrackerModifyReqDto", "", "application/json", modifyJson.getBytes());

        mockMvc.perform(multipart("/habits/completion").file(imageFile)
                .file(dtoPart)
                .header("Authorization", accessToken).with(request -> {
                    request.setMethod("PATCH");
                    return request;
                }))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("해빗 트래커를 완료하였습니다."))
            .andExpect(jsonPath("$.data").isEmpty());

        String nowMonth = now.getYear() + "-" + now.getMonthValue();
        mockMvc.perform(get("/habits/month/" + nowMonth)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("이번 달 사용자 해빗 리스트 조회를 성공하였습니다."))
            .andExpect(jsonPath("$.data.memberHabitDtoList", hasSize(1)))
            .andExpect(jsonPath("$.data.memberHabitDtoList[0].alias").value("임시"))
            .andExpect(jsonPath("$.data.memberHabitDtoList[0].memberHabitId").value(
                memberHabitList.get(0).getId()))
            .andExpect(jsonPath("$.data.memberHabitDtoList[0].icon").value("tempIcon"));
    }

    @Test
    @DisplayName("모든 활성화된 사용자 해빗 리스트 조회")
    void findAllActiveMemberHabit() throws Exception {
        HabitCreateReqDto habitCreateReqDto = HabitCreateReqDto.builder().habitId(1L).alias("임시")
            .dayOfWeek(null).icon("tempIcon").period(1).build();

        String habitBody = objectMapper.writeValueAsString(habitCreateReqDto);

        mockMvc.perform(post("/habits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(habitBody)
                .header("Authorization", accessToken))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(201))
            .andExpect(jsonPath("$.message").value("해빗 생성이 완료되었습니다."))
            .andExpect(jsonPath("$.data").isEmpty());

        List<MemberHabit> memberHabitList = memberHabitRepository.findAllByIsDeletedAndMember(false,
            member);

        mockMvc.perform(get("/habits/active")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("활성화된 사용자 해빗 리스트 조회를 성공하였습니다."))
            .andExpect(jsonPath("$.data.memberHabitList", hasSize(1)))
            .andExpect(jsonPath("$.data.memberHabitList[0].name").value("걷기"))
            .andExpect(jsonPath("$.data.memberHabitList[0].alias").value("임시"))
            .andExpect(jsonPath("$.data.memberHabitList[0].memberHabitId").value(
                memberHabitList.get(0).getId()))
            .andExpect(jsonPath("$.data.memberHabitList[0].icon").value("tempIcon"))
            .andExpect(jsonPath("$.data.memberHabitList[0].createdAt").isNotEmpty())
            .andExpect(jsonPath("$.data.memberHabitList[0].habitTrackerId").value(0))
            .andExpect(jsonPath("$.data.memberHabitList[0].currentStreak").value(0))
            .andExpect(jsonPath("$.data.memberHabitList[0].isSucceeded").value(false))
            .andExpect(jsonPath("$.data.memberHabitList[0].dayList").isEmpty());
    }

    @Test
    @DisplayName("모든 활성화된 사용자 해빗 간단 리스트 조회")
    void findAllActiveSimpleMemberHabit() throws Exception {
        HabitCreateReqDto habitCreateReqDto = HabitCreateReqDto.builder().habitId(1L).alias("임시")
            .dayOfWeek(null).icon("tempIcon").period(1).build();

        String habitBody = objectMapper.writeValueAsString(habitCreateReqDto);

        mockMvc.perform(post("/habits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(habitBody)
                .header("Authorization", accessToken))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(201))
            .andExpect(jsonPath("$.message").value("해빗 생성이 완료되었습니다."))
            .andExpect(jsonPath("$.data").isEmpty());

        List<MemberHabit> memberHabitList = memberHabitRepository.findAllByIsDeletedAndMember(false,
            member);

        mockMvc.perform(get("/habits/active-day")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("활성화된 사용자 해빗 간단 리스트 조회를 성공하였습니다."))
            .andExpect(jsonPath("$.data.memberHabitList", hasSize(1)))
            .andExpect(jsonPath("$.data.memberHabitList[0].name").value("걷기"))
            .andExpect(jsonPath("$.data.memberHabitList[0].alias").value("임시"))
            .andExpect(jsonPath("$.data.memberHabitList[0].memberHabitId").value(
                memberHabitList.get(0).getId()))
            .andExpect(jsonPath("$.data.memberHabitList[0].dayList").isEmpty());
    }

    @Test
    @DisplayName("모든 비활성화된 사용자 해빗 리스트 조회")
    void findAllNonActiveMemberHabit() throws Exception {
        Habit habit = habitRepository.findById(1L).orElseThrow();
        MemberHabit memberHabit = memberHabitRepository.save(
            MemberHabit.builder().habit(habit).member(member).isDeleted(true)
                .succeededDatetime(LocalDateTime.now()).maintainWay(
                    MaintainWay.PERIOD).maintainAmount(1).icon("tempIcon").alias("임시").build());

        mockMvc.perform(get("/habits/non-active")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("비활성화된 사용자 해빗 리스트 조회를 성공하였습니다."))
            .andExpect(jsonPath("$.data.memberHabitList", hasSize(1)))
            .andExpect(jsonPath("$.data.memberHabitList[0].name").value("걷기"))
            .andExpect(jsonPath("$.data.memberHabitList[0].alias").value("임시"))
            .andExpect(jsonPath("$.data.memberHabitList[0].memberHabitId").value(
                memberHabit.getId()))
            .andExpect(jsonPath("$.data.memberHabitList[0].icon").value("tempIcon"))
            .andExpect(jsonPath("$.data.memberHabitList[0].createdAt").isNotEmpty())
            .andExpect(jsonPath("$.data.memberHabitList[0].succeededTime").isNotEmpty());
    }

    @Test
    @DisplayName("해빗 검색어에 따른 검색")
    void findAllMatchHabit() throws Exception {
        mockMvc.perform(get("/habits/search")
                .contentType(MediaType.APPLICATION_JSON)
                .param("habitName", "요가")
                .header("Authorization", accessToken))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("검색된 해빗 리스트 조회를 성공하였습니다."))
            .andExpect(jsonPath("$.data.habitList", hasSize(1)))
            .andExpect(jsonPath("$.data.habitList[0].habitName").value("요가"))
            .andExpect(jsonPath("$.data.habitList[0].habitId").value(4));
    }

    @Test
    @DisplayName("해빗 랭킹 리스트 조회")
    void findAllHabitRank() throws Exception {
        mockMvc.perform(get("/habits/rank")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("해빗 랭킹 리스트 조회를 성공하였습니다."))
            .andExpect(jsonPath("$.data.habitList", hasSize(10)))
            .andExpect(jsonPath("$.data.habitList[0].name").isNotEmpty())
            .andExpect(jsonPath("$.data.habitList[0].habitId", greaterThanOrEqualTo(0)))
            .andExpect(jsonPath("$.data.habitList[1].name").isNotEmpty())
            .andExpect(jsonPath("$.data.habitList[1].habitId", greaterThanOrEqualTo(0)))
            .andExpect(jsonPath("$.data.habitList[2].name").isNotEmpty())
            .andExpect(jsonPath("$.data.habitList[2].habitId", greaterThanOrEqualTo(0)))
            .andExpect(jsonPath("$.data.habitList[3].name").isNotEmpty())
            .andExpect(jsonPath("$.data.habitList[3].habitId", greaterThanOrEqualTo(0)))
            .andExpect(jsonPath("$.data.habitList[4].name").isNotEmpty())
            .andExpect(jsonPath("$.data.habitList[4].habitId", greaterThanOrEqualTo(0)))
            .andExpect(jsonPath("$.data.habitList[5].name").isNotEmpty())
            .andExpect(jsonPath("$.data.habitList[5].habitId", greaterThanOrEqualTo(0)))
            .andExpect(jsonPath("$.data.habitList[6].name").isNotEmpty())
            .andExpect(jsonPath("$.data.habitList[6].habitId", greaterThanOrEqualTo(0)))
            .andExpect(jsonPath("$.data.habitList[7].name").isNotEmpty())
            .andExpect(jsonPath("$.data.habitList[7].habitId", greaterThanOrEqualTo(0)))
            .andExpect(jsonPath("$.data.habitList[8].name").isNotEmpty())
            .andExpect(jsonPath("$.data.habitList[8].habitId", greaterThanOrEqualTo(0)))
            .andExpect(jsonPath("$.data.habitList[9].name").isNotEmpty())
            .andExpect(jsonPath("$.data.habitList[9].habitId", greaterThanOrEqualTo(0)));
    }
}