package life.bareun.diary.global.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import life.bareun.diary.global.auth.principal.OAuth2MemberPrincipal;
import life.bareun.diary.global.auth.token.AuthTokenProvider;
import life.bareun.diary.global.auth.util.ResponseUtil;
import life.bareun.diary.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

// SimpleUrlAuthenticationSuccessHandler는 성공 후 특정 URL로 redirect하기 위해 쓰인다.
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final AuthTokenProvider authTokenProvider;
    private final RedirectStrategy redirectStrategy;

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
        int statusCode = oAuth2MemberPrincipal.isNewMember()
            ? HttpStatus.CREATED.value()
            : HttpStatus.OK.value();
        // AuthLoginResDto authLoginRes = AuthLoginResDto.builder()
        //     .accessToken(accessToken)
        //     .refreshToken(refreshToken)
        //     .build();

        // response.setHeader(SecurityConfig.ACCESS_TOKEN_HEADER, accessToken);
        // response.setHeader(SecurityConfig.REFRESH_TOKEN_HEADER, refreshToken);

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
        ResponseUtil.addRefreshTokenCookie(response, refreshToken, refreshTokenMaxAge);
        // ResponseUtil.writeSuccess(
        //     response,
        //     BaseResponse.success(
        //         statusCode,
        //         "OAuth2 인증되었습니다.",
        //         null // authLoginRes
        //     )
        // );

        // response.sendRedirect("http://localhost:3000/auth?status=" + statusCode);

        redirectStrategy.sendRedirect(
            request,
            response,
            "http://localhost:3000/auth?status=" + statusCode
        );
    }
}

