package life.bareun.diary.habit.service;

import java.util.List;
import life.bareun.diary.habit.dto.request.HabitCreateReqDto;
import life.bareun.diary.habit.dto.request.HabitDeleteReqDto;
import life.bareun.diary.habit.dto.response.HabitMatchResDto;
import life.bareun.diary.habit.dto.response.MemberHabitActiveResDto;
import life.bareun.diary.habit.dto.response.MemberHabitActiveSimpleResDto;
import life.bareun.diary.habit.dto.response.MemberHabitNonActiveResDto;
import life.bareun.diary.habit.dto.response.MemberHabitResDto;
import life.bareun.diary.habit.entity.MemberHabit;
import life.bareun.diary.member.entity.Member;

public interface HabitService {

    void createMemberHabit(HabitCreateReqDto habitCreateReqDto);

    void deleteMemberHabit(HabitDeleteReqDto habitDeleteReqDto);

    MemberHabitResDto findAllMonthMemberHabit(String monthValue);

    void connectHabitList();

    MemberHabitActiveResDto findAllActiveMemberHabit();

    MemberHabitNonActiveResDto findAllNonActiveMemberHabit();

    HabitMatchResDto findAllMatchHabit(String habitName);

    MemberHabitActiveSimpleResDto findAllActiveSimpleMemberHabit();

    List<MemberHabit> findAllActiveMemberHabitByMember(Member member);
}
