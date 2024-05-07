package life.bareun.diary.member.repository;

import java.util.Optional;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.member.entity.MemberDailyPhrase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDailyPhraseRepository extends JpaRepository<MemberDailyPhrase, Long>,
    MemberDailyPhraseRepositoryCustom {

    Optional<MemberDailyPhrase> findByMember(Member member);
}
