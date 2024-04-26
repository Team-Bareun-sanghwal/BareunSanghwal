package life.bareun.diary.product.repository;

import java.util.List;
import life.bareun.diary.product.entity.StreakColor;
import life.bareun.diary.product.entity.StreakColorGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreakColorRepository extends JpaRepository<StreakColor, Long> {

    List<StreakColor> findByStreakColorGrade(StreakColorGrade streakColorGrade);
}
