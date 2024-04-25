package life.bareun.diary.recap.repository;

import java.util.List;
import life.bareun.diary.recap.entity.Recap;
import life.bareun.diary.recap.entity.RecapHabitAccomplished;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecapHabitAccomplishedRepository
    extends JpaRepository<RecapHabitAccomplished, Long> {
    List<RecapHabitAccomplished> findAllByRecap_OrderByActionCountDesc(Recap recap);

    RecapHabitAccomplished findByRecapAndIsBest(Recap recap, Boolean isBest);

}
