package life.bareun.diary.global.security.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.util.Date;
import life.bareun.diary.global.security.dto.response.AuthAccessTokenResDto;
import life.bareun.diary.global.security.exception.CustomSecurityException;
import life.bareun.diary.global.security.exception.SecurityErrorCode;
import life.bareun.diary.global.security.token.AuthToken;
import life.bareun.diary.global.security.token.AuthTokenProvider;
import life.bareun.diary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberRepository;
    private final AuthTokenService authTokenService;

    @Override
    public AuthAccessTokenResDto issueAccessToken(String refreshToken) {
        if(refreshToken == null) {
            throw new CustomSecurityException(SecurityErrorCode.UNAUTHENTICATED);
        }

        AuthToken refreshAuthToken = authTokenProvider.tokenToAuthToken(refreshToken);
        try {
            authTokenProvider.validate(refreshAuthToken);
        } catch (ExpiredJwtException e) {
            throw new CustomSecurityException(SecurityErrorCode.EXPIRED_REFRESH_TOKEN);
        } catch (JwtException e) {
            throw new CustomSecurityException(SecurityErrorCode.INVALID_AUTHENTICATION);
        }

        if(authTokenService.isRevoked(refreshToken)) {
            throw new CustomSecurityException(SecurityErrorCode.REVOKED_REFRESH_TOKEN);
        }

        Long memberId = authTokenProvider.getMemberIdFromToken(refreshAuthToken);
        String role = memberRepository.findById(memberId)
            .orElseThrow(
                () -> new CustomSecurityException(SecurityErrorCode.INVALID_AUTHENTICATION)
            )
            .getRole()
            .name();

        return new AuthAccessTokenResDto(
            authTokenProvider.createAccessToken(
                new Date(),
                Long.toString(memberId),
                role
            )
        );
    }
}
