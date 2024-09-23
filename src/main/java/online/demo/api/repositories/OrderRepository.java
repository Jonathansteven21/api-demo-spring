package online.demo.api.repositories;

import lombok.NonNull;
import online.demo.api.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


// This interface defines methods to access Order entities in the database
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    // Find an order by reference code
    Optional<Order> findByReferenceCode(String referenceCode);

    // Find an order by pageable
    @NonNull
    Page<Order> findAll(Pageable pageable);

}
