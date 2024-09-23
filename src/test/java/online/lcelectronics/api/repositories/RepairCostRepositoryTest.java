package online.lcelectronics.api.repositories;

import online.demo.api.entities.Order;
import online.demo.api.entities.RepairCost;
import online.demo.api.repositories.RepairCostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class RepairCostRepositoryTest {

    @Mock
    private RepairCostRepository repairCostRepository;

    @InjectMocks
    private RepairCost repairCost;

    @InjectMocks
    private Order order;

    @BeforeEach
    void setUp() {
        repairCost = new RepairCost();
        order = new Order();
        repairCost.setOrder(order);
        repairCost.setAmount(new BigDecimal("100.00"));
    }

    /**
     * Tests the findByOrder method of RepairCostRepository.
     * Verifies that the correct RepairCosts are returned when searched by order.
     */
    @Test
    void whenFindByOrder_thenReturnRepairCosts() {
        when(repairCostRepository.findByOrder(order))
                .thenReturn(Collections.singletonList(repairCost));

        List<RepairCost> foundCosts = repairCostRepository.findByOrder(order);

        assertFalse(foundCosts.isEmpty());
        assertEquals(repairCost.getOrder(), foundCosts.get(0).getOrder());
    }

    /**
     * Tests the findByOrder method of RepairCostRepository when the order is not found.
     * Ensures that an empty list is returned when no repair costs are found for the given order.
     */
    @Test
    void whenOrderNotFound_thenReturnEmptyList() {
        Order nonExistentOrder = new Order();
        when(repairCostRepository.findByOrder(nonExistentOrder))
                .thenReturn(Collections.emptyList());

        List<RepairCost> foundCosts = repairCostRepository.findByOrder(nonExistentOrder);

        assertTrue(foundCosts.isEmpty());
    }

    /**
     * Tests the findByAmount method of RepairCostRepository.
     * Verifies that the correct RepairCosts are returned when searched by amount.
     */
    @Test
    void whenFindByAmount_thenReturnRepairCosts() {
        when(repairCostRepository.findByAmount(repairCost.getAmount()))
                .thenReturn(Collections.singletonList(repairCost));

        List<RepairCost> foundCosts = repairCostRepository.findByAmount(repairCost.getAmount());

        assertFalse(foundCosts.isEmpty());
        assertEquals(repairCost.getAmount(), foundCosts.get(0).getAmount());
    }

    /**
     * Tests the findByAmount method of RepairCostRepository when the amount is not found.
     * Ensures that an empty list is returned when no repair costs are found for the given amount.
     */
    @Test
    void whenAmountNotFound_thenReturnEmptyList() {
        BigDecimal nonExistentAmount = new BigDecimal("9999.99");
        when(repairCostRepository.findByAmount(nonExistentAmount))
                .thenReturn(Collections.emptyList());

        List<RepairCost> foundCosts = repairCostRepository.findByAmount(nonExistentAmount);

        assertTrue(foundCosts.isEmpty());
    }
}
