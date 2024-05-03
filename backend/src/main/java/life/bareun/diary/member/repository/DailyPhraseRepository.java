package life.bareun.diary.member.repository;

import life.bareun.diary.member.entity.DailyPhrase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyPhraseRepository extends JpaRepository<DailyPhrase, Long> {

}
