package life.bareun.diary.product.repository;

import java.util.Optional;
import life.bareun.diary.product.entity.TreeColor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeColorRepository extends JpaRepository<TreeColor, Integer> {

    Optional<TreeColor> findByName(String name);
}
