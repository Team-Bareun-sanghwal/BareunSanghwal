package life.bareun.diary.global.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.global.security.principal.OAuth2MemberPrincipal;
import life.bareun.diary.global.security.token.AuthTokenProvider;
import life.bareun.diary.global.security.util.ResponseUtil;
import life.bareun.diary.member.dto.response.MemberLoginRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

// SimpleUrlAuthenticationSuccessHandler는 성공 후 특정 URL로 redirect하기 위해 쓰인다.
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final AuthTokenProvider authTokenProvider;

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException {
        OAuth2MemberPrincipal oAuth2MemberPrincipal = (OAuth2MemberPrincipal) authentication.getPrincipal();
        String accessToken = authTokenProvider.createToken(oAuth2MemberPrincipal);
        System.out.println("accessToken: " + accessToken);

        // 응답
        int statusCode = oAuth2MemberPrincipal.isNewMember() ? HttpStatus.CREATED.value()
            : HttpStatus.OK.value();

        ResponseUtil.respondSuccess(
            response,
            BaseResponse.success(
                statusCode,
                "OAuth2 인증되었습니다.",
                new MemberLoginRes(accessToken)
            )
        );
    }
}

