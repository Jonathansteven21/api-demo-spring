package online.lcelectronics.api.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientPaymentTest {

    private ClientPayment clientPayment;
    private Validator validator;
    private Order order;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        order = new Order();
        clientPayment = new ClientPayment();
        clientPayment.setId(1);
        clientPayment.setOrder(order);
        clientPayment.setAmount(BigDecimal.valueOf(100.00));
    }

    /**
     * Tests the getter and setter methods of ClientPayment.
     * Verifies that the values are correctly set and retrieved.
     */
    @Test
    void testGettersAndSetters() {
        assertEquals(1, clientPayment.getId());
        assertEquals(BigDecimal.valueOf(100.00), clientPayment.getAmount());
        assertEquals(order, clientPayment.getOrder());
    }

    /**
     * Tests validation when the order is null.
     * Ensures a constraint violation occurs when the order is null.
     *
     * @param invalidOrder the invalid order (null).
     */
    @ParameterizedTest
    @NullSource
    void whenOrderIsNull_thenOneConstraintViolation(Order invalidOrder) {
        clientPayment.setOrder(invalidOrder);

        Set<ConstraintViolation<ClientPayment>> violations = validator.validate(clientPayment);

        assertEquals(1, violations.size());
        ConstraintViolation<ClientPayment> violation = violations.iterator().next();
        assertEquals("Order must be provided", violation.getMessage());
    }

    /**
     * Tests validation when the date is not null.
     * Ensures a constraint violation occurs when the date is not null.
     */
    @Test
    void whenDateIsNotNull_thenOneConstraintViolation() {
        clientPayment.setDate(LocalDate.now());

        Set<ConstraintViolation<ClientPayment>> violations = validator.validate(clientPayment);

        assertEquals(1, violations.size());
        ConstraintViolation<ClientPayment> violation = violations.iterator().next();
        assertEquals("date must be null", violation.getMessage());
    }

    /**
     * Tests validation when the amount is null.
     * Ensures a constraint violation occurs when the amount is null.
     *
     * @param invalidAmount the invalid amount (null).
     */
    @ParameterizedTest
    @NullSource
    void whenAmountIsNull_thenOneConstraintViolation(BigDecimal invalidAmount) {
        clientPayment.setAmount(invalidAmount);

        Set<ConstraintViolation<ClientPayment>> violations = validator.validate(clientPayment);

        assertEquals(1, violations.size());
        ConstraintViolation<ClientPayment> violation = violations.iterator().next();
        assertEquals("Amount must be provided", violation.getMessage());
    }

    /**
     * Tests validation when the amount is not positive.
     * Ensures a constraint violation occurs when the amount is not positive.
     *
     * @param invalidAmount the invalid amount (not positive).
     */
    @ParameterizedTest
    @ValueSource(doubles = {-100.00, -1.00, 0.00})
    void whenAmountIsNotPositive_thenOneConstraintViolation(double invalidAmount) {
        clientPayment.setAmount(BigDecimal.valueOf(invalidAmount));

        Set<ConstraintViolation<ClientPayment>> violations = validator.validate(clientPayment);

        assertEquals(1, violations.size());
        ConstraintViolation<ClientPayment> violation = violations.iterator().next();
        assertEquals("amount must be greater than zero", violation.getMessage());
    }
}
