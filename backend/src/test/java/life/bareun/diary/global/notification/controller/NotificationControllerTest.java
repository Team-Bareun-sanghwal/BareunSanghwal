package life.bareun.diary.global.notification.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Date;
import life.bareun.diary.global.auth.embed.OAuth2Provider;
import life.bareun.diary.global.auth.token.AuthToken;
import life.bareun.diary.global.auth.token.AuthTokenProvider;
import life.bareun.diary.global.notification.dto.request.NotificationReqDto;
import life.bareun.diary.global.notification.entity.Notification;
import life.bareun.diary.global.notification.entity.NotificationCategory;
import life.bareun.diary.global.notification.repository.NotificationCategoryRepository;
import life.bareun.diary.global.notification.repository.NotificationRepository;
import life.bareun.diary.member.dto.MemberRegisterDto;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.entity.embed.Role;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.member.repository.TreeRepository;
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
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthTokenProvider authTokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TreeRepository treeRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationCategoryRepository notificationCategoryRepository;

    @Autowired
    EntityManager entityManager;

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
    @DisplayName("알림 토큰 생성")
    void createNotificationToken() throws Exception {
        NotificationReqDto notificationReqDto = NotificationReqDto.builder()
            .notificationToken("notificationToken").build();

        String notificationBody = objectMapper.writeValueAsString(notificationReqDto);

        mockMvc.perform(post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(notificationBody)
                .header("Authorization", accessToken))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(201))
            .andExpect(jsonPath("$.message").value("알림 토큰 생성이 완료되었습니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("알림 목록 조회")
    void findAllNotification() throws Exception {
        NotificationCategory notificationCategory = notificationCategoryRepository.findById(1L)
            .orElseThrow();
        notificationRepository.save(
            Notification.builder().notificationCategory(notificationCategory).content("안녕하세요.")
                .member(member).isRead(false).build());

        mockMvc.perform(get("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("알림 목록 조회가 완료되었습니다."))
            .andExpect(jsonPath("$.data.notificationList", hasSize(1)))
            .andExpect(jsonPath("$.data.notificationList[0].notificationId").isNotEmpty())
            .andExpect(jsonPath("$.data.notificationList[0].icon").isString())
            .andExpect(jsonPath("$.data.notificationList[0].content").value("안녕하세요."))
            .andExpect(jsonPath("$.data.notificationList[0].isRead").value(false))
            .andExpect(jsonPath("$.data.notificationList[0].createdAt").isNotEmpty());
    }

    @Test
    @DisplayName("알림 상태 변경")
    void modifyNotificationStatus() throws Exception {
        NotificationCategory notificationCategory = notificationCategoryRepository.findById(1L)
            .orElseThrow();
        notificationRepository.save(
            Notification.builder().notificationCategory(notificationCategory).content("안녕하세요.")
                .member(member).isRead(false).build());

        mockMvc.perform(get("/notifications/read")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("알림 상태 변경이 완료되었습니다."))
            .andExpect(jsonPath("$.data").isEmpty());

        entityManager.clear();

        mockMvc.perform(get("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", accessToken))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(200))
            .andExpect(jsonPath("$.message").value("알림 목록 조회가 완료되었습니다."))
            .andExpect(jsonPath("$.data.notificationList", hasSize(1)))
            .andExpect(jsonPath("$.data.notificationList[0].notificationId").isNotEmpty())
            .andExpect(jsonPath("$.data.notificationList[0].icon").isString())
            .andExpect(jsonPath("$.data.notificationList[0].content").value("안녕하세요."))
            .andExpect(jsonPath("$.data.notificationList[0].isRead").value(true))
            .andExpect(jsonPath("$.data.notificationList[0].createdAt").isNotEmpty());

    }
}