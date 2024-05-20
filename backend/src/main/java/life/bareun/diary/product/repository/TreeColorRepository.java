package life.bareun.diary.product.repository;

import java.util.List;
import java.util.Optional;
import life.bareun.diary.product.entity.TreeColor;
import life.bareun.diary.product.entity.TreeColorGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeColorRepository extends JpaRepository<TreeColor, Integer> {

    List<TreeColor> findAllByTreeColorGrade(TreeColorGrade gotchaGrade);

    Optional<TreeColor> findByName(String name);
}
