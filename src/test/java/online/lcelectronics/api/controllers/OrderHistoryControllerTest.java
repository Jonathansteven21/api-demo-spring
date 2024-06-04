package online.lcelectronics.api.controllers;

import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.entities.OrderHistory;
import online.lcelectronics.api.services.OrderHistoryService;
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

class OrderHistoryControllerTest {

    @Mock
    private OrderHistoryService orderHistoryService;

    @InjectMocks
    private OrderHistoryController orderHistoryController;

    private OrderHistory orderHistory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderHistory = new OrderHistory();
        orderHistory.setId(1);
        orderHistory.setOrder(new Order());
        orderHistory.setEventDate(LocalDate.now());
        orderHistory.setText("Test order history");
    }

    @Test
    void getAllOrderHistoryEntries() {
        List<OrderHistory> orderHistories = Arrays.asList(orderHistory);
        when(orderHistoryService.getAllOrderHistoryEntries()).thenReturn(orderHistories);

        ResponseEntity<ApiResponse<List<OrderHistory>>> responseEntity = orderHistoryController.getAllOrderHistoryEntries();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orderHistories, responseEntity.getBody().getData());
    }

    @Test
    void getOrderHistoryEntryById() {
        Integer orderHistoryId = 1;
        when(orderHistoryService.getOrderHistoryEntryById(orderHistoryId)).thenReturn(orderHistory);

        ResponseEntity<ApiResponse<OrderHistory>> responseEntity = orderHistoryController.getOrderHistoryEntryById(orderHistoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orderHistory, responseEntity.getBody().getData());
    }

    @Test
    void saveOrderHistoryEntry() {
        when(orderHistoryService.saveOrderHistoryEntry(orderHistory)).thenReturn(orderHistory);

        ResponseEntity<ApiResponse<OrderHistory>> responseEntity = orderHistoryController.saveOrderHistoryEntry(orderHistory);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(orderHistory, responseEntity.getBody().getData());
    }

    @Test
    void updateOrderHistoryEntry() {
        Integer orderHistoryId = 1;
        when(orderHistoryService.updateOrderHistoryEntry(any(OrderHistory.class))).thenReturn(orderHistory);

        ResponseEntity<ApiResponse<OrderHistory>> responseEntity = orderHistoryController.updateOrderHistoryEntry(orderHistoryId, orderHistory);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orderHistory, responseEntity.getBody().getData());
    }
}

