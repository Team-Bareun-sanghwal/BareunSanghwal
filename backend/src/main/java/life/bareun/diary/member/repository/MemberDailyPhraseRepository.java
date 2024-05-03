package life.bareun.diary.member.repository;

import life.bareun.diary.member.entity.MemberDailyPhrase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDailyPhraseRepository extends JpaRepository<MemberDailyPhrase, Long>, MemberDailyPhraseRepositoryCustom {

}
