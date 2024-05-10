package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.entities.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// This interface defines methods to access OrderHistory entities in the database
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Integer> {

    // Finds order history entries by the associated order
    Optional<OrderHistory> findByOrder(Order order);
}
