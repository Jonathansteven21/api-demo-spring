package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.entities.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// This interface defines methods to access OrderHistory entities in the database
@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Integer> {

    // Finds order history entries by the associated order
    List<OrderHistory> findByOrder(Order order);
}
