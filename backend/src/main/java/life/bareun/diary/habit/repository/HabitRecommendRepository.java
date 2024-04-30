package life.bareun.diary.habit.repository;

import java.util.List;
import life.bareun.diary.habit.entity.HabitRecommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRecommendRepository extends JpaRepository<HabitRecommend, Long> {
    List<HabitRecommend> findAllByOrderByIdAsc();
}
