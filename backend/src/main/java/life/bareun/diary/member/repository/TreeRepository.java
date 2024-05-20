package life.bareun.diary.member.repository;

import java.util.List;
import life.bareun.diary.member.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRepository extends JpaRepository<Tree, Long> {

    List<Tree> findAllByOrderByLevelAsc();
}
