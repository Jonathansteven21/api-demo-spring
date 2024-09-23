package online.lcelectronics.api.services;

import online.demo.api.entities.Client;
import online.demo.api.entities.HistoricAppliance;
import online.demo.api.entities.Image;
import online.demo.api.entities.Order;
import online.demo.api.enums.OrderStatus;
import online.demo.api.exceptions.NotFoundException;
import online.demo.api.repositories.ClientRepository;
import online.demo.api.repositories.HistoricApplianceRepository;
import online.demo.api.repositories.ImageRepository;
import online.demo.api.repositories.OrderRepository;
import online.demo.api.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
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
     * Tests the getOrderByReferenceCode method of OrderService when the order exists.
     * Verifies that the correct order is returned.
     */
    @Test
    void getOrderByReferenceCode_existingReferenceCode() {
        Order order = new Order();
        when(orderRepository.findByReferenceCode("unique-reference-code")).thenReturn(Optional.of(order));

        Order result = orderService.getOrderByReferenceCode("unique-reference-code");
        assertEquals(order, result);
    }

    /**
     * Tests the getOrderByReferenceCode method of OrderService when the order does not exist.
     * Ensures that a NotFoundException is thrown.
     */
    @Test
    void getOrderByReferenceCode_nonExistingReferenceCode() {
        when(orderRepository.findByReferenceCode("non-existent-code")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.getOrderByReferenceCode("non-existent-code"));
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
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order result = orderService.saveOrder(order);
        assertNotNull(result);
        assertNotNull(result.getReferenceCode());
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

    /**
     * Tests the getLastFiveOrders method of OrderService.
     * Verifies that the last five orders are returned.
     */
    @Test
    void getLastFiveOrders() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdDate").descending());
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            orders.add(new Order());
        }
        Page<Order> orderPage = new PageImpl<>(orders, pageable, 5);

        when(orderRepository.findAll(pageable)).thenReturn(orderPage);

        // Act
        List<Order> result = orderService.getLastFiveOrders();

        // Assert
        assertEquals(5, result.size()); // Ensure that 5 orders are returned
        verify(orderRepository).findAll(pageable); // Verify that the repository method is called
    }

    /**
     * Tests the getOrdersByPageable method of OrderService when orders exist.
     * Verifies that the correct page of orders is returned.
     */
    @Test
    void getOrdersByPageable_existingOrders() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdDate").ascending());
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            orders.add(new Order());
        }
        Page<Order> orderPage = new PageImpl<>(orders, pageable, 10);

        when(orderRepository.findAll(pageable)).thenReturn(orderPage);

        // Act
        Page<Order> result = orderService.getOrdersByPageable(0, 10, "createdDate", "asc");

        // Assert
        assertEquals(10, result.getTotalElements()); // Ensure 10 orders are returned
        assertEquals(orders, result.getContent()); // Ensure the content matches
        verify(orderRepository).findAll(pageable); // Verify that the repository method is called
    }

    /**
     * Tests the getOrdersByPageable method of OrderService when no orders exist.
     * Ensures that a NotFoundException is thrown.
     */
    @Test
    void getOrdersByPageable_noOrdersFound() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdDate").ascending());
        Page<Order> emptyPage = Page.empty(pageable);

        when(orderRepository.findAll(pageable)).thenReturn(emptyPage);

        // Act & Assert
        assertThrows(NotFoundException.class, () -> orderService.getOrdersByPageable(0, 10, "createdDate", "asc"));
        verify(orderRepository).findAll(pageable); // Verify that the repository method is called
    }
}
