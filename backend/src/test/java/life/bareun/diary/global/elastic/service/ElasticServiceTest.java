package life.bareun.diary.global.elastic.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import life.bareun.diary.global.elastic.dto.ElasticDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ElasticServiceTest {

    @Autowired
    ElasticService elasticService;

    private final Logger rankLogger = LoggerFactory.getLogger("rank-log");

    @Test
    @DisplayName("엘라스틱서치 로그 수집")
    void findAllElasticLog() {
        rankLogger.info("rank-log {} {}", 1L, "랭킹 순위에 반영됩니다.");
        List<ElasticDto> elasticDtoList = elasticService.findAllElasticLog();
        assertNotNull(elasticDtoList);
    }
}