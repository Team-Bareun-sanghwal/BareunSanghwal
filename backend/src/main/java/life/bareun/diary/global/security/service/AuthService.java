package life.bareun.diary.global.security.service;

import life.bareun.diary.global.security.dto.response.AuthAccessTokenResDto;

public interface AuthService {
    AuthAccessTokenResDto issueAccessToken(String refreshToken);

}
