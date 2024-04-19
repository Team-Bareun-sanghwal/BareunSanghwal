package life.bareun.diary.habit.dto;

public record HabitTrackerTodayFactorDto(

    Long memberHabitId,

    int createdYear,

    int createdMonth,

    int createdDay

) { }
