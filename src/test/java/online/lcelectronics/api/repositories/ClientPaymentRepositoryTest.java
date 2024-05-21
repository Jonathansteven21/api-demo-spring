package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.ClientPayment;
import online.lcelectronics.api.entities.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class ClientPaymentRepositoryTest {

    @Mock
    private ClientPaymentRepository clientPaymentRepository;

    @InjectMocks
    private ClientPayment clientPayment;

    @InjectMocks
    private Order order;

    @BeforeEach
    void setUp() {
        clientPayment = new ClientPayment();
        clientPayment.setOrder(order);
        clientPayment.setDate(LocalDate.now());
    }

    /**
     * Tests the findByOrder method of ClientPaymentRepository.
     * Verifies that the correct ClientPayment(s) are returned when associated with the given order.
     */
    @Test
    void whenFindByOrder_thenReturnClientPayments() {
        when(clientPaymentRepository.findByOrder(order))
                .thenReturn(Collections.singletonList(clientPayment));

        List<ClientPayment> foundPayments = clientPaymentRepository.findByOrder(order);

        assertFalse(foundPayments.isEmpty());
        assertEquals(clientPayment.getOrder(), foundPayments.get(0).getOrder());
    }

    /**
     * Tests the findByDate method of ClientPaymentRepository.
     * Verifies that the correct ClientPayment(s) are returned when associated with the given date.
     */
    @Test
    void whenFindByDate_thenReturnClientPayments() {
        when(clientPaymentRepository.findByDate(clientPayment.getDate()))
                .thenReturn(Collections.singletonList(clientPayment));

        List<ClientPayment> foundPayments = clientPaymentRepository.findByDate(clientPayment.getDate());

        assertFalse(foundPayments.isEmpty());
        assertEquals(clientPayment.getDate(), foundPayments.get(0).getDate());
    }

    /**
     * Tests the findByOrder method of ClientPaymentRepository when the order is not found.
     * Ensures that an empty list is returned when the order does not exist.
     */
    @Test
    void whenOrderNotFound_thenReturnEmptyList() {
        Order nonExistentOrder = new Order();
        when(clientPaymentRepository.findByOrder(nonExistentOrder))
                .thenReturn(Collections.emptyList());

        List<ClientPayment> foundPayments = clientPaymentRepository.findByOrder(nonExistentOrder);

        assertTrue(foundPayments.isEmpty());
    }

    /**
     * Tests the findByDate method of ClientPaymentRepository when the date is not found.
     * Ensures that an empty list is returned when there are no payments for the given date.
     */
    @Test
    void whenDateNotFound_thenReturnEmptyList() {
        LocalDate nonExistentDate = LocalDate.of(2050, 1, 1);
        when(clientPaymentRepository.findByDate(nonExistentDate))
                .thenReturn(Collections.emptyList());

        List<ClientPayment> foundPayments = clientPaymentRepository.findByDate(nonExistentDate);

        assertTrue(foundPayments.isEmpty());
    }

}
