package life.bareun.diary.habit.repository;

import life.bareun.diary.habit.dto.HabitTrackerDeleteDto;

public interface HabitTrackerRepositoryCustom {

    void deleteAfterHabitTracker(HabitTrackerDeleteDto habitDeleteReqDto);

}
