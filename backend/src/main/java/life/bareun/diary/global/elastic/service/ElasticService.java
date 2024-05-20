package life.bareun.diary.global.elastic.service;

import java.util.List;
import life.bareun.diary.global.elastic.dto.ElasticDto;

public interface ElasticService {

    List<ElasticDto> findAllElasticLog();

}
