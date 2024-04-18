package life.bareun.diary.habit.service;

import life.bareun.diary.habit.dto.request.HabitCreateReqDto;
import life.bareun.diary.habit.dto.request.HabitDeleteReqDto;

public interface HabitService {

    void createMemberHabit(HabitCreateReqDto habitCreateReqDto);

    void deleteMemberHabit(HabitDeleteReqDto habitDeleteReqDto);

}
