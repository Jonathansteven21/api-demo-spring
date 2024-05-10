package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.entities.RepairCost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

// This interface defines methods to access RepairCost entities in the database
public interface RepairCostRepository extends JpaRepository<RepairCost, Integer> {

    // Finds repair costs by the associated order
    Optional<RepairCost> findByOrder(Order order);

    // Finds repair costs by the amount
    Optional<RepairCost> findByAmount(BigDecimal amount);
}
