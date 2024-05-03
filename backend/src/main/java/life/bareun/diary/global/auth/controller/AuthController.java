package life.bareun.diary.global.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<BaseResponse<Void>> accessToken(
        @RequestHeader(SecurityConfig.REFRESH_TOKEN_HEADER)
        String refreshToken,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        AuthAccessTokenResDto authAccessTokenResDto = authService.issueAccessToken(refreshToken);

        ResponseUtil.addAccessTokenCookie(
            response,
            authAccessTokenResDto.accessToken(),
            authAccessTokenResDto.expiry()
        );
        return ResponseEntity.ok(
            BaseResponse.success(
                HttpStatus.OK.value(),
                "액세스 토큰이 발급되었습니다.",
                null
            )
        );
    }
}