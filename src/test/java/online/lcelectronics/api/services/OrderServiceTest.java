package online.lcelectronics.api.services;

import online.lcelectronics.api.entities.Client;
import online.lcelectronics.api.entities.HistoricAppliance;
import online.lcelectronics.api.entities.Image;
import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.enums.OrderStatus;
import online.lcelectronics.api.exceptions.NotFoundException;
import online.lcelectronics.api.repositories.ClientRepository;
import online.lcelectronics.api.repositories.HistoricApplianceRepository;
import online.lcelectronics.api.repositories.ImageRepository;
import online.lcelectronics.api.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private HistoricApplianceRepository historicApplianceRepository;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the getAllOrders method of OrderService.
     * Verifies that all orders are returned.
     */
    @Test
    void getAllOrders() {
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();
        assertEquals(orders, result);
    }

    /**
     * Tests the getOrderById method of OrderService when the order exists.
     * Verifies that the correct order is returned.
     */
    @Test
    void getOrderById_existingId() {
        Order order = new Order();
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(1);
        assertEquals(order, result);
    }

    /**
     * Tests the getOrderById method of OrderService when the order does not exist.
     * Ensures that a NotFoundException is thrown.
     */
    @Test
    void getOrderById_nonExistingId() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.getOrderById(1));
    }

    /**
     * Tests the getOrdersByCriteria method of OrderService.
     * Verifies that orders matching the criteria are returned.
     */
    @Test
    void getOrdersByCriteria() {
        Order order = new Order();
        order.setStatus(OrderStatus.COMPLETED);
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        when(orderRepository.findAll(any(Specification.class))).thenReturn(orders);

        List<Order> result = orderService.getOrdersByCriteria(order);
        assertEquals(orders, result);
    }

    /**
     * Tests the saveOrder method of OrderService with a valid order.
     * Verifies that the order is successfully saved.
     */
    @Test
    void saveOrder_validOrder() {
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.saveOrder(order);
        assertNotNull(result);
    }

    /**
     * Tests the saveOrder method of OrderService with an invalid client.
     * Ensures that a NotFoundException is thrown.
     */
    @Test
    void saveOrder_invalidClient() {
        Order order = new Order();
        order.setClient(new Client()); // Invalid client

        assertThrows(NotFoundException.class, () -> orderService.saveOrder(order));
    }

    /**
     * Tests the saveOrder method of OrderService with an invalid historic appliance.
     * Ensures that a NotFoundException is thrown.
     */
    @Test
    void saveOrder_invalidHistoricAppliance() {
        Order order = new Order();
        order.setHistoricAppliance(new HistoricAppliance()); // Invalid historic appliance

        assertThrows(NotFoundException.class, () -> orderService.saveOrder(order));
    }

    /**
     * Tests the saveOrder method of OrderService with an invalid image.
     * Ensures that a NotFoundException is thrown.
     */
    @Test
    void saveOrder_invalidImage() {
        Order order = new Order();
        List<Image> images = new ArrayList<>();
        images.add(new Image()); // Invalid image
        order.setImages(images);

        assertThrows(NotFoundException.class, () -> orderService.saveOrder(order));
    }

    /**
     * Tests the updateOrderStatus method of OrderService with an existing order.
     * Verifies that the order status is successfully updated.
     */
    @Test
    void updateOrderStatus_validOrder() {
        Integer orderId = 1;
        OrderStatus newStatus = OrderStatus.DELIVERED;

        Order order = new Order();
        order.setId(orderId);
        order.setStatus(OrderStatus.DIAGNOSED);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.saveAndFlush(order)).thenReturn(order);

        Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);

        assertNotNull(updatedOrder);
        assertEquals(newStatus, updatedOrder.getStatus());
        verify(orderRepository).saveAndFlush(order);
    }

    /**
     * Tests the updateOrderStatus method of OrderService with a non-existing order.
     * Ensures that a NotFoundException is thrown.
     */
    @Test
    void updateOrderStatus_nonExistingOrder() {
        Integer orderId = 1;
        OrderStatus newStatus = OrderStatus.DELIVERED;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> orderService.updateOrderStatus(orderId, newStatus));

        assertEquals("Order not found with ID: " + orderId, exception.getMessage());
        verify(orderRepository, never()).saveAndFlush(any(Order.class));
    }
}
