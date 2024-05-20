package life.bareun.diary.product.repository;

import java.util.List;
import java.util.Optional;
import life.bareun.diary.product.entity.StreakColor;
import life.bareun.diary.product.entity.StreakColorGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreakColorRepository extends JpaRepository<StreakColor, Integer> {

    List<StreakColor> findAllByStreakColorGrade(StreakColorGrade gotchaGrade);

    Optional<StreakColor> findByName(String name);
}
