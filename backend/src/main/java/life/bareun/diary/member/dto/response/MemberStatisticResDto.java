package life.bareun.diary.member.dto.response;

import java.util.List;
import life.bareun.diary.member.dto.MemberPracticeCountPerDayOfWeekDto;
import life.bareun.diary.member.dto.MemberPracticedHabitDto;

public record MemberStatisticResDto(
    List<MemberPracticedHabitDto> practicedHabitsTop,
    String maxPracticedHabit,
    List<MemberPracticeCountPerDayOfWeekDto> practiceCountsPerDayOfWeek,
    int totalDays,
    int streakDays,
    int starredDays,
    int longestStreak
) {

}
