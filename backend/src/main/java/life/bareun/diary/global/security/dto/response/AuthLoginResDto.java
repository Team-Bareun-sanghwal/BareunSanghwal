package life.bareun.diary.global.security.dto.response;


import lombok.Builder;

@Builder
public record AuthLoginResDto(
    String accessToken,
    String refreshToken
) {

}