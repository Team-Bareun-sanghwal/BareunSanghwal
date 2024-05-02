package life.bareun.diary.global.auth.service;

import life.bareun.diary.global.auth.dto.response.AuthAccessTokenResDto;

public interface AuthService {

    AuthAccessTokenResDto issueAccessToken(String refreshToken);

}
