package life.bareun.diary.streak.entity.embed;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AchieveType {
    NOT_ACHIEVE("NOT_ACHIEVE"),
    ACHIEVE("ACHIEVE"),
    NOT_EXISTED("NOT_EXISTED");

    private final String type;
}
