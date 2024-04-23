package life.bareun.diary.recap.repository;

import life.bareun.diary.recap.entity.RecapHabitAccomplished;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecapHabitAccomplishedRepository
    extends JpaRepository<RecapHabitAccomplished, Long> {

}
