package life.bareun.diary.streak.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import life.bareun.diary.global.auth.util.AuthUtil;
import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.exception.MemberErrorCode;
import life.bareun.diary.member.exception.MemberException;
import life.bareun.diary.member.repository.MemberRepository;
import life.bareun.diary.streak.dto.request.StreakRecoveryReqDto;
import life.bareun.diary.streak.dto.response.MemberStreakResDto;
import life.bareun.diary.streak.dto.response.MonthStreakResDto;
import life.bareun.diary.streak.dto.response.StreakRecoveryInfoResDto;
import life.bareun.diary.streak.service.StreakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/streaks")
@RequiredArgsConstructor
public class StreakController {

    private final StreakService streakService;
    private final MemberRepository memberRepository;

    @GetMapping("")
    public ResponseEntity<BaseResponse<MemberStreakResDto>> findMemberStreakCount() {

        MemberStreakResDto memberStreakResponseDto = streakService.getMemberStreakResDto();

        return ResponseEntity.status(HttpStatus.OK.value())
            .body(BaseResponse.success(HttpStatus.OK.value(), "사용자 전체 스트릭 조회에 성공했습니다.", memberStreakResponseDto));
    }

    @GetMapping(value = {"/{dateString}/{memberHabitId}", "/{dateString}"})
    public ResponseEntity<BaseResponse<MonthStreakResDto>> findAllMemberStreakByHabit(
        @PathVariable("dateString") String dateString,
        @PathVariable(name = "memberHabitId", required = false) Long memberHabitId) {

        MonthStreakResDto monthStreakResDto = null;
        String message = null;

        if (memberHabitId != null) {
            monthStreakResDto = streakService.getHabitStreakResDtoByMemberHabitId(dateString, memberHabitId);
            message = "사용자의 월간 스트릭 중 선택한 해빗 스트릭 조회에 성공했습니다.";
        } else {
            monthStreakResDto = streakService.getHabitStreakResDtoByMember(dateString);
            message = "사용자의 월간 스트릭 스트릭 조회에 성공했습니다.";
        }

        return ResponseEntity.status(HttpStatus.OK.value())
            .body(BaseResponse.success(HttpStatus.OK.value(), message, monthStreakResDto));
    }

    @PatchMapping("/recovery")
    public ResponseEntity<BaseResponse<String>> recoveryStreak(
        @RequestBody StreakRecoveryReqDto streakRecoveryReqDto) {

        streakService.recoveryStreak(streakRecoveryReqDto.date());

        return ResponseEntity.status(HttpStatus.OK.value())
            .body(BaseResponse.success(HttpStatus.OK.value(), "성공적으로 리커버리가 적용되었습니다.", null));
    }

    @GetMapping("/recovery/{date}")
    public ResponseEntity<BaseResponse<StreakRecoveryInfoResDto>> asdf(@PathVariable("date") LocalDate date) {

        return ResponseEntity.status(HttpStatus.OK.value())
            .body(BaseResponse.success(HttpStatus.OK.value(),
                "리커버리 사용 시 변동될 스트릭 정보 조회에 성공했습니다.",
                streakService.getStreakRecoveryInfoResDto(date)));
    }

    @PostMapping("/dummy/daily")
    public ResponseEntity<BaseResponse<StreakRecoveryInfoResDto>> createDummyDaily() {


        Member member = getCurrentMember();
        LocalDate startDate = LocalDate.of(2023, 8, 2);
        LocalDate endDate = LocalDate.of(2024, 5, 19);

        while (startDate.isBefore(endDate)) {
            LocalDateTime dateTime = LocalDateTime.of(startDate, LocalTime.of(0, 0));

            streakService.createDailyStreakDummy(member, startDate, dateTime, dateTime);

            startDate = startDate.plusDays(1);
        }


        return ResponseEntity.status(HttpStatus.OK.value())
                .body(BaseResponse.success(HttpStatus.OK.value(),
                        "더미 데일리 데이터 생성 완료.",
                        null));
    }

    private Member getCurrentMember() {
        return memberRepository.findById(AuthUtil.getMemberIdFromAuthentication())
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }
}
