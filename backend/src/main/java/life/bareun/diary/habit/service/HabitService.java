package life.bareun.diary.habit.service;

import life.bareun.diary.habit.dto.request.HabitCreateReqDto;

public interface HabitService {
    void createMemberHabit(HabitCreateReqDto habitCreateReqDto);
}
