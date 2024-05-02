package life.bareun.diary.global.auth.dto.response;


import lombok.Builder;

@Builder
public record AuthLoginResDto(
    String accessToken,
    String refreshToken
) {

}