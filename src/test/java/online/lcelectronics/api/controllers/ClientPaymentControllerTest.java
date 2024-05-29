package online.lcelectronics.api.controllers;

import online.lcelectronics.api.entities.ClientPayment;
import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.services.ClientPaymentService;
import online.lcelectronics.api.util.ApiResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClientPaymentControllerTest {

    @Mock
    private ClientPaymentService clientPaymentService;

    @InjectMocks
    private ClientPaymentController clientPaymentController;

    /**
     * Tests the getAllClientPayments method of ClientPaymentController.
     * Verifies that the response contains the expected data and status.
     */
    @Test
    void getAllClientPayments() {
        List<ClientPayment> clientPayments = new ArrayList<>();
        clientPayments.add(new ClientPayment());
        when(clientPaymentService.getAllClientPayments()).thenReturn(clientPayments);

        ResponseEntity<ApiResponse<List<ClientPayment>>> responseEntity = clientPaymentController.getAllClientPayments();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<List<ClientPayment>> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Client payments retrieved successfully", response.getMessage());
        assertEquals(clientPayments, response.getData());
    }

    /**
     * Tests the getClientPaymentById method of ClientPaymentController.
     * Verifies that the response contains the expected data and status.
     */
    @Test
    void getClientPaymentById() {
        ClientPayment clientPayment = new ClientPayment();
        when(clientPaymentService.getClientPaymentById(1)).thenReturn(clientPayment);

        ResponseEntity<ApiResponse<ClientPayment>> responseEntity = clientPaymentController.getClientPaymentById(1);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<ClientPayment> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Client payment retrieved successfully", response.getMessage());
        assertEquals(clientPayment, response.getData());
    }

    /**
     * Tests the getClientPaymentsByOrder method of ClientPaymentController.
     * Verifies that the response contains the expected data and status.
     */
    @Test
    void getClientPaymentsByOrder() {
        List<ClientPayment> clientPayments = new ArrayList<>();
        clientPayments.add(new ClientPayment());
        when(clientPaymentService.getClientPaymentsByOrder(any(Order.class))).thenReturn(clientPayments);

        ResponseEntity<ApiResponse<List<ClientPayment>>> responseEntity = clientPaymentController.getClientPaymentsByOrder(1);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<List<ClientPayment>> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Client payments retrieved successfully for the order", response.getMessage());
        assertEquals(clientPayments, response.getData());
    }

    /**
     * Tests the getClientPaymentsByDate method of ClientPaymentController.
     * Verifies that the response contains the expected data and status.
     */
    @Test
    void getClientPaymentsByDate() {
        List<ClientPayment> clientPayments = new ArrayList<>();
        clientPayments.add(new ClientPayment());
        when(clientPaymentService.getClientPaymentsByDate(any(LocalDate.class))).thenReturn(clientPayments);

        ResponseEntity<ApiResponse<List<ClientPayment>>> responseEntity = clientPaymentController.getClientPaymentsByDate(LocalDate.now());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<List<ClientPayment>> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Client payments retrieved successfully for the date", response.getMessage());
        assertEquals(clientPayments, response.getData());
    }

    /**
     * Tests the saveClientPayment method of ClientPaymentController.
     * Verifies that the response contains the expected data and status.
     */
    @Test
    void saveClientPayment() {
        ClientPayment clientPayment = new ClientPayment();
        ClientPayment savedClientPayment = new ClientPayment();
        when(clientPaymentService.saveClientPayment(clientPayment)).thenReturn(savedClientPayment);

        ResponseEntity<ApiResponse<ClientPayment>> responseEntity = clientPaymentController.saveClientPayment(clientPayment);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ApiResponse<ClientPayment> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("Client payment saved successfully", response.getMessage());
        assertEquals(savedClientPayment, response.getData());
    }

    /**
     * Tests the updateClientPayment method of ClientPaymentController.
     * Verifies that the response contains the expected data and status.
     */
    @Test
    void updateClientPayment() {
        Integer clientId = 1;
        ClientPayment clientPayment = new ClientPayment();
        ClientPayment updatedClientPayment = new ClientPayment();
        when(clientPaymentService.updateClientPayment(clientPayment)).thenReturn(updatedClientPayment);

        ResponseEntity<ApiResponse<ClientPayment>> responseEntity = clientPaymentController.updateClientPayment(clientId, clientPayment);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<ClientPayment> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Client payment updated successfully", response.getMessage());
        assertEquals(updatedClientPayment, response.getData());
    }
}
