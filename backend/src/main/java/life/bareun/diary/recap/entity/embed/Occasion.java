package life.bareun.diary.recap.entity.embed;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Occasion {

    DAWN("DAWN"), MORNING("MORNING"), EVENING("EVENING"), NIGHT("NIGHT");

    private final String value;

}
