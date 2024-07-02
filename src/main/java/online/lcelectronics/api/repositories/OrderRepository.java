package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


// This interface defines methods to access Order entities in the database
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    // Find an order by reference code
    Optional<Order> findByReferenceCode(String referenceCode);
}
