package online.lcelectronics.api.controllers;

import online.lcelectronics.api.entities.Client;
import online.lcelectronics.api.entities.HistoricAppliance;
import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.enums.OrderStatus;
import online.lcelectronics.api.services.OrderService;
import online.lcelectronics.api.util.ApiResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = new Order();
        order.setId(1);
        order.setClient(new Client());
        order.setIssue("Test issue");
        order.setProductReceivedNotes("Test product received notes");
        order.setHistoricAppliance(new HistoricAppliance());
        order.setStatus(OrderStatus.DELIVERED);
        order.setCreatedDate(LocalDate.now());
    }

    @Test
    void getAllOrders() {
        List<Order> orders = Arrays.asList(order);
        when(orderService.getAllOrders()).thenReturn(orders);

        ResponseEntity<ApiResponse<List<Order>>> responseEntity = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orders, responseEntity.getBody().getData());
    }

    @Test
    void getOrderById() {
        Integer orderId = 1;
        when(orderService.getOrderById(orderId)).thenReturn(order);

        ResponseEntity<ApiResponse<Order>> responseEntity = orderController.getOrderById(orderId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(order, responseEntity.getBody().getData());
    }

    @Test
    void saveOrder() {
        when(orderService.saveOrder(order)).thenReturn(order);

        ResponseEntity<ApiResponse<Order>> responseEntity = orderController.saveOrder(order);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(order, responseEntity.getBody().getData());
    }

    @Test
    void updateOrderStatus() {
        Integer orderId = 1;
        OrderStatus status = OrderStatus.COMPLETED;
        when(orderService.updateOrderStatus(orderId, status)).thenReturn(order);

        ResponseEntity<ApiResponse<Order>> responseEntity = orderController.updateOrderStatus(orderId, status);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(order, responseEntity.getBody().getData());
    }
}
