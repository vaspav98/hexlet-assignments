package exercise.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import exercise.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    List<Product> findByPriceBetweenOrderByPrice(int min, int max);

    /**
     * другой вариант, где сортировка идёт как параметр
     */
    List<Product> findByPriceBetween(int min, int max, Sort sort);
    // END
}
