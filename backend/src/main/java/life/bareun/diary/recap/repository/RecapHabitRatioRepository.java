package life.bareun.diary.recap.repository;

import java.util.List;
import life.bareun.diary.recap.entity.Recap;
import life.bareun.diary.recap.entity.RecapHabitRatio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecapHabitRatioRepository extends JpaRepository<RecapHabitRatio, Long> {
    List<RecapHabitRatio> findTop4ByRecap_OrderByRatioDesc(Recap recap);
}
