package online.lcelectronics.api.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.entities.RepairCost;
import online.lcelectronics.api.exceptions.NotFoundException;
import online.lcelectronics.api.repositories.OrderRepository;
import online.lcelectronics.api.repositories.RepairCostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RepairCostService {

    private final RepairCostRepository repairCostRepository;
    private final OrderRepository orderRepository;

    // Retrieve all repair costs
    public List<RepairCost> getAllRepairCosts() {
        return repairCostRepository.findAll();
    }

    // Retrieve a repair cost by its ID
    public RepairCost getRepairCostById(Integer id) {
        return repairCostRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Repair cost not found with ID: " + id));
    }

    // Retrieve repair costs by the associated order
    public List<RepairCost> getRepairCostsByOrder(Order order) {
        List<RepairCost> repairCostList= repairCostRepository.findByOrder(order);
        if (repairCostList.isEmpty()) {
            throw new NotFoundException("Repair cost not found with order ID: "+ order.getId());
        }
        return repairCostList;

    }

    // Retrieve repair costs by the amount
    public List<RepairCost> getRepairCostsByAmount(
            @Positive(message = "amount must be greater than zero") BigDecimal amount) {
        List<RepairCost> repairCostList= repairCostRepository.findByAmount(amount);
        if (repairCostList.isEmpty()) {
            throw new NotFoundException("Repair cost not found with the amount: "+ amount);
        }
        return repairCostList;
    }

    // Save a repair cost
    @Transactional
    public RepairCost saveRepairCost(RepairCost repairCost) {
        validateRepairCost(repairCost);
        return repairCostRepository.save(repairCost);
    }

    // Update a repair cost
    @Transactional
    public RepairCost updateRepairCost(RepairCost repairCost) {
        if (!repairCostRepository.existsById(repairCost.getId())) {
            throw new NotFoundException("Repair cost not found with ID: " + repairCost.getId());
        }

        validateRepairCost(repairCost);
        return repairCostRepository.saveAndFlush(repairCost);
    }

    // Private method to validate repair cost
    private void validateRepairCost(RepairCost repairCost) {
        if (repairCost.getOrder() != null && !orderRepository.existsById(repairCost.getOrder().getId())) {
            throw new NotFoundException("Order not found with ID: " + repairCost.getOrder().getId());
        }
    }
}
