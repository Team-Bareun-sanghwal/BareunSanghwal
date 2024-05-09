package life.bareun.diary.habit.repository;

import java.util.List;
import life.bareun.diary.habit.dto.HabitTrackerCountDto;
import life.bareun.diary.habit.dto.HabitTrackerDeleteDto;
import life.bareun.diary.habit.dto.HabitTrackerLastDto;
import life.bareun.diary.habit.dto.HabitTrackerModifyDto;
import life.bareun.diary.habit.dto.HabitTrackerScheduleDto;
import life.bareun.diary.habit.dto.HabitTrackerTodayDto;
import life.bareun.diary.habit.dto.HabitTrackerTodayFactorDto;
import life.bareun.diary.habit.dto.response.HabitPracticeCountPerDayOfWeekDto;
import life.bareun.diary.habit.entity.HabitTracker;
import life.bareun.diary.member.dto.MemberHabitTrackerDto;
import life.bareun.diary.member.dto.MemberPracticeCountPerHourDto;
import life.bareun.diary.member.dto.MemberTopHabitDto;

public interface HabitTrackerRepositoryCustom {

    void deleteAfterHabitTracker(HabitTrackerDeleteDto habitDeleteReqDto);

    void modifyHabitTracker(HabitTrackerModifyDto habitTrackerModifyDto);

    List<HabitTrackerTodayDto> findAllTodayHabitTracker(HabitTrackerTodayFactorDto habitTrackerTodayFactorDto);

    HabitTracker findLastHabitTracker(HabitTrackerLastDto habitTrackerLastDto);
    
    Long getHabitTrackerCountByMemberAndDate(HabitTrackerCountDto habitTrackerCountDto);

    Long getHabitTrackerCountByMemberHabitAndDate(HabitTrackerScheduleDto habitTrackerScheduleDto);

    List<MemberTopHabitDto> findTopHabits(Long memberId);

    Long countByMemberId(Long memberId);

    List<HabitPracticeCountPerDayOfWeekDto> countPracticedHabitsPerDayOfWeek(Long memberId);

    List<MemberPracticeCountPerHourDto> countPracticedHabitsPerHour(Long memberId);

    List<Integer> findAllCreatedYearByMemberHabitId(Long memberId, Long memberHabitId);

    MemberHabitTrackerDto findAllHabitTrackerByCreatedYearAndMemberHabitOrderByCreatedDate(Integer year, Long memberId, Long memberHabitId);
    
}
