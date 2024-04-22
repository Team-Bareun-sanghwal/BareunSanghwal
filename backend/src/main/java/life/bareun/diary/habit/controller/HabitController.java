package life.bareun.diary.habit.controller;

import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.habit.dto.request.HabitCreateReqDto;
import life.bareun.diary.habit.dto.request.HabitDeleteReqDto;
import life.bareun.diary.habit.dto.request.HabitTrackerModifyReqDto;
import life.bareun.diary.habit.dto.response.HabitTrackerWeekResDto;
import life.bareun.diary.habit.dto.response.HabitTrackerDetailResDto;
import life.bareun.diary.habit.dto.response.HabitTrackerTodayResDto;
import life.bareun.diary.habit.service.HabitService;
import life.bareun.diary.habit.service.HabitTrackerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/habits")
public class HabitController {

    private final HabitService habitService;

    private final HabitTrackerService habitTrackerService;

    @PostMapping
    public ResponseEntity<BaseResponse<String>> createMemberHabit(
        @RequestBody HabitCreateReqDto habitCreateReqDto) {
        habitService.createMemberHabit(habitCreateReqDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(BaseResponse.success(HttpStatus.CREATED.value(), "해빗 생성이 완료되었습니다.", null));
    }

    @PostMapping("/delete")
    public ResponseEntity<BaseResponse<String>> deleteMemberHabit(
        @RequestBody HabitDeleteReqDto habitDeleteReqDto) {
        habitService.deleteMemberHabit(habitDeleteReqDto);
        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse.success(HttpStatus.OK.value(), "해빗 삭제가 완료되었습니다.", null));
    }

    @PatchMapping("/completion")
    public ResponseEntity<BaseResponse<String>> modifyHabitTracker(
        @RequestPart(value = "image", required = false) MultipartFile image,
        @RequestPart(value = "HabitTrackerModifyReqDto") HabitTrackerModifyReqDto
            habitTrackerModifyReqDto) {
        habitTrackerService.modifyHabitTracker(image, habitTrackerModifyReqDto);
        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse.success(HttpStatus.OK.value(), "해빗 트래커를 완료하였습니다.", null));
    }

    @GetMapping("/today")
    public ResponseEntity<BaseResponse<HabitTrackerTodayResDto>> findAllTodayHabitTracker() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse.success(HttpStatus.OK.value(), "오늘의 해빗 트래커 리스트 조회를 성공하였습니다.",
                habitTrackerService.findAllTodayHabitTracker()));
    }

    @GetMapping("/{habitTrackerId}")
    public ResponseEntity<BaseResponse<HabitTrackerDetailResDto>> findDetailHabitTracker(
        @PathVariable("habitTrackerId") Long habitTrackerId) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse.success(HttpStatus.OK.value(), "오늘의 해빗 트래커 상세 조회를 성공하였습니다.",
                habitTrackerService.findDetailHabitTracker(habitTrackerId)));
    }

    @GetMapping("/day")
    public ResponseEntity<BaseResponse<HabitTrackerWeekResDto>> findAllWeekHabitTracker() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse.success(HttpStatus.OK.value(), "요일 별 해빗 트래커 개수 조회를 성공하였습니다.",
                habitTrackerService.findAllWeekHabitTracker()));
    }

}
