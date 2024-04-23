package life.bareun.diary.recap.repository;

import life.bareun.diary.recap.entity.Recap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecapRepository extends JpaRepository<Recap, Long>, RecapRepositoryCustom {

}
