package online.lcelectronics.api.services;

import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.entities.OrderHistory;
import online.lcelectronics.api.exceptions.NotFoundException;
import online.lcelectronics.api.repositories.OrderHistoryRepository;
import online.lcelectronics.api.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderHistoryServiceTest {

    @Mock
    private OrderHistoryRepository orderHistoryRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderHistoryService orderHistoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the getAllOrderHistoryEntries method of OrderHistoryService.
     * Verifies that all order history entries are returned.
     */
    @Test
    void getAllOrderHistoryEntries() {
        List<OrderHistory> orderHistories = new ArrayList<>();
        when(orderHistoryRepository.findAll()).thenReturn(orderHistories);

        List<OrderHistory> result = orderHistoryService.getAllOrderHistoryEntries();
        assertEquals(orderHistories, result);
    }

    /**
     * Tests the getOrderHistoryEntryById method of OrderHistoryService when the entry exists.
     * Verifies that the correct order history entry is returned.
     */
    @Test
    void getOrderHistoryEntryById_existingId() {
        OrderHistory orderHistory = new OrderHistory();
        when(orderHistoryRepository.findById(1)).thenReturn(Optional.of(orderHistory));

        OrderHistory result = orderHistoryService.getOrderHistoryEntryById(1);
        assertEquals(orderHistory, result);
    }

    /**
     * Tests the getOrderHistoryEntryById method of OrderHistoryService when the entry does not exist.
     * Ensures that a NotFoundException is thrown.
     */
    @Test
    void getOrderHistoryEntryById_nonExistingId() {
        when(orderHistoryRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderHistoryService.getOrderHistoryEntryById(1));
    }

    /**
     * Tests the getOrderHistoryEntriesByOrder method of OrderHistoryService when order history exists for the order.
     * Verifies that the order history entries for the specified order are returned.
     */
    @Test
    void getOrderHistoryEntriesByOrder_existingOrderHistory() {
        Order order = new Order();
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOrder(order);

        when(orderHistoryRepository.findByOrder(order)).thenReturn(Collections.singletonList(orderHistory));

        List<OrderHistory> result = orderHistoryService.getOrderHistoryEntriesByOrder(order);

        assertFalse(result.isEmpty());
        assertTrue(result.contains(orderHistory));
    }

    /**
     * Tests the getOrderHistoryEntriesByOrder method of OrderHistoryService when no order history exists for the order.
     * Ensures that a NotFoundException is thrown.
     */
    @Test
    void getOrderHistoryEntriesByOrder_noOrderHistory() {
        Order order = new Order();

        when(orderHistoryRepository.findByOrder(order)).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> orderHistoryService.getOrderHistoryEntriesByOrder(order));
    }

    /**
     * Tests the saveOrderHistoryEntry method of OrderHistoryService with a valid order history entry.
     * Verifies that the order history entry is successfully saved.
     */
    @Test
    void saveOrderHistoryEntry() {
        Order order = new Order();
        order.setId(1);

        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOrder(order);

        when(orderRepository.existsById(any(Integer.class))).thenReturn(true);
        when(orderHistoryRepository.save(orderHistory)).thenReturn(orderHistory);

        OrderHistory result = orderHistoryService.saveOrderHistoryEntry(orderHistory);

        assertEquals(orderHistory, result);
    }

    /**
     * Tests the saveOrderHistoryEntry method of OrderHistoryService with an existing order.
     * Verifies that the order history entry is successfully updated.
     */
    @Test
    void saveOrderHistoryEntry_existingOrder() {
        Order order = new Order();
        order.setId(1);

        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOrder(order);
        orderHistory.setId(1);
        when(orderRepository.existsById(any(Integer.class))).thenReturn(true);
        when(orderHistoryRepository.existsById(1)).thenReturn(true);
        when(orderHistoryRepository.saveAndFlush(orderHistory)).thenReturn(orderHistory);

        OrderHistory result = orderHistoryService.updateOrderHistoryEntry(orderHistory);
        assertEquals(orderHistory, result);
    }

    /**
     * Tests the updateOrderHistoryEntry method of OrderHistoryService with a non-existing order history entry.
     * Ensures that a NotFoundException is thrown.
     */
    @Test
    void updateOrderHistoryEntry_nonExistingEntry() {
        Order order = new Order();
        order.setId(1);

        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOrder(order);
        when(orderRepository.existsById(any(Integer.class))).thenReturn(true);
        when(orderHistoryRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> orderHistoryService.updateOrderHistoryEntry(orderHistory));
    }

}
