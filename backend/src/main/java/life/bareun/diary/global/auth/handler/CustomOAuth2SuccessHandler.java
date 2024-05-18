package life.bareun.diary.global.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import life.bareun.diary.global.auth.principal.OAuth2MemberPrincipal;
import life.bareun.diary.global.auth.token.AuthTokenProvider;
import life.bareun.diary.global.auth.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        Date currentDate = new Date();
        String accessToken = authTokenProvider.createAccessToken(currentDate, id, role);
        String refreshToken = authTokenProvider.createRefreshToken(currentDate, id);

        log.debug("accessToken: {}", accessToken);
        log.debug("refreshToken: {}", refreshToken);
        System.out.println("accessToken: " + accessToken);
        System.out.println("refreshToken: " + refreshToken);

        // 응답
        int statusCode = oAuth2MemberPrincipal.getMemberStatus().getCode();

        long accessTokenMaxAge = authTokenProvider.getExpiry(
            authTokenProvider.tokenToAuthToken(accessToken)
        ).toSeconds();
        ResponseUtil.addAccessTokenCookie(
            response,
            accessToken,
            accessTokenMaxAge
        );

        long refreshTokenMaxAge = authTokenProvider.getExpiry(
            authTokenProvider.tokenToAuthToken(refreshToken)
        ).toSeconds();
        ResponseUtil.addRefreshTokenCookie(
            response,
            refreshToken,
            refreshTokenMaxAge
        );

        /* 배포용 코드 */
        response.sendRedirect("https://bareun.life/auth?status=" + statusCode);

        /* 로컬 테스트를 위한 코드 */
        // if(oAuth2MemberPrincipal.getOAuth2Provider().equals(OAuth2Provider.GOOGLE)) {
        //     response.sendRedirect("https://bareun.life/auth?status=" + statusCode);
        // } else if (oAuth2MemberPrincipal.getOAuth2Provider().equals(OAuth2Provider.KAKAO)) {
        //     String redirectUrl = "http://localhost:3000/auth"
        //         + "?status=" + statusCode
        //         + "&at=" + accessToken
        //         + "&rt=" + refreshToken;
        //     response.sendRedirect(redirectUrl);
        // }
    }
}

