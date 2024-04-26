package life.bareun.diary.member.repository;

import java.util.Optional;
import life.bareun.diary.member.entity.MemberRecovery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRecoveryRepository extends JpaRepository<MemberRecovery, Long> {

    Optional<MemberRecovery> findByMemberId(Long id);

    void deleteByMemberId(Long id);
}
