package life.bareun.diary.member.dto.response;

import java.util.List;
import life.bareun.diary.member.dto.PracticeCountPerDayOfWeekDto;
import life.bareun.diary.member.dto.PracticedHabitDto;

public record MemberStatisticResDto(
    List<PracticedHabitDto> practicedHabitsTop,
    String maxPracticedHabit,
    List<PracticeCountPerDayOfWeekDto> practiceCountsPerDayOfWeek,
    int totalDays,
    int streakDays,
    int starredDays,
    int longestStreak
) {

}
