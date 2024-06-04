package online.lcelectronics.api.services;

import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.entities.RepairCost;
import online.lcelectronics.api.exceptions.NotFoundException;
import online.lcelectronics.api.repositories.OrderRepository;
import online.lcelectronics.api.repositories.RepairCostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RepairCostServiceTest {

    @Mock
    private RepairCostRepository repairCostRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private RepairCostService repairCostService;

    private RepairCost repairCost;
    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        order = new Order();
        order.setId(1);

        repairCost = new RepairCost();
        repairCost.setId(1);
        repairCost.setOrder(order);
        repairCost.setDescription("Test repair cost");
        repairCost.setAmount(BigDecimal.valueOf(100));
    }

    /**
     * Tests the getAllRepairCosts method of RepairCostService.
     * Verifies that all repair costs are returned.
     */
    @Test
    void getAllRepairCosts() {
        List<RepairCost> repairCostList = new ArrayList<>();
        repairCostList.add(repairCost);

        when(repairCostRepository.findAll()).thenReturn(repairCostList);

        List<RepairCost> result = repairCostService.getAllRepairCosts();

        assertEquals(repairCostList, result);
    }

    /**
     * Tests the getRepairCostById method of RepairCostService when the repair cost exists.
     * Verifies that the correct repair cost is returned.
     */
    @Test
    void getRepairCostById_existingId() {
        when(repairCostRepository.findById(1)).thenReturn(Optional.of(repairCost));

        RepairCost result = repairCostService.getRepairCostById(1);

        assertEquals(repairCost, result);
    }

    /**
     * Tests the getRepairCostById method of RepairCostService when the repair cost does not exist.
     * Ensures that a NotFoundException is thrown.
     */
    @Test
    void getRepairCostById_nonExistingId() {
        when(repairCostRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> repairCostService.getRepairCostById(1));
    }

    /**
     * Tests the getRepairCostsByOrder method of RepairCostService when the order exists.
     * Verifies that the correct repair costs are returned.
     */
    @Test
    void getRepairCostsByOrder_existingOrder() {
        List<RepairCost> repairCostList = new ArrayList<>();
        repairCostList.add(repairCost);

        when(repairCostRepository.findByOrder(order)).thenReturn(repairCostList);

        List<RepairCost> result = repairCostService.getRepairCostsByOrder(order);

        assertEquals(repairCostList, result);
    }

    /**
     * Tests the getRepairCostsByOrder method of RepairCostService when the order does not exist.
     * Ensures that a NotFoundException is thrown.
     */
    @Test
    void getRepairCostsByOrder_nonExistingOrder() {
        when(repairCostRepository.findByOrder(order)).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> repairCostService.getRepairCostsByOrder(order));
    }

    /**
     * Tests the getRepairCostsByAmount method of RepairCostService when repair costs with the specified amount exist.
     * Verifies that the correct repair costs are returned.
     */
    @Test
    void getRepairCostsByAmount_existingAmount() {
        List<RepairCost> repairCostList = new ArrayList<>();
        repairCostList.add(repairCost);

        when(repairCostRepository.findByAmount(any(BigDecimal.class))).thenReturn(repairCostList);

        List<RepairCost> result = repairCostService.getRepairCostsByAmount(BigDecimal.valueOf(100));

        assertEquals(repairCostList, result);
    }

    /**
     * Tests the getRepairCostsByAmount method of RepairCostService when repair costs with the specified amount do not exist.
     * Ensures that a NotFoundException is thrown.
     */
    @Test
    void getRepairCostsByAmount_nonExistingAmount() {
        when(repairCostRepository.findByAmount(any(BigDecimal.class))).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> repairCostService.getRepairCostsByAmount(BigDecimal.valueOf(200)));
    }

    /**
     * Tests the saveRepairCost method of RepairCostService with a valid repair cost.
     * Verifies that the repair cost is successfully saved.
     */
    @Test
    void saveRepairCost_validRepairCost() {
        when(orderRepository.existsById(any(Integer.class))).thenReturn(true);
        when(repairCostRepository.save(any(RepairCost.class))).thenReturn(repairCost);

        RepairCost result = repairCostService.saveRepairCost(repairCost);

        assertEquals(repairCost, result);
    }

    /**
     * Tests the saveRepairCost method of RepairCostService with an invalid order.
     * Ensures that a NotFoundException is thrown.
     */
    @Test
    void saveRepairCost_invalidOrder() {
        when(orderRepository.existsById(any(Integer.class))).thenReturn(false);

        assertThrows(NotFoundException.class, () -> repairCostService.saveRepairCost(repairCost));
    }

    /**
     * Tests the updateRepairCost method of RepairCostService with an existing repair cost.
     * Verifies that the repair cost is successfully updated.
     */
    @Test
    void updateRepairCost_existingRepairCost() {
        when(repairCostRepository.existsById(1)).thenReturn(true);
        when(repairCostRepository.saveAndFlush(any(RepairCost.class))).thenReturn(repairCost);
        when(orderRepository.existsById(any(Integer.class))).thenReturn(true); // Mocking order existence

        RepairCost result = repairCostService.updateRepairCost(repairCost);

        assertEquals(repairCost, result);
    }

    /**
     * Tests the updateRepairCost method of RepairCostService with a non-existing repair cost.
     * Ensures that a NotFoundException is thrown.
     */
    @Test
    void updateRepairCost_nonExistingRepairCost() {
        when(repairCostRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> repairCostService.updateRepairCost(repairCost));
    }
}
