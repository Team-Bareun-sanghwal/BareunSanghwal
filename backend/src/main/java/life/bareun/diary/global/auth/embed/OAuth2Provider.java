package life.bareun.diary.global.auth.embed;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OAuth2Provider {
    GOOGLE("GOOGLE"),
    KAKAO("KAKAO");

    private final String value;
}
