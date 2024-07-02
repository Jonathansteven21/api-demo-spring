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

    /**
     * Test for verifying the retrieval of all order history entries.
     * It mocks the OrderHistoryService to return a predefined list of order history entries and
     * verifies the response.
     */
    @Test
    void getAllOrderHistoryEntries() {
        // Arrange
        List<OrderHistory> orderHistories = Arrays.asList(orderHistory);
        when(orderHistoryService.getAllOrderHistoryEntries()).thenReturn(orderHistories);

        // Act
        ResponseEntity<ApiResponse<List<OrderHistory>>> responseEntity = orderHistoryController.getAllOrderHistoryEntries();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orderHistories, responseEntity.getBody().getData());
    }

    /**
     * Test for verifying the retrieval of an order history entry by its ID.
     * It mocks the OrderHistoryService to return a predefined order history entry and
     * verifies the response.
     */
    @Test
    void getOrderHistoryEntryById() {
        // Arrange
        Integer orderHistoryId = 1;
        when(orderHistoryService.getOrderHistoryEntryById(orderHistoryId)).thenReturn(orderHistory);

        // Act
        ResponseEntity<ApiResponse<OrderHistory>> responseEntity = orderHistoryController.getOrderHistoryEntryById(orderHistoryId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orderHistory, responseEntity.getBody().getData());
    }

    /**
     * Test for verifying the saving of a new order history entry.
     * It mocks the OrderHistoryService to return the saved order history entry and
     * verifies the response.
     */
    @Test
    void saveOrderHistoryEntry() {
        // Arrange
        when(orderHistoryService.saveOrderHistoryEntry(orderHistory)).thenReturn(orderHistory);

        // Act
        ResponseEntity<ApiResponse<OrderHistory>> responseEntity = orderHistoryController.saveOrderHistoryEntry(orderHistory);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(orderHistory, responseEntity.getBody().getData());
    }

    /**
     * Test for verifying the update of an existing order history entry.
     * It mocks the OrderHistoryService to return the updated order history entry and
     * verifies the response.
     */
    @Test
    void updateOrderHistoryEntry() {
        // Arrange
        Integer orderHistoryId = 1;
        when(orderHistoryService.updateOrderHistoryEntry(any(OrderHistory.class))).thenReturn(orderHistory);

        // Act
        ResponseEntity<ApiResponse<OrderHistory>> responseEntity = orderHistoryController.updateOrderHistoryEntry(orderHistoryId, orderHistory);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orderHistory, responseEntity.getBody().getData());
    }
}
