package life.bareun.diary.global.elastic.controller;

import java.util.List;
import life.bareun.diary.global.common.response.BaseResponse;
import life.bareun.diary.global.elastic.dto.ElasticDto;
import life.bareun.diary.global.elastic.service.ElasticService;
import life.bareun.diary.habit.dto.response.HabitTrackerTodayResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/elastic")
public class ElasticController {

    private final ElasticService elasticService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<ElasticDto>>> findAllTodayHabitTracker() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(BaseResponse.success(HttpStatus.OK.value(), "엘라스틱서치 로그 리스트 조회를 성공하였습니다.",
                elasticService.findAllElasticLog()));
    }

}
