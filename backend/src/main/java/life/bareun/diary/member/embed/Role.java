package life.bareun.diary.member.embed;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String value;
}
