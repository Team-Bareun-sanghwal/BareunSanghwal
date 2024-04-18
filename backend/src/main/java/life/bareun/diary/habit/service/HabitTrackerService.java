package life.bareun.diary.habit.service;

import life.bareun.diary.habit.dto.HabitTrackerCreateDto;

public interface HabitTrackerService {

    void createHabitTrackerByDay(HabitTrackerCreateDto habitTrackerCreateDto);

    void createHabitTrackerByPeriod(HabitTrackerCreateDto habitTrackerCreateDto);

    void deleteAllHabitTracker(Long memberHabitId);

    void deleteAfterHabitTracker(Long memberHabitId);
}
