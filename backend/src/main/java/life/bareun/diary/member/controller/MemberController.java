package life.bareun.diary.member.controller;

import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.global.security.config.SecurityConfig;
import life.bareun.diary.member.dto.request.MemberUpdateReqDto;
import life.bareun.diary.member.dto.response.MemberInfoResDto;
import life.bareun.diary.member.dto.response.MemberStatisticResDto;
import life.bareun.diary.member.dto.response.MemberStreakColorResDto;
import life.bareun.diary.member.dto.response.MemberTreeColorResDto;
import life.bareun.diary.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

        return ResponseEntity.ok(
            BaseResponse.success(
                HttpStatus.OK.value(),
                "회원 정보가 수정되었습니다",
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
                    "나 너무 많은 일이 잇엇어 힘들다 진짜",
                    null
                )
            );
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse<Void>> delete() {
        memberService.delete();
        return ResponseEntity.ok(
            BaseResponse.success(
                HttpStatus.OK.value(),
                "회원 정보가 삭제되었습니다",
                null
            )
        );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<MemberInfoResDto>> info() {
        MemberInfoResDto info = memberService.info();
        return ResponseEntity.ok(
            BaseResponse.success(
                HttpStatus.OK.value(),
                "회원 정보를 읽어왔습니다.",
                info
            )
        );
    }

    @GetMapping("/color/streak")
    public ResponseEntity<BaseResponse<MemberStreakColorResDto>> streakColor() {
        MemberStreakColorResDto memberStreakColorResDto = memberService.streakColor();
        return ResponseEntity.ok(
            BaseResponse.success(
                HttpStatus.OK.value(),
                "회원의 현재 스트릭 색상 정보를 읽어왔습니다.",
                memberStreakColorResDto
            )
        );
    }

    @GetMapping("/color/tree")
    public ResponseEntity<BaseResponse<MemberTreeColorResDto>> treeColor() {
        MemberTreeColorResDto memberTreeColorResDto = memberService.treeColor();
        return ResponseEntity.ok(
            BaseResponse.success(
                HttpStatus.OK.value(),
                "회원의 현재 스트릭 색상 정보를 읽어왔습니다.",
                memberTreeColorResDto
            )
        );
    }

    @GetMapping("/statistic")
    public ResponseEntity<BaseResponse<MemberStatisticResDto>> statistic() {
        MemberStatisticResDto statistic = memberService.statistic();
        return ResponseEntity.status(HttpStatus.OK.value())
            .body(
                BaseResponse.success(
                    HttpStatus.OK.value(),
                    "통계닷!",
                    statistic
                )
            );
    }
}
