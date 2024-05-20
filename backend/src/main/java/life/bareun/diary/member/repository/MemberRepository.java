package life.bareun.diary.member.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import life.bareun.diary.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findBySub(String sub);

    List<Member> findAllByLastHarvestedDateIsNullOrLastHarvestedDateIsBefore(LocalDate localDate);
}
