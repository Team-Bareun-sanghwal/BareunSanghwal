package life.bareun.diary.habit.service;

import life.bareun.diary.habit.dto.HabitTrackerCreateDto;
import life.bareun.diary.habit.entity.HabitTracker;
import life.bareun.diary.habit.repository.HabitTrackerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitTrackerServiceImpl implements HabitTrackerService {

    private final HabitTrackerRepository habitTrackerRepository;

    @Override
    public void createHabitTrackerByDay(HabitTrackerCreateDto habitTrackerCreateDto) {
        habitTrackerRepository.save(
            HabitTracker.builder().memberHabit(habitTrackerCreateDto.memberHabit())
                .createdYear(habitTrackerCreateDto.targetDay().getYear())
                .createdMonth(habitTrackerCreateDto.targetDay().getMonthValue())
                .createdDay(habitTrackerCreateDto.targetDay().getDayOfMonth())
                .day(habitTrackerCreateDto.amount()).build());
    }

    @Override
    public void createHabitTrackerByPeriod(HabitTrackerCreateDto habitTrackerCreateDto) {
        habitTrackerRepository.save(
            HabitTracker.builder().memberHabit(habitTrackerCreateDto.memberHabit())
                .createdYear(habitTrackerCreateDto.targetDay().getYear())
                .createdMonth(habitTrackerCreateDto.targetDay().getMonthValue())
                .createdDay(habitTrackerCreateDto.targetDay().getDayOfMonth())
                .day(habitTrackerCreateDto.amount()).build());
    }
}
