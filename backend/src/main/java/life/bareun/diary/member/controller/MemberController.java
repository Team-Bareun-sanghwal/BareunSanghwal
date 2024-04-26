package life.bareun.diary.member.controller;

import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.member.dto.request.MemberUpdateReq;
import life.bareun.diary.member.dto.response.MemberInfoRes;
import life.bareun.diary.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        MemberUpdateReq memberUpdateReq
    ) {
        memberService.update(memberUpdateReq);

        return ResponseEntity.ok(
            BaseResponse.success(
                HttpStatus.OK.value(),
                "회원 정보가 수정되었습니다",
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
    public ResponseEntity<BaseResponse<MemberInfoRes>> info() {
        MemberInfoRes info = memberService.info();
        return ResponseEntity.ok(
            BaseResponse.success(
                HttpStatus.OK.value(),
                "회원 정보를 읽어왔습니다.",
                info
            )
        );
    }
}
