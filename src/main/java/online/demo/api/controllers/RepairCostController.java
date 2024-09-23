package online.demo.api.controllers;

import lombok.RequiredArgsConstructor;
import online.demo.api.entities.Order;
import online.demo.api.entities.RepairCost;
import online.demo.api.util.ApiResponse;
import online.demo.api.services.RepairCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/repair-costs")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RepairCostController {

    // RepairCostService dependency to handle business logic
    private final RepairCostService repairCostService;

    // Retrieve all repair costs
    @GetMapping
    public ResponseEntity<ApiResponse<List<RepairCost>>> getAllRepairCosts() {
        List<RepairCost> repairCosts = repairCostService.getAllRepairCosts();
        ApiResponse<List<RepairCost>> response = new ApiResponse<>(HttpStatus.OK.value(), "All repair costs retrieved successfully", repairCosts);
        return ResponseEntity.ok(response);
    }

    // Retrieve a repair cost by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RepairCost>> getRepairCostById(@PathVariable @NotNull(message = "ID cannot be null") Integer id) {
        RepairCost repairCost = repairCostService.getRepairCostById(id);
        ApiResponse<RepairCost> response = new ApiResponse<>(HttpStatus.OK.value(), "Repair cost retrieved successfully", repairCost);
        return ResponseEntity.ok(response);
    }

    // Retrieve repair costs by associated order
    @GetMapping("/order")
    public ResponseEntity<ApiResponse<List<RepairCost>>> getRepairCostsByOrder(@RequestParam @NotNull(message = "Order ID cannot be null") Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        List<RepairCost> repairCosts = repairCostService.getRepairCostsByOrder(order);
        ApiResponse<List<RepairCost>> response = new ApiResponse<>(HttpStatus.OK.value(), "Repair costs retrieved successfully for order ID: " + orderId, repairCosts);
        return ResponseEntity.ok(response);
    }

    // Retrieve repair costs by amount
    @GetMapping("/amount")
    public ResponseEntity<ApiResponse<List<RepairCost>>> getRepairCostsByAmount(@RequestParam @Positive(message = "Amount must be greater than zero") BigDecimal amount) {
        List<RepairCost> repairCosts = repairCostService.getRepairCostsByAmount(amount);
        ApiResponse<List<RepairCost>> response = new ApiResponse<>(HttpStatus.OK.value(), "Repair costs retrieved successfully for amount: " + amount, repairCosts);
        return ResponseEntity.ok(response);
    }

    // Save a new repair cost
    @PostMapping
    public ResponseEntity<ApiResponse<RepairCost>> saveRepairCost(@Valid @RequestBody RepairCost repairCost) {
        repairCost.setId(null);
        RepairCost savedRepairCost = repairCostService.saveRepairCost(repairCost);
        ApiResponse<RepairCost> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Repair cost saved successfully", savedRepairCost);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update an existing repair cost
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RepairCost>> updateRepairCost(@PathVariable @NotNull(message = "ID cannot be null") Integer id, @Valid @RequestBody RepairCost repairCost) {
        repairCost.setId(id);
        RepairCost updatedRepairCost = repairCostService.updateRepairCost(repairCost);
        ApiResponse<RepairCost> response = new ApiResponse<>(HttpStatus.OK.value(), "Repair cost updated successfully", updatedRepairCost);
        return ResponseEntity.ok(response);
    }
}
