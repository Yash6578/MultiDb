package in.org.practice.multidb.repository.postgres;

import in.org.practice.multidb.entity.postgres.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
