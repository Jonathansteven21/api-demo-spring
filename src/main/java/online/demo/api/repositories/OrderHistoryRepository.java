package online.demo.api.repositories;

import online.demo.api.entities.Order;
import online.demo.api.entities.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// This interface defines methods to access OrderHistory entities in the database
@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Integer> {

    // Finds order history entries by the associated order
    List<OrderHistory> findByOrder(Order order);
}
