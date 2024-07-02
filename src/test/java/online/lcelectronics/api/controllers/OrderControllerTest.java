package online.lcelectronics.api.controllers;

import online.lcelectronics.api.converters.ClientConverter;
import online.lcelectronics.api.converters.OrderConverter;
import online.lcelectronics.api.dto.ClientDTO;
import online.lcelectronics.api.dto.OrderDTO;
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

    @Mock
    private OrderConverter orderConverter;

    @Mock
    private ClientConverter clientConverter;

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

    /**
     * Test for verifying the retrieval of all orders.
     * It mocks the OrderService to return a predefined list of orders and
     * verifies the response from the controller.
     */
    @Test
    void getAllOrders() {
        // Arrange
        List<Order> orders = Arrays.asList(order);
        when(orderService.getAllOrders()).thenReturn(orders);

        // Act
        ResponseEntity<ApiResponse<List<Order>>> responseEntity = orderController.getAllOrders();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orders, responseEntity.getBody().getData());
    }

    /**
     * Test for verifying the retrieval of an order by its ID.
     * It mocks the OrderService to return a predefined order and
     * verifies the response from the controller.
     */
    @Test
    void getOrderById() {
        // Arrange
        Integer orderId = 1;
        when(orderService.getOrderById(orderId)).thenReturn(order);

        // Act
        ResponseEntity<ApiResponse<Order>> responseEntity = orderController.getOrderById(orderId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(order, responseEntity.getBody().getData());
    }

    /**
     * Test for verifying the retrieval of an order by its reference code.
     * It mocks the OrderService to return a predefined order and
     * verifies the response from the controller.
     */
    @Test
    void getOrderByReferenceCode() {
        // Arrange
        String referenceCode = "ref123";

        // Mock the conversion process
        when(orderService.getOrderByReferenceCode(referenceCode)).thenReturn(order);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        // Optionally mock client conversion
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setIdentityCard(order.getClient().getIdentityCard());
        clientDTO.setName(order.getClient().getName());
        when(clientConverter.toDto(order.getClient())).thenReturn(clientDTO);
        orderDTO.setClient(clientDTO);

        when(orderConverter.toDto(order)).thenReturn(orderDTO);

        // Act
        ResponseEntity<ApiResponse<OrderDTO>> responseEntity = orderController.getOrderByReferenceCode(referenceCode);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orderDTO, responseEntity.getBody().getData());
    }

    /**
     * Test for verifying the saving of an order.
     * It mocks the OrderService to return the saved order and
     * verifies the response from the controller.
     */
    @Test
    void saveOrder() {
        // Arrange
        when(orderService.saveOrder(order)).thenReturn(order);

        // Act
        ResponseEntity<ApiResponse<Order>> responseEntity = orderController.saveOrder(order);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(order, responseEntity.getBody().getData());
    }

    /**
     * Test for verifying the update of an order's status.
     * It mocks the OrderService to return the updated order and
     * verifies the response from the controller.
     */
    @Test
    void updateOrderStatus() {
        // Arrange
        Integer orderId = 1;
        OrderStatus status = OrderStatus.COMPLETED;
        when(orderService.updateOrderStatus(orderId, status)).thenReturn(order);

        // Act
        ResponseEntity<ApiResponse<Order>> responseEntity = orderController.updateOrderStatus(orderId, status);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(order, responseEntity.getBody().getData());
    }
}
