package online.lcelectronics.api.services;

import online.demo.api.entities.ClientPayment;
import online.demo.api.entities.Order;
import online.demo.api.exceptions.NotFoundException;
import online.demo.api.repositories.ClientPaymentRepository;
import online.demo.api.repositories.OrderRepository;
import online.demo.api.services.ClientPaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientPaymentServiceTest {

    @Mock
    private ClientPaymentRepository clientPaymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ClientPaymentService clientPaymentService;

    private ClientPayment clientPayment;
    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1);

        clientPayment = new ClientPayment();
        clientPayment.setId(1);
        clientPayment.setOrder(order);
        clientPayment.setDate(LocalDate.now());
    }

    /**
     * Tests the getAllClientPayments method of ClientPaymentService.
     * Verifies that all client payments are retrieved successfully.
     */
    @Test
    void getAllClientPayments() {
        List<ClientPayment> payments = new ArrayList<>();
        payments.add(clientPayment);
        when(clientPaymentRepository.findAll()).thenReturn(payments);

        List<ClientPayment> result = clientPaymentService.getAllClientPayments();
        assertEquals(1, result.size());
        assertEquals(clientPayment, result.get(0));
    }

    /**
     * Tests the getClientPaymentById method of ClientPaymentService with an existing ID.
     * Verifies that the client payment with the given ID is retrieved successfully.
     */
    @Test
    void getClientPaymentById_existingId() {
        when(clientPaymentRepository.findById(1)).thenReturn(Optional.of(clientPayment));

        ClientPayment result = clientPaymentService.getClientPaymentById(1);
        assertEquals(clientPayment, result);
    }

    /**
     * Tests the getClientPaymentById method of ClientPaymentService with a non-existing ID.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void getClientPaymentById_nonExistingId() {
        when(clientPaymentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clientPaymentService.getClientPaymentById(1));
    }

    /**
     * Tests the getClientPaymentsByOrder method of ClientPaymentService with an existing order.
     * Verifies that client payments with the given order are retrieved successfully.
     */
    @Test
    void getClientPaymentsByOrder_existingOrder() {
        List<ClientPayment> payments = new ArrayList<>();
        payments.add(clientPayment);
        when(clientPaymentRepository.findByOrder(order)).thenReturn(payments);

        List<ClientPayment> result = clientPaymentService.getClientPaymentsByOrder(order);
        assertEquals(1, result.size());
        assertEquals(clientPayment, result.get(0));
    }

    /**
     * Tests the getClientPaymentsByOrder method of ClientPaymentService with a non-existing order.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void getClientPaymentsByOrder_nonExistingOrder() {
        when(clientPaymentRepository.findByOrder(order)).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> clientPaymentService.getClientPaymentsByOrder(order));
    }

    /**
     * Tests the getClientPaymentsByDate method of ClientPaymentService with an existing date.
     * Verifies that client payments with the given date are retrieved successfully.
     */
    @Test
    void getClientPaymentsByDate_existingDate() {
        List<ClientPayment> payments = new ArrayList<>();
        payments.add(clientPayment);
        when(clientPaymentRepository.findByDate(any(LocalDate.class))).thenReturn(payments);

        List<ClientPayment> result = clientPaymentService.getClientPaymentsByDate(clientPayment.getDate());
        assertEquals(1, result.size());
        assertEquals(clientPayment, result.get(0));
    }

    /**
     * Tests the getClientPaymentsByDate method of ClientPaymentService with a non-existing date.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void getClientPaymentsByDate_nonExistingDate() {
        when(clientPaymentRepository.findByDate(any(LocalDate.class))).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> clientPaymentService.getClientPaymentsByDate(clientPayment.getDate()));
    }

    /**
     * Tests the saveClientPayment method of ClientPaymentService with a valid payment.
     * Verifies that the client payment is successfully saved.
     */
    @Test
    void saveClientPayment_validPayment() {
        when(orderRepository.existsById(order.getId())).thenReturn(true);
        when(clientPaymentRepository.save(clientPayment)).thenReturn(clientPayment);

        ClientPayment result = clientPaymentService.saveClientPayment(clientPayment);
        assertEquals(clientPayment, result);
    }

    /**
     * Tests the saveClientPayment method of ClientPaymentService with an invalid order.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void saveClientPayment_invalidOrder() {
        when(orderRepository.existsById(order.getId())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> clientPaymentService.saveClientPayment(clientPayment));
    }

    /**
     * Tests the updateClientPayment method of ClientPaymentService with an existing payment.
     * Verifies that the client payment is successfully updated.
     */
    @Test
    void updateClientPayment_existingPayment() {
        when(orderRepository.existsById(order.getId())).thenReturn(true);
        when(clientPaymentRepository.existsById(clientPayment.getId())).thenReturn(true);
        when(clientPaymentRepository.saveAndFlush(clientPayment)).thenReturn(clientPayment);

        ClientPayment result = clientPaymentService.updateClientPayment(clientPayment);
        assertEquals(clientPayment, result);
    }

    /**
     * Tests the updateClientPayment method of ClientPaymentService with a non-existing payment.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void updateClientPayment_nonExistingPayment() {
        when(orderRepository.existsById(order.getId())).thenReturn(true);
        when(clientPaymentRepository.existsById(clientPayment.getId())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> clientPaymentService.updateClientPayment(clientPayment));
    }

    /**
     * Tests the updateClientPayment method of ClientPaymentService with an invalid order.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void updateClientPayment_invalidOrder() {
        when(orderRepository.existsById(order.getId())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> clientPaymentService.updateClientPayment(clientPayment));
    }

}
