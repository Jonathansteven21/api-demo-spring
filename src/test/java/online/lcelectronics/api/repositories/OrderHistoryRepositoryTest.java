package online.lcelectronics.api.repositories;

import online.demo.api.entities.Order;
import online.demo.api.entities.OrderHistory;
import online.demo.api.repositories.OrderHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class OrderHistoryRepositoryTest {

    @Mock
    private OrderHistoryRepository orderHistoryRepository;

    @InjectMocks
    private OrderHistory orderHistory;

    @InjectMocks
    private Order order;

    @BeforeEach
    void setUp() {
        orderHistory = new OrderHistory();
        order = new Order();
        orderHistory.setOrder(order);
    }

    /**
     * Tests the findByOrder method of OrderHistoryRepository.
     * Verifies that the correct OrderHistories are returned when searched by order.
     */
    @Test
    void whenFindByOrder_thenReturnOrderHistories() {
        when(orderHistoryRepository.findByOrder(order))
                .thenReturn(Collections.singletonList(orderHistory));

        List<OrderHistory> foundHistories = orderHistoryRepository.findByOrder(order);

        assertFalse(foundHistories.isEmpty());
        assertEquals(orderHistory.getOrder(), foundHistories.get(0).getOrder());
    }

    /**
     * Tests the findByOrder method of OrderHistoryRepository when the order is not found.
     * Ensures that an empty list is returned when no order histories are found for the given order.
     */
    @Test
    void whenOrderNotFound_thenReturnEmptyList() {
        Order nonExistentOrder = new Order();
        when(orderHistoryRepository.findByOrder(nonExistentOrder))
                .thenReturn(Collections.emptyList());

        List<OrderHistory> foundHistories = orderHistoryRepository.findByOrder(nonExistentOrder);

        assertTrue(foundHistories.isEmpty());
    }
}
