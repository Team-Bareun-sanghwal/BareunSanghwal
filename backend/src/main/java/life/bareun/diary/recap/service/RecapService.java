package life.bareun.diary.recap.service;

import life.bareun.diary.recap.dto.response.RecapDetailResDto;
import life.bareun.diary.recap.dto.response.RecapListResDto;

public interface RecapService {
    void createRecap();

    RecapListResDto findAllRecap();

    RecapDetailResDto findDetailRecap(Long recapId);
}
