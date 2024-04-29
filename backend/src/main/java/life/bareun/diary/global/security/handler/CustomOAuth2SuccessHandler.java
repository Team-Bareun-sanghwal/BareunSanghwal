package life.bareun.diary.global.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.global.security.dto.response.AuthLoginRes;
import life.bareun.diary.global.security.principal.OAuth2MemberPrincipal;
import life.bareun.diary.global.security.token.AuthTokenProvider;
import life.bareun.diary.global.security.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

// SimpleUrlAuthenticationSuccessHandler는 성공 후 특정 URL로 redirect하기 위해 쓰인다.
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final AuthTokenProvider authTokenProvider;

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException {
        OAuth2MemberPrincipal oAuth2MemberPrincipal = (OAuth2MemberPrincipal) authentication.getPrincipal();
        String id = oAuth2MemberPrincipal.getName();
        String role = oAuth2MemberPrincipal.getAuthority();
        String accessToken = authTokenProvider.createAccessToken(id, role);
        String refreshToken = authTokenProvider.createRefreshToken(id);
        // System.out.println("accessToken: " + accessToken);
        log.debug("accessToken: {}", accessToken);
        log.debug("refreshToken: {}", refreshToken);

        // 응답
        int statusCode = oAuth2MemberPrincipal.isNewMember() ? HttpStatus.CREATED.value()
            : HttpStatus.OK.value();
        AuthLoginRes authLoginRes = AuthLoginRes.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
        ResponseUtil.respondSuccess(
            response,
            BaseResponse.success(
                statusCode,
                "OAuth2 인증되었습니다.",
                authLoginRes
            )
        );
    }
}

