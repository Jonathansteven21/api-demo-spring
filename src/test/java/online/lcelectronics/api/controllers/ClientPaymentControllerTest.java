package online.lcelectronics.api.controllers;

import online.lcelectronics.api.entities.ClientPayment;
import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.services.ClientPaymentService;
import online.lcelectronics.api.util.ApiResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;

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

    @Test
    void getAllClientPayments() {
        List<ClientPayment> clientPayments = Arrays.asList(clientPayment);
        when(clientPaymentService.getAllClientPayments()).thenReturn(clientPayments);

        ResponseEntity<ApiResponse<List<ClientPayment>>> responseEntity = clientPaymentController.getAllClientPayments();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clientPayments, responseEntity.getBody().getData());
    }

    @Test
    void getClientPaymentById() {
        Integer clientPaymentId = 1;
        when(clientPaymentService.getClientPaymentById(clientPaymentId)).thenReturn(clientPayment);

        ResponseEntity<ApiResponse<ClientPayment>> responseEntity = clientPaymentController.getClientPaymentById(clientPaymentId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clientPayment, responseEntity.getBody().getData());
    }

    @Test
    void getClientPaymentsByOrder() {
        Integer orderId = 1;
        List<ClientPayment> clientPayments = Arrays.asList(clientPayment);
        when(clientPaymentService.getClientPaymentsByOrder(any())).thenReturn(clientPayments);

        ResponseEntity<ApiResponse<List<ClientPayment>>> responseEntity = clientPaymentController.getClientPaymentsByOrder(orderId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clientPayments, responseEntity.getBody().getData());
    }

    @Test
    void getClientPaymentsByDate() {
        LocalDate date = LocalDate.now();
        List<ClientPayment> clientPayments = Arrays.asList(clientPayment);
        when(clientPaymentService.getClientPaymentsByDate(date)).thenReturn(clientPayments);

        ResponseEntity<ApiResponse<List<ClientPayment>>> responseEntity = clientPaymentController.getClientPaymentsByDate(date);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clientPayments, responseEntity.getBody().getData());
    }

    @Test
    void saveClientPayment() {
        when(clientPaymentService.saveClientPayment(clientPayment)).thenReturn(clientPayment);

        ResponseEntity<ApiResponse<ClientPayment>> responseEntity = clientPaymentController.saveClientPayment(clientPayment);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(clientPayment, responseEntity.getBody().getData());
    }

    @Test
    void updateClientPayment() {
        Integer clientPaymentId = 1;
        when(clientPaymentService.updateClientPayment(clientPayment)).thenReturn(clientPayment);

        ResponseEntity<ApiResponse<ClientPayment>> responseEntity = clientPaymentController.updateClientPayment(clientPaymentId, clientPayment);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(clientPayment, responseEntity.getBody().getData());
    }
}
