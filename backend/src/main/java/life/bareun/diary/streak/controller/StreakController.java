package life.bareun.diary.streak.controller;

import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.streak.dto.request.StreakRecoveryReqDto;
import life.bareun.diary.streak.dto.response.HabitStreakResDto;
import life.bareun.diary.streak.dto.response.MemberStreakResDto;
import life.bareun.diary.streak.service.StreakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/streaks")
@RequiredArgsConstructor
public class StreakController {

    private final StreakService streakService;

    @GetMapping("")
    public ResponseEntity<BaseResponse<MemberStreakResDto>> findMemberStreakCount() {

        MemberStreakResDto memberStreakResponseDto = streakService.getMemberStreakResDto();

        return ResponseEntity.status(HttpStatus.OK.value())
            .body(BaseResponse.success(HttpStatus.OK.value(), "사용자 전체 스트릭 조회에 성공했습니다.", memberStreakResponseDto));
    }

    @GetMapping(value = {"/{dateString}/{memberHabitId}", "/{dateString}"})
    public ResponseEntity<BaseResponse<HabitStreakResDto>> findAllMemberStreakByHabit(
        @PathVariable("dateString") String dateString,
        @PathVariable(name = "memberHabitId", required = false) Long memberHabitId) {

        HabitStreakResDto habitStreakResDto = null;
        String message = null;

        if (memberHabitId != null) {
            habitStreakResDto = streakService.getHabitStreakResDtoByMemberHabitId(dateString, memberHabitId);
            message = "사용자의 월간 스트릭 중 선택한 해빗 스트릭 조회에 성공했습니다.";
        } else {
            habitStreakResDto = streakService.getHabitStreakResDtoByMember(dateString);
            message = "사용자의 월간 스트릭 중 선택한 해빗에 관련된 스트릭 조회에 성공했습니다.";
        }

        return ResponseEntity.status(HttpStatus.OK.value())
            .body(BaseResponse.success(HttpStatus.OK.value(), message, habitStreakResDto));
    }

    @PatchMapping("/recovery")
    public ResponseEntity<BaseResponse<?>> recoveryStreak(
        @RequestBody StreakRecoveryReqDto streakRecoveryReqDto) {
        
        streakService.recoveryStreak(streakRecoveryReqDto.date());

        return ResponseEntity.status(HttpStatus.OK.value())
            .body(BaseResponse.success(HttpStatus.OK.value(), "성공적으로 리커버리가 적용되었습니다.", null));
    }
}
