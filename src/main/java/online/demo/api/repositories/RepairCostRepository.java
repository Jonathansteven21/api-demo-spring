package online.demo.api.repositories;

import online.demo.api.entities.Order;
import online.demo.api.entities.RepairCost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;
import java.util.List;

// This interface defines methods to access RepairCost entities in the database
public interface RepairCostRepository extends JpaRepository<RepairCost, Integer> {

    // Finds repair costs by the associated order
    List<RepairCost> findByOrder(Order order);

    // Finds repair costs by the amount
    List<RepairCost> findByAmount(BigDecimal amount);
}
