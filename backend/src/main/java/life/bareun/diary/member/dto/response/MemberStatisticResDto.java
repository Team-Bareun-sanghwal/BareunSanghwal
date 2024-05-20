package life.bareun.diary.member.dto.response;

import java.util.List;
import life.bareun.diary.member.dto.MemberPracticeCountPerDayOfWeekDto;
import life.bareun.diary.member.dto.MemberPracticeCountPerHourDto;
import life.bareun.diary.member.dto.MemberTopHabitDto;
import lombok.Builder;

@Builder
public record MemberStatisticResDto(
    // 가장 많이 수행한 해빗 5개
    List<MemberTopHabitDto> practicedHabitsTop,

    // 가장 많이 수행한 해빗 이름
    String maxPracticedHabit,

    // 요일 별 수행 횟수
    List<MemberPracticeCountPerDayOfWeekDto> practiceCountsPerDayOfWeek,

    // 시간대(0, 1, 2, ..., 23) 단위별 수행 횟수
    List<MemberPracticeCountPerHourDto> practiceCountsPerHour,

    // 총 서비스 사용 일 수
    int totalDays,

    // 연속 여부와 관계 없이 스트릭을 채운 일 수 
    int streakDays,

    // 모든 해빗을 수행한(별을 받은) 날의 수
    int starredDays,

    // 최장 스트릭
    int longestStreak
) {

}
