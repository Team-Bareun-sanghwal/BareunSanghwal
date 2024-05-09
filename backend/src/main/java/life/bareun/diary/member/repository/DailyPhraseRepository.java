package life.bareun.diary.member.repository;

import java.util.Optional;
import life.bareun.diary.member.entity.DailyPhrase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyPhraseRepository extends JpaRepository<DailyPhrase, Long> {
    Optional<DailyPhrase> findByPhrase(String phrase);
}
