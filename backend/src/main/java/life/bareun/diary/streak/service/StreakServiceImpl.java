package life.bareun.diary.streak.service;

import life.bareun.diary.streak.repository.MemberDailyStreakRepository;
import life.bareun.diary.streak.repository.MemberTotalStreakRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StreakServiceImpl implements StreakService {

    private final MemberDailyStreakRepository memberDailyStreakRepository;
    private final MemberTotalStreakRepository memberTotalStreakRepository;
    
}
