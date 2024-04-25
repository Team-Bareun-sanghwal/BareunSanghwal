package life.bareun.diary.recap.controller;

import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.recap.dto.request.RecapListResDto;
import life.bareun.diary.recap.service.RecapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recaps")
public class RecapController {

    private final RecapService recapService;

    @PostMapping
    public ResponseEntity<BaseResponse<String>> createRecap() {
        recapService.createRecap();
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(BaseResponse.success(HttpStatus.CREATED.value(), "리캡 생성을 성공하였습니다.", null));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<RecapListResDto>> findAllRecap() {
        return ResponseEntity.status(HttpStatus.OK).body(
            BaseResponse.success(HttpStatus.OK.value(), "리캡 리스트 조회를 성공하였습니다.",
                recapService.findAllRecap()));
    }
}
