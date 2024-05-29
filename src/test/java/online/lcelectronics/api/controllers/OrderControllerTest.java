package online.lcelectronics.api.controllers;

import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.enums.OrderStatus;
import online.lcelectronics.api.services.OrderService;
import online.lcelectronics.api.util.ApiResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    /**
     * Tests the getAllOrders method of OrderController.
     * Verifies that all orders are retrieved successfully.
     */
    @Test
    void getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        when(orderService.getAllOrders()).thenReturn(orders);

        ResponseEntity<ApiResponse<List<Order>>> responseEntity = orderController.getAllOrders();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<List<Order>> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Orders retrieved successfully", response.getMessage());
        assertEquals(orders, response.getData());
    }

    /**
     * Tests the getOrderById method of OrderController with an existing order ID.
     * Verifies that the order with the given ID is retrieved successfully.
     */
    @Test
    void getOrderById() {
        Order order = new Order();
        when(orderService.getOrderById(1)).thenReturn(order);

        ResponseEntity<ApiResponse<Order>> responseEntity = orderController.getOrderById(1);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<Order> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Order retrieved successfully", response.getMessage());
        assertEquals(order, response.getData());
    }

    /**
     * Tests the getOrdersByCriteria method of OrderController with specified criteria.
     * Verifies that the orders matching the criteria are retrieved successfully.
     */
    @Test
    void getOrdersByCriteria() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        when(orderService.getOrdersByCriteria(any(Order.class))).thenReturn(orders);

        ResponseEntity<ApiResponse<List<Order>>> responseEntity = orderController.getOrdersByCriteria(null, null, null, null, null);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<List<Order>> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Orders found", response.getMessage());
        assertEquals(orders, response.getData());
    }

    /**
     * Tests the saveOrder method of OrderController with a valid order.
     * Verifies that the order is successfully saved.
     */
    @Test
    void saveOrder() {
        Order order = new Order();
        Order savedOrder = new Order();
        when(orderService.saveOrder(order)).thenReturn(savedOrder);

        ResponseEntity<ApiResponse<Order>> responseEntity = orderController.saveOrder(order);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ApiResponse<Order> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("Order created successfully", response.getMessage());
        assertEquals(savedOrder, response.getData());
    }

    /**
     * Tests the updateOrderStatus method of OrderController with an existing order ID and status.
     * Verifies that the order status is successfully updated.
     */
    @Test
    void updateOrderStatus() {
        Integer orderId = 1;
        OrderStatus status = OrderStatus.COMPLETED;
        Order updatedOrder = new Order();
        when(orderService.updateOrderStatus(orderId, status)).thenReturn(updatedOrder);

        ResponseEntity<ApiResponse<Order>> responseEntity = orderController.updateOrderStatus(orderId, status);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<Order> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Order status updated successfully", response.getMessage());
        assertEquals(updatedOrder, response.getData());
    }
}
