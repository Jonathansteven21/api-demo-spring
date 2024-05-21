package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.entities.RepairCost;
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

    @Test
    void whenFindByOrder_thenReturnRepairCosts() {
        when(repairCostRepository.findByOrder(order))
                .thenReturn(Collections.singletonList(repairCost));

        List<RepairCost> foundCosts = repairCostRepository.findByOrder(order);

        assertFalse(foundCosts.isEmpty());
        assertEquals(repairCost.getOrder(), foundCosts.get(0).getOrder());
    }

    @Test
    void whenOrderNotFound_thenReturnEmptyList() {
        Order nonExistentOrder = new Order();
        when(repairCostRepository.findByOrder(nonExistentOrder))
                .thenReturn(Collections.emptyList());

        List<RepairCost> foundCosts = repairCostRepository.findByOrder(nonExistentOrder);

        assertTrue(foundCosts.isEmpty());
    }

    @Test
    void whenFindByAmount_thenReturnRepairCosts() {
        when(repairCostRepository.findByAmount(repairCost.getAmount()))
                .thenReturn(Collections.singletonList(repairCost));

        List<RepairCost> foundCosts = repairCostRepository.findByAmount(repairCost.getAmount());

        assertFalse(foundCosts.isEmpty());
        assertEquals(repairCost.getAmount(), foundCosts.get(0).getAmount());
    }

    @Test
    void whenAmountNotFound_thenReturnEmptyList() {
        BigDecimal nonExistentAmount = new BigDecimal("9999.99");
        when(repairCostRepository.findByAmount(nonExistentAmount))
                .thenReturn(Collections.emptyList());

        List<RepairCost> foundCosts = repairCostRepository.findByAmount(nonExistentAmount);

        assertTrue(foundCosts.isEmpty());
    }
}
