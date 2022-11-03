package in.org.practice.multidb.repository.sql;

import in.org.practice.multidb.entity.sql.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
