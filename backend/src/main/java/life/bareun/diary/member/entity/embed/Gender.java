package life.bareun.diary.member.entity.embed;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Gender {
    M("M"),
    F("F"),
    N("N");

    private final String value;
}