package life.bareun.diary.habit.service;

import life.bareun.diary.habit.dto.HabitTrackerCreateDto;
import life.bareun.diary.habit.dto.HabitTrackerLastDto;
import life.bareun.diary.habit.dto.request.HabitTrackerModifyReqDto;
import life.bareun.diary.habit.dto.response.HabitTrackerWeekResDto;
import life.bareun.diary.habit.dto.response.HabitTrackerDetailResDto;
import life.bareun.diary.habit.dto.response.HabitTrackerTodayResDto;
import life.bareun.diary.habit.entity.HabitTracker;
import life.bareun.diary.habit.entity.MemberHabit;
import org.springframework.web.multipart.MultipartFile;

public interface HabitTrackerService {

    void createHabitTrackerByDay(HabitTrackerCreateDto habitTrackerCreateDto);

    void createHabitTrackerByPeriod(HabitTrackerCreateDto habitTrackerCreateDto);

    void deleteAllHabitTracker(Long memberHabitId);

    void deleteAfterHabitTracker(Long memberHabitId);

    void modifyHabitTracker(MultipartFile image, HabitTrackerModifyReqDto habitTrackerModifyReqDto);

    HabitTrackerTodayResDto findAllTodayHabitTracker();

    HabitTrackerDetailResDto findDetailHabitTracker(Long habitTrackerId);

    HabitTrackerWeekResDto findAllWeekHabitTracker();

    Boolean existsByMemberHabitAndSucceededTimeIsNotNull(MemberHabit memberHabit);

    HabitTracker findLastHabitTracker(HabitTrackerLastDto habitTrackerLastDto);
}
