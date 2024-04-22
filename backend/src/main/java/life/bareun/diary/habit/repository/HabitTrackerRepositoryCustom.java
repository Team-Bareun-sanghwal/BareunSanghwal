package life.bareun.diary.habit.repository;

import java.util.List;
import life.bareun.diary.habit.dto.HabitTrackerDeleteDto;
import life.bareun.diary.habit.dto.HabitTrackerLastDto;
import life.bareun.diary.habit.dto.HabitTrackerTodayDto;
import life.bareun.diary.habit.dto.HabitTrackerTodayFactorDto;
import life.bareun.diary.habit.dto.request.HabitTrackerModifyDto;
import life.bareun.diary.habit.entity.HabitTracker;

public interface HabitTrackerRepositoryCustom {

    void deleteAfterHabitTracker(HabitTrackerDeleteDto habitDeleteReqDto);

    void modifyHabitTracker(HabitTrackerModifyDto habitTrackerModifyDto);

    List<HabitTrackerTodayDto>
        findAllTodayHabitTracker(HabitTrackerTodayFactorDto habitTrackerTodayFactorDto);

    HabitTracker findLastHabitTracker(HabitTrackerLastDto habitTrackerLastDto);

}
