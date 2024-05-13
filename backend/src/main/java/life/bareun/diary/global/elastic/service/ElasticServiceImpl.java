package life.bareun.diary.global.elastic.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import life.bareun.diary.global.elastic.exception.ElasticErrorCode;
import life.bareun.diary.global.elastic.exception.ElasticException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import life.bareun.diary.global.elastic.dto.ElasticDto;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ElasticServiceImpl implements ElasticService {

    private final RestHighLevelClient client;
    @Override
    public List<ElasticDto> findAllElasticLog() {
        List<ElasticDto> logList = new ArrayList<>();
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
        String scrollId = null;
        try {
            SearchRequest searchRequest = new SearchRequest("rank-log");
            searchRequest.scroll(scroll);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            searchSourceBuilder.query(
                QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchQuery("log_name.keyword", "rank-log"))
                    .filter(QueryBuilders.rangeQuery("@timestamp")
                        .gte("now-168h")
                        .lte("now"))
            );
            searchSourceBuilder.size(100);
            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            scrollId = searchResponse.getScrollId();
            SearchHit[] searchHits = searchResponse.getHits().getHits();

            refineLog(searchHits, logList);
            while (searchHits.length > 0) {
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(scroll);
                searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
                scrollId = searchResponse.getScrollId();
                searchHits = searchResponse.getHits().getHits();
                // 다음 배치 처리
                refineLog(searchHits, logList);
            }
        } catch (IOException e) {
            log.error("FAIL_COLLECT_ELASTIC_LOG", e);
            throw new ElasticException(ElasticErrorCode.FAIL_COLLECT_ELASTIC_LOG);
        } finally {
            // Scroll 컨텍스트 정리
            if (scrollId != null) {
                ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
                clearScrollRequest.addScrollId(scrollId);
                int retries = 3;
                while(retries-- > 0) {
                    try {
                        client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
                        break;
                    } catch (IOException e) {
                        log.error("FAIL_SCROLL_ELASTIC_LOG", e);
                        log.error(ElasticErrorCode.FAIL_SCROLL_ELASTIC_LOG.getMessage());
                    }
                }
            }
        }
        return logList;
    }

    private void refineLog(SearchHit[] searchHits, List<ElasticDto> logList) {
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit hit : searchHits) {
            try {
                // 히트의 소스를 JsonNode로 파싱
                JsonNode jsonNode = objectMapper.readTree(hit.getSourceAsString());

                if(jsonNode.path("habit_id").asText().equals("null")) {
                    continue;
                }

                // 필요한 필드 추출
                long habitId = Long.parseLong(jsonNode.path("habit_id").asText());
                if(habitId < 1 || habitId > 313) {
                    continue;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(jsonNode.path("@timestamp").asText(), formatter.withZone(ZoneOffset.UTC));

                // ZonedDateTime을 LocalDateTime으로 변환
                LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
                logList.add(new ElasticDto(habitId, localDateTime));
            }
            catch (Exception e) {
                log.error("FAIL_REFINE_ELASTIC_LOG", e);
                return;
            }
        }
    }
}
