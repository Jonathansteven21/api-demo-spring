package online.lcelectronics.api.controllers;

import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.entities.OrderHistory;
import online.lcelectronics.api.services.OrderHistoryService;
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
class OrderHistoryControllerTest {

    @Mock
    private OrderHistoryService orderHistoryService;

    @InjectMocks
    private OrderHistoryController orderHistoryController;

    @Test
    void getAllOrderHistoryEntries() {
        List<OrderHistory> orderHistories = new ArrayList<>();
        orderHistories.add(new OrderHistory());
        when(orderHistoryService.getAllOrderHistoryEntries()).thenReturn(orderHistories);

        ResponseEntity<ApiResponse<List<OrderHistory>>> responseEntity = orderHistoryController.getAllOrderHistoryEntries();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<List<OrderHistory>> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Order histories retrieved successfully", response.getMessage());
        assertEquals(orderHistories, response.getData());
    }

    @Test
    void getOrderHistoryEntryById() {
        OrderHistory orderHistory = new OrderHistory();
        when(orderHistoryService.getOrderHistoryEntryById(1)).thenReturn(orderHistory);

        ResponseEntity<ApiResponse<OrderHistory>> responseEntity = orderHistoryController.getOrderHistoryEntryById(1);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<OrderHistory> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Order history retrieved successfully", response.getMessage());
        assertEquals(orderHistory, response.getData());
    }

    @Test
    void getOrderHistoryEntriesByOrder() {
        List<OrderHistory> orderHistories = new ArrayList<>();
        orderHistories.add(new OrderHistory());
        when(orderHistoryService.getOrderHistoryEntriesByOrder(any(Order.class))).thenReturn(orderHistories);

        ResponseEntity<ApiResponse<List<OrderHistory>>> responseEntity = orderHistoryController.getOrderHistoryEntriesByOrder(1);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<List<OrderHistory>> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Order histories retrieved successfully for order ID: 1", response.getMessage());
        assertEquals(orderHistories, response.getData());
    }

    @Test
    void saveOrderHistoryEntry() {
        OrderHistory orderHistory = new OrderHistory();
        OrderHistory savedOrderHistory = new OrderHistory();
        when(orderHistoryService.saveOrderHistoryEntry(orderHistory)).thenReturn(savedOrderHistory);

        ResponseEntity<ApiResponse<OrderHistory>> responseEntity = orderHistoryController.saveOrderHistoryEntry(orderHistory);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ApiResponse<OrderHistory> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("Order history entry created successfully", response.getMessage());
        assertEquals(savedOrderHistory, response.getData());
    }

    @Test
    void updateOrderHistoryEntry() {
        Integer orderHistoryId = 1;
        OrderHistory orderHistory = new OrderHistory();
        OrderHistory updatedOrderHistory = new OrderHistory();
        when(orderHistoryService.updateOrderHistoryEntry(orderHistory)).thenReturn(updatedOrderHistory);

        ResponseEntity<ApiResponse<OrderHistory>> responseEntity = orderHistoryController.updateOrderHistoryEntry(orderHistoryId, orderHistory);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<OrderHistory> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Order history entry updated successfully", response.getMessage());
        assertEquals(updatedOrderHistory, response.getData());
    }
}
