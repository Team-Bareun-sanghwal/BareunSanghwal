package life.bareun.diary.habit.repository;

import life.bareun.diary.habit.dto.HabitTrackerDeleteDto;
import life.bareun.diary.habit.dto.request.HabitTrackerModifyDto;

public interface HabitTrackerRepositoryCustom {

    void deleteAfterHabitTracker(HabitTrackerDeleteDto habitDeleteReqDto);

    void modifyHabitTracker(HabitTrackerModifyDto habitTrackerModifyDto);

}
