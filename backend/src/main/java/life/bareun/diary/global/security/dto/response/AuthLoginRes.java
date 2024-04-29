package life.bareun.diary.global.security.dto.response;


import lombok.Builder;

@Builder
public record AuthLoginRes(
    String accessToken,
    String refreshToken
) {

}