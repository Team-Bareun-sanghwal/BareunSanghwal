package life.bareun.diary.global.auth.embed;


import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public enum OAuth2Provider {
    GOOGLE("GOOGLE"),
    KAKAO("KAKAO"),
    PROTECTED("[PROTECTED]"); // 로그인 이후에 사용됨


    private final String value;


    public static boolean validate(String name) {
        return name.equals(GOOGLE.value) || name.equals(KAKAO.value);
    }
}
