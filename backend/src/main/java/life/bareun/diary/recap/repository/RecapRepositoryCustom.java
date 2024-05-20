package life.bareun.diary.recap.repository;

import java.util.List;
import life.bareun.diary.recap.dto.RecapMemberDto;
import life.bareun.diary.recap.dto.RecapMemberHabitDto;
import life.bareun.diary.recap.dto.RecapMemberMonthDto;
import life.bareun.diary.recap.dto.RecapModifyDto;
import life.bareun.diary.recap.dto.RecapMonthDto;

public interface RecapRepositoryCustom {

    List<RecapMemberDto> findAllAppropriateMember(RecapMonthDto recapMonthDto);

    List<RecapMemberHabitDto> findAllAppropriateMemberHabit(RecapMemberMonthDto recapMemberMonthDto);

    void modifyRecap(RecapModifyDto recapModifyDto);
}
