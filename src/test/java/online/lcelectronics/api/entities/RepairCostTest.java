package online.lcelectronics.api.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validator;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepairCostTest {

    private RepairCost repairCost;
    private Validator validator;
    private Order order;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        order = new Order();
        repairCost = new RepairCost();
        repairCost.setId(1);
        repairCost.setOrder(order);
        repairCost.setDescription("Test description");
        repairCost.setAmount(BigDecimal.valueOf(100));
    }

    /**
     * Tests the getter and setter methods of RepairCost.
     * Verifies that the values are correctly set and retrieved.
     */
    @Test
    void testGettersAndSetters() {
        assertEquals(1, repairCost.getId());
        assertEquals(order, repairCost.getOrder());
        assertEquals("Test description", repairCost.getDescription());
        assertEquals(BigDecimal.valueOf(100), repairCost.getAmount());
    }

    /**
     * Tests validation when the order is null.
     * Ensures a constraint violation occurs when the order is null.
     */
    @Test
    void whenOrderIsNull_thenOneConstraintViolation() {
        repairCost.setOrder(null);

        Set<ConstraintViolation<RepairCost>> violations = validator.validate(repairCost);

        assertEquals(1, violations.size());
        ConstraintViolation<RepairCost> violation = violations.iterator().next();
        assertEquals("order must not be null", violation.getMessage());
    }

    /**
     * Tests validation when the description is empty.
     * Ensures a constraint violation occurs when the description is empty.
     */
    @Test
    void whenDescriptionIsEmpty_thenOneConstraintViolation() {
        repairCost.setDescription("");

        Set<ConstraintViolation<RepairCost>> violations = validator.validate(repairCost);

        assertEquals(1, violations.size());
        ConstraintViolation<RepairCost> violation = violations.iterator().next();
        assertEquals("description must not be null or empty", violation.getMessage());
    }

    /**
     * Tests validation when the description is null.
     * Ensures a constraint violation occurs when the description is null.
     */
    @Test
    void whenDescriptionIsNull_thenOneConstraintViolation() {
        repairCost.setDescription(null);

        Set<ConstraintViolation<RepairCost>> violations = validator.validate(repairCost);

        assertEquals(1, violations.size());
        ConstraintViolation<RepairCost> violation = violations.iterator().next();
        assertEquals("description must not be null or empty", violation.getMessage());
    }

    /**
     * Tests validation when the amount is zero.
     * Ensures a constraint violation occurs when the amount is zero.
     */
    @Test
    void whenAmountIsZero_thenOneConstraintViolation() {
        repairCost.setAmount(BigDecimal.ZERO);

        Set<ConstraintViolation<RepairCost>> violations = validator.validate(repairCost);

        assertEquals(1, violations.size());
        ConstraintViolation<RepairCost> violation = violations.iterator().next();
        assertEquals("amount must be greater than zero", violation.getMessage());
    }

    /**
     * Tests validation when the amount is negative.
     * Ensures a constraint violation occurs when the amount is negative.
     */
    @Test
    void whenAmountIsNegative_thenOneConstraintViolation() {
        repairCost.setAmount(BigDecimal.valueOf(-100));

        Set<ConstraintViolation<RepairCost>> violations = validator.validate(repairCost);

        assertEquals(1, violations.size());
        ConstraintViolation<RepairCost> violation = violations.iterator().next();
        assertEquals("amount must be greater than zero", violation.getMessage());
    }
}
