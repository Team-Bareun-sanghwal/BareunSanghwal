package life.bareun.diary.habit.controller;

import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.habit.dto.request.HabitCreateReqDto;
import life.bareun.diary.habit.dto.request.HabitDeleteReqDto;
import life.bareun.diary.habit.service.HabitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/habits")
public class HabitController {

    private final HabitService habitService;

    @PostMapping
    public ResponseEntity<BaseResponse<String>> createMemberHabit(
        @RequestBody HabitCreateReqDto habitCreateReqDto) {
        habitService.createMemberHabit(habitCreateReqDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(BaseResponse.success(HttpStatus.CREATED.value(), "생성이 완료되었습니다.", null));
    }

    @PostMapping("/delete")
    public ResponseEntity<BaseResponse<String>> deleteMemberHabit(@RequestBody HabitDeleteReqDto habitDeleteReqDto) {
        habitService.deleteMemberHabit(habitDeleteReqDto);
        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse.success(HttpStatus.OK.value(), "삭제가 완료되었습니다.", null));
    }
}
