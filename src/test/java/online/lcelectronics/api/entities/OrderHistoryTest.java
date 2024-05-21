package online.lcelectronics.api.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderHistoryTest {

    private OrderHistory orderHistory;
    private Validator validator;
    private Order order;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        order = new Order();
        orderHistory = new OrderHistory();
        orderHistory.setId(1);
        orderHistory.setOrder(order);
        orderHistory.setText("Test event");
    }

    /**
     * Tests the getter and setter methods of OrderHistory.
     * Verifies that the values are correctly set and retrieved.
     */
    @Test
    void testGettersAndSetters() {
        assertEquals(1, orderHistory.getId());
        assertEquals(order, orderHistory.getOrder());
        assertEquals("Test event", orderHistory.getText());
        // Ensure eventDate is null
        assertNull(orderHistory.getEventDate());
    }

    /**
     * Tests validation when the order is null.
     * Ensures a constraint violation occurs when the order is null.
     */
    @Test
    void whenOrderIsNull_thenOneConstraintViolation() {
        orderHistory.setOrder(null);

        Set<ConstraintViolation<OrderHistory>> violations = validator.validate(orderHistory);

        assertEquals(1, violations.size());
        ConstraintViolation<OrderHistory> violation = violations.iterator().next();
        assertEquals("order must not be null", violation.getMessage());
    }

    /**
     * Tests validation when the text is empty.
     * Ensures a constraint violation occurs when the text is empty.
     */
    @Test
    void whenTextIsEmpty_thenOneConstraintViolation() {
        orderHistory.setText("");

        Set<ConstraintViolation<OrderHistory>> violations = validator.validate(orderHistory);

        assertEquals(1, violations.size());
        ConstraintViolation<OrderHistory> violation = violations.iterator().next();
        assertEquals("text must not be null or empty", violation.getMessage());
    }

    /**
     * Tests validation when the text is null.
     * Ensures a constraint violation occurs when the text is null.
     */
    @Test
    void whenTextIsNull_thenOneConstraintViolation() {
        orderHistory.setText(null);

        Set<ConstraintViolation<OrderHistory>> violations = validator.validate(orderHistory);

        assertEquals(1, violations.size());
        ConstraintViolation<OrderHistory> violation = violations.iterator().next();
        assertEquals("text must not be null or empty", violation.getMessage());
    }
}
