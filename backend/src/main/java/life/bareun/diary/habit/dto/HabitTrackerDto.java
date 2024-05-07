package life.bareun.diary.habit.dto;

import java.time.LocalDateTime;
import lombok.Getter;


@Getter
public class HabitTrackerDto {

    private final Long habitTrackerId;
    private final LocalDateTime succeededTime;
    private final String content;
    private final String image;
    private final String period;

    public HabitTrackerDto(
        Long habitTrackerId,
        LocalDateTime succeededTime,
        String content,
        String image,
        int month,
        int day
    ) {
        this.habitTrackerId = habitTrackerId;
        this.succeededTime = succeededTime;
        this.content = content;
        this.image = image;
        this.period = String.format("%d월 %d일", month, day);
    }
}
