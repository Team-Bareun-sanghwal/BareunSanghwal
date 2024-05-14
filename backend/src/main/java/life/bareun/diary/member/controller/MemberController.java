package life.bareun.diary.member.controller;

import life.bareun.diary.global.auth.config.SecurityConfig;
import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.member.dto.request.MemberUpdateReqDto;
import life.bareun.diary.member.dto.response.MemberDailyPhraseResDto;
import life.bareun.diary.member.dto.response.MemberHabitListResDto;
import life.bareun.diary.member.dto.response.MemberHabitTrackersResDto;
import life.bareun.diary.member.dto.response.MemberInfoResDto;
import life.bareun.diary.member.dto.response.MemberPointResDto;
import life.bareun.diary.member.dto.response.MemberStatisticResDto;
import life.bareun.diary.member.dto.response.MemberStreakColorResDto;
import life.bareun.diary.member.dto.response.MemberStreakInfoResDto;
import life.bareun.diary.member.dto.response.MemberStreakRecoveryCountResDto;
import life.bareun.diary.member.dto.response.MemberTreeInfoResDto;
import life.bareun.diary.member.dto.response.MemberTreePointResDto;
import life.bareun.diary.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/members")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PatchMapping
    public ResponseEntity<BaseResponse<Void>> update(
        @RequestBody
        MemberUpdateReqDto memberUpdateReqDto
    ) {
        memberService.update(memberUpdateReqDto);

        return ResponseEntity
            .status(
                HttpStatus.OK.value()
            )
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "사용자 정보가 수정되었습니다",
                    null
                )
            );
    }


    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(
        @RequestHeader(SecurityConfig.REFRESH_TOKEN_HEADER)
        String refreshToken
    ) {
        memberService.logout(refreshToken);

        return ResponseEntity
            .status(
                HttpStatus.OK.value()
            )
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "로그아웃되었습니다.",
                    null
                )
            );
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> delete() {
        memberService.delete();
        return ResponseEntity
            .status(
                HttpStatus.OK.value()
            )
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "사용자 정보가 삭제되었습니다",
                    null
                )
            );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<MemberInfoResDto>> info() {
        MemberInfoResDto memberInfoResDto = memberService.info();
        return ResponseEntity
            .status(
                HttpStatus.OK.value()
            )
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "사용자 정보를 읽어왔습니다.",
                    memberInfoResDto
                )
            );
    }

    @GetMapping("/streak/color")
    public ResponseEntity<BaseResponse<MemberStreakColorResDto>> streakColor() {
        MemberStreakColorResDto memberStreakColorResDto = memberService.streakInfo();
        return ResponseEntity
            .status(
                HttpStatus.OK.value()
            )
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "사용자의 현재 스트릭 색상 정보를 읽어왔습니다.",
                    memberStreakColorResDto
                )
            );
    }

    @GetMapping("/tree")
    public ResponseEntity<BaseResponse<MemberTreeInfoResDto>> treeColor() {
        MemberTreeInfoResDto memberTreeInfoResDto = memberService.treeInfo();
        return ResponseEntity
            .status(
                HttpStatus.OK.value()
            )
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "사용자의 현재 나무 색상 정보를 읽어왔습니다.",
                    memberTreeInfoResDto
                )
            );
    }

    @GetMapping("/point")
    public ResponseEntity<BaseResponse<MemberPointResDto>> point() {
        MemberPointResDto memberPointResDto = memberService.point();
        return ResponseEntity
            .status(
                HttpStatus.OK.value()
            )
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "사용자의 현재 보유 포인트 정보를 읽어왔습니다.",
                    memberPointResDto
                )
            );
    }

    @GetMapping("/streak")
    public ResponseEntity<BaseResponse<MemberStreakInfoResDto>> streak() {
        MemberStreakInfoResDto memberStreakInfoResDto = memberService.streak();
        return ResponseEntity
            .status(
                HttpStatus.OK.value()
            )
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "사용자의 현재 스트릭 정보를 읽어왔습니다.",
                    memberStreakInfoResDto
                )
            );
    }

    @GetMapping("/recovery-count")
    public ResponseEntity<BaseResponse<MemberStreakRecoveryCountResDto>> recoveryCount() {
        MemberStreakRecoveryCountResDto memberStreakRecoveryCountResDto = memberService.streakRecoveryCount();
        return ResponseEntity
            .status(
                HttpStatus.OK.value()
            )
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "사용자의 리커버리 갯수 보유 정보를 읽어왔습니다.",
                    memberStreakRecoveryCountResDto
                )
            );
    }

    @GetMapping("/habits")
    public ResponseEntity<BaseResponse<MemberHabitListResDto>> habits() {
        MemberHabitListResDto memberHabitListResDto = memberService.habits();

        return ResponseEntity
            .status(
                HttpStatus.OK.value()
            )
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "사용자의 해빗 목록을 읽어왔습니다.",
                    memberHabitListResDto
                )
            );
    }

    @GetMapping("/statistic")
    public ResponseEntity<BaseResponse<MemberStatisticResDto>> statistic() {
        MemberStatisticResDto statistic = memberService.statistic();
        return ResponseEntity
            .status(
                HttpStatus.OK.value()
            )
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "사용자의 통계 데이터를 읽어왔습니다.",
                    statistic
                )
            );
    }

    @GetMapping("/tree/point")
    public ResponseEntity<BaseResponse<MemberTreePointResDto>> treePoint() {
        MemberTreePointResDto memberTreePointResDto = memberService.treePoint();

        return ResponseEntity
            .status(
                HttpStatus.OK.value()
            )
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    String.format("%d 포인트를 획득했습니다.", memberTreePointResDto.point()),
                    memberTreePointResDto
                )
            );
    }

    @GetMapping("/{memberHabitId}/tracker")
    public ResponseEntity<BaseResponse<MemberHabitTrackersResDto>> habitTrackers(
        @PathVariable
        String memberHabitId
    ) {
        MemberHabitTrackersResDto memberHabitTrackersResDto = memberService.habitTrackers(
            memberHabitId
        );

        return ResponseEntity
            .status(
                HttpStatus.OK.value()
            )
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    String.format("%d번 해빗의 해빗 트래커 정보를 읽어왔습니다,", Integer.parseInt(memberHabitId)),
                    memberHabitTrackersResDto
                )
            );
    }

    @GetMapping("/daily-phrase")
    public ResponseEntity<BaseResponse<MemberDailyPhraseResDto>> daliyPhrase() {
        MemberDailyPhraseResDto memberDailyPhraseResDto = memberService.dailyPhrase();

        return ResponseEntity
            .status(
                HttpStatus.OK.value()
            )
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "오늘의 문구를 읽어 왔습니다.",
                    memberDailyPhraseResDto
                )
            );
    }

}
