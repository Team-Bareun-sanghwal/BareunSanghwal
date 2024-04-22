package life.bareun.diary.member.repository;

import java.util.Optional;
import life.bareun.diary.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsBySub(String sub);

    Optional<Member> findBySub(String sub);
}
