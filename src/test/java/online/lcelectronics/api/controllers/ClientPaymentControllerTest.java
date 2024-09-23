package online.lcelectronics.api.controllers;

import online.demo.api.controllers.ClientPaymentController;
import online.demo.api.entities.ClientPayment;
import online.demo.api.entities.Order;
import online.demo.api.services.ClientPaymentService;
import online.demo.api.util.ApiResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

class ClientPaymentControllerTest {

    @Mock
    private ClientPaymentService clientPaymentService;

    @InjectMocks
    private ClientPaymentController clientPaymentController;

    private ClientPayment clientPayment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientPayment = new ClientPayment();
        clientPayment.setId(1);
        clientPayment.setOrder(new Order());
        clientPayment.setDate(LocalDate.now());
        clientPayment.setAmount(new BigDecimal("100.00"));
    }

    /**
     * Test for verifying the retrieval of all client payments.
     * It mocks the ClientPaymentService to return a predefined list of client payments and
     * verifies the response.
     */
    @Test
    void getAllClientPayments() {
        // Arrange
        List<ClientPayment> clientPayments = Arrays.asList(clientPayment);
        when(clientPaymentService.getAllClientPayments()).thenReturn(clientPayments);

        // Act
        ResponseEntity<ApiResponse<List<ClientPayment>>> responseEntity = clientPaymentController.getAllClientPayments();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clientPayments, responseEntity.getBody().getData());
    }

    /**
     * Test for verifying the retrieval of a client payment by its ID.
     * It mocks the ClientPaymentService to return a predefined client payment and
     * verifies the response.
     */
    @Test
    void getClientPaymentById() {
        // Arrange
        Integer clientPaymentId = 1;
        when(clientPaymentService.getClientPaymentById(clientPaymentId)).thenReturn(clientPayment);

        // Act
        ResponseEntity<ApiResponse<ClientPayment>> responseEntity = clientPaymentController.getClientPaymentById(clientPaymentId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clientPayment, responseEntity.getBody().getData());
    }

    /**
     * Test for verifying the retrieval of client payments by order ID.
     * It mocks the ClientPaymentService to return a predefined list of client payments and
     * verifies the response.
     */
    @Test
    void getClientPaymentsByOrder() {
        // Arrange
        Integer orderId = 1;
        List<ClientPayment> clientPayments = Arrays.asList(clientPayment);
        when(clientPaymentService.getClientPaymentsByOrder(any())).thenReturn(clientPayments);

        // Act
        ResponseEntity<ApiResponse<List<ClientPayment>>> responseEntity = clientPaymentController.getClientPaymentsByOrder(orderId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clientPayments, responseEntity.getBody().getData());
    }

    /**
     * Test for verifying the retrieval of client payments by date.
     * It mocks the ClientPaymentService to return a predefined list of client payments and
     * verifies the response.
     */
    @Test
    void getClientPaymentsByDate() {
        // Arrange
        LocalDate date = LocalDate.now();
        List<ClientPayment> clientPayments = Arrays.asList(clientPayment);
        when(clientPaymentService.getClientPaymentsByDate(date)).thenReturn(clientPayments);

        // Act
        ResponseEntity<ApiResponse<List<ClientPayment>>> responseEntity = clientPaymentController.getClientPaymentsByDate(date);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clientPayments, responseEntity.getBody().getData());
    }

    /**
     * Test for verifying the saving of a new client payment.
     * It mocks the ClientPaymentService to return the saved client payment and
     * verifies the response.
     */
    @Test
    void saveClientPayment() {
        // Arrange
        when(clientPaymentService.saveClientPayment(clientPayment)).thenReturn(clientPayment);

        // Act
        ResponseEntity<ApiResponse<ClientPayment>> responseEntity = clientPaymentController.saveClientPayment(clientPayment);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(clientPayment, responseEntity.getBody().getData());
    }

    /**
     * Test for verifying the update of an existing client payment.
     * It mocks the ClientPaymentService to return the updated client payment and
     * verifies the response.
     */
    @Test
    void updateClientPayment() {
        // Arrange
        Integer clientPaymentId = 1;
        when(clientPaymentService.updateClientPayment(clientPayment)).thenReturn(clientPayment);

        // Act
        ResponseEntity<ApiResponse<ClientPayment>> responseEntity = clientPaymentController.updateClientPayment(clientPaymentId, clientPayment);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clientPayment, responseEntity.getBody().getData());
    }
}
