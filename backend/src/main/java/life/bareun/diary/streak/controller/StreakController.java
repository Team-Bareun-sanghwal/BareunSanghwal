package life.bareun.diary.streak.controller;

import java.util.Optional;
import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.streak.service.StreakService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/streak")
@RequiredArgsConstructor
public class StreakController {

    private final StreakService streakService;

    @GetMapping("/")
    public BaseResponse<?> findMemberStreakCount() {
        return BaseResponse.success(HttpStatus.OK.value(), "사용자 전체 스트릭 조회에 성공했습니다.", null);
    }

    @GetMapping("/{dateString}/{habitId}")
    public BaseResponse<?> findAllMemberStreakByHabit(@PathVariable("dateString") String dateString,
        @PathVariable(name = "habitId", required = false) Optional<Integer> optionalHabitId) {
        int habitId = optionalHabitId.orElse(-1);
        return BaseResponse.success(HttpStatus.OK.value(), "해당 해빗의 사용자 월간 스트릭 조회에 성공했습니다", null);
    }
}
