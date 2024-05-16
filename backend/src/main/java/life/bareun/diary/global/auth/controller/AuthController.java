package life.bareun.diary.global.auth.controller;

import life.bareun.diary.global.auth.config.SecurityConfig;
import life.bareun.diary.global.auth.dto.response.AuthAccessTokenResDto;
import life.bareun.diary.global.auth.service.AuthService;
import life.bareun.diary.global.auth.util.ResponseUtil;
import life.bareun.diary.global.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @GetMapping("/access-token")
    public ResponseEntity<BaseResponse<String>> accessToken(
        @RequestHeader(SecurityConfig.REFRESH_TOKEN_HEADER)
        String refreshToken
        // HttpServletResponse response
    ) {
        AuthAccessTokenResDto authAccessTokenResDto = authService.issueAccessToken(refreshToken);

        System.out.println("Try to add Access token");
        // ResponseUtil.addAccessTokenCookie(
        //     response,
        //     authAccessTokenResDto.accessToken(),
        //     authAccessTokenResDto.expiry()
        // );
        //
        // Cookie cookie = new Cookie(
        //     SecurityConfig.ACCESS_TOKEN_HEADER,
        //     authAccessTokenResDto.accessToken()
        // );
        // cookie.setHttpOnly(true);
        // cookie.setPath("/");
        // cookie.setSecure(true);
        // cookie.setMaxAge((int) authAccessTokenResDto.expiry());
        // cookie.setDomain("bareun.life");
        // response.addCookie(cookie);
        //
        // System.out.println("Set-Cookie: " +  response.getHeader("Set-Cookie"));

        return ResponseEntity
            .status(
                HttpStatus.OK.value()
            )
            // .header(
            //     "Set-Cookie",
            //     ResponseUtil.createResponseCookieString(
            //         SecurityConfig.ACCESS_TOKEN_HEADER,
            //         authAccessTokenResDto.accessToken(),
            //         authAccessTokenResDto.expiry()
            //     )
            // )
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "액세스 토큰이 발급되었습니다.",
                    authAccessTokenResDto.accessToken()
                )
            );
    }
}
