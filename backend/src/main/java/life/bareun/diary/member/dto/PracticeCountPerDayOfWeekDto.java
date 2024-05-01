package life.bareun.diary.member.dto;

public record PracticeCountPerDayOfWeekDto(
    String dayOfWeek,
    int value,
    int colorIdx
) {

}
