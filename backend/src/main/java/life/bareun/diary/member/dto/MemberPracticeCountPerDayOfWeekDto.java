package life.bareun.diary.member.dto;

public record MemberPracticeCountPerDayOfWeekDto(
    String dayOfWeek,
    int value,
    int colorIdx
) {

}
