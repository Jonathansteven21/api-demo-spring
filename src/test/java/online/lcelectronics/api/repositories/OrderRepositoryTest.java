package online.lcelectronics.api.repositories;

import online.demo.api.entities.Client;
import online.demo.api.entities.HistoricAppliance;
import online.demo.api.entities.Image;
import online.demo.api.entities.Order;
import online.demo.api.enums.OrderStatus;
import online.demo.api.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@DataJpaTest
class OrderRepositoryTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setClient(new Client()); // Assuming Client entity exists and is properly set up
        order.setIssue("Test issue");
        order.setProductReceivedNotes("Test notes");
        order.setHistoricAppliance(new HistoricAppliance()); // Assuming HistoricAppliance entity exists and is properly set up
        order.setImages(List.of(new Image())); // Assuming Image entity exists and is properly set up
        order.setStatus(OrderStatus.COMPLETED);
        order.setReferenceCode("unique-reference-code");
    }

    /**
     * Test for verifying the retrieval of an order by its reference code.
     * It mocks the OrderRepository to return a predefined order and
     * verifies the response.
     */
    @Test
    void testFindByReferenceCode() {
        // Arrange
        when(orderRepository.findByReferenceCode(order.getReferenceCode()))
                .thenReturn(Optional.of(order));

        // Act
        Optional<Order> foundOrder = orderRepository.findByReferenceCode("unique-reference-code");

        // Assert
        assertTrue(foundOrder.isPresent());
        assertEquals("unique-reference-code", foundOrder.get().getReferenceCode());
    }

    /**
     * Test for verifying the behavior when an order with the given reference code is not found.
     * It mocks the OrderRepository to return an empty optional and
     * verifies the response.
     */
    @Test
    void testFindByReferenceCode_NotFound() {
        // Arrange
        String nonExistentReferenceCode = "non-existent-code";
        when(orderRepository.findByReferenceCode(nonExistentReferenceCode))
                .thenReturn(Optional.empty());

        // Act
        Optional<Order> foundOrder = orderRepository.findByReferenceCode(nonExistentReferenceCode);

        // Assert
        assertTrue(foundOrder.isEmpty());
    }

    /**
     * Test for verifying pagination of orders using the pageable method.
     * It mocks the OrderRepository to return a predefined page of orders
     * and verifies the response.
     */
    @Test
    void testFindAllWithPagination() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10); // First page, 10 orders per page
        List<Order> orders = List.of(order); // Create a list with one order
        Page<Order> orderPage = new PageImpl<>(orders, pageable, orders.size());

        when(orderRepository.findAll(pageable)).thenReturn(orderPage);

        // Act
        Page<Order> foundOrdersPage = orderRepository.findAll(pageable);

        // Assert
        assertEquals(1, foundOrdersPage.getTotalElements()); // 1 order found
        assertEquals(1, foundOrdersPage.getContent().size()); // The content should contain 1 order
        assertEquals(order.getReferenceCode(), foundOrdersPage.getContent().get(0).getReferenceCode()); // Ensure the order matches
    }
}
