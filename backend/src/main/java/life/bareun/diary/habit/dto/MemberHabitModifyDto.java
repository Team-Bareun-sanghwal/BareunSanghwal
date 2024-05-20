package life.bareun.diary.habit.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record MemberHabitModifyDto(

    Long memberHabitId,

    LocalDateTime succeededTime

) { }
