package life.bareun.diary.recap.repository;

import java.util.List;
import life.bareun.diary.member.entity.Member;
import life.bareun.diary.recap.entity.Recap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecapRepository extends JpaRepository<Recap, Long>, RecapRepositoryCustom {
    List<Recap> findAllByMember(Member member);
}
