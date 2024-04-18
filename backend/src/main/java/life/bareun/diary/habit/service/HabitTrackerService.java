package life.bareun.diary.habit.service;

import java.time.LocalDate;
import life.bareun.diary.habit.dto.HabitTrackerCreateDto;

public interface HabitTrackerService {

    void createHabitTrackerByDay(HabitTrackerCreateDto habitTrackerCreateDto);

    void createHabitTrackerByPeriod(HabitTrackerCreateDto habitTrackerCreateDto);
}
