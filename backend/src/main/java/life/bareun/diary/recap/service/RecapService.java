package life.bareun.diary.recap.service;

import life.bareun.diary.recap.dto.request.RecapListResDto;

public interface RecapService {
    void createRecap();

    RecapListResDto findAllRecap();
}
