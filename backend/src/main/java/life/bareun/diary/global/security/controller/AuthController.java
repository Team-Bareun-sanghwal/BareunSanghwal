package life.bareun.diary.global.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.global.security.dto.response.AuthAccessTokenRes;
import life.bareun.diary.global.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @GetMapping("/access-token")
    public ResponseEntity<BaseResponse<AuthAccessTokenRes>> accessToken(
        HttpServletRequest request
    ) {
        AuthAccessTokenRes authAccessTokenRes = authService.issueAccessToken(
            request.getHeader("Authorization-Refresh")
        );
        return ResponseEntity.ok(
            BaseResponse.success(
                HttpStatus.OK.value(),
                "액세스 토큰이 발급되었습니다.",
                authAccessTokenRes
            )
        );
    }
}
