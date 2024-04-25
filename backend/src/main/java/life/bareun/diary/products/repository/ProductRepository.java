package life.bareun.diary.products.repository;

import java.util.Optional;
import life.bareun.diary.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByKey(String key);
}
