package online.lcelectronics.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.entities.RepairCost;
import online.lcelectronics.api.repositories.RepairCostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RepairCostService {

    private final RepairCostRepository repairCostRepository;

    // Retrieve all repair costs
    public List<RepairCost> getAllRepairCosts() {
        return repairCostRepository.findAll();
    }

    // Retrieve a repair cost by its ID
    public RepairCost getRepairCostById(Integer id) {
        Optional<RepairCost> optionalRepairCost = repairCostRepository.findById(id);
        return optionalRepairCost.orElse(null);
    }

    // Retrieve repair costs by the associated order
    public List<RepairCost> getRepairCostsByOrder(Order order) {
        return repairCostRepository.findByOrder(order);
    }

    // Retrieve repair costs by the amount
    public List<RepairCost> getRepairCostsByAmount(BigDecimal amount) {
        return repairCostRepository.findByAmount(amount);
    }

    // Save a repair cost
    @Transactional
    public RepairCost saveRepairCost(RepairCost repairCost) {
        return repairCostRepository.save(repairCost);
    }

    // Update a repair cost
    @Transactional
    public RepairCost updateRepairCost(RepairCost repairCost) {
        return repairCostRepository.saveAndFlush(repairCost);
    }
}
