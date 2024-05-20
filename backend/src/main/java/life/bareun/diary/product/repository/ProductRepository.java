package life.bareun.diary.product.repository;

import java.util.Optional;
import life.bareun.diary.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductKey(String productKey);
}
