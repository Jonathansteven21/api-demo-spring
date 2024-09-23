package online.lcelectronics.api.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import online.demo.api.entities.Inventory;
import online.demo.api.enums.Brand;
import online.demo.api.enums.Component;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        inventory = new Inventory();
        inventory.setId(1L);
        inventory.setSerial("123456789");
        inventory.setName("Test Inventory");
        inventory.setQuantityNew(10);
        inventory.setQuantityUsed(5);
        inventory.setLocation("Test Location");
        inventory.setLastPrice(BigDecimal.valueOf(100.50));
        inventory.setDateLastPrice(new Date());
        inventory.setCompatibleApplianceModels(new ArrayList<>());
        inventory.setComponent(Component.MAIN_BOARD);
        inventory.setBrand(Brand.SAMSUNG);
        inventory.setImages(new ArrayList<>());
    }

    /**
     * Tests the getter and setter methods of Inventory.
     * Verifies that the values are correctly set and retrieved.
     */
    @Test
    void testGettersAndSetters() {
        assertEquals(1L, inventory.getId());
        assertEquals("123456789", inventory.getSerial());
        assertEquals("Test Inventory", inventory.getName());
        assertEquals(10, inventory.getQuantityNew());
        assertEquals(5, inventory.getQuantityUsed());
        assertEquals("Test Location", inventory.getLocation());
        assertEquals(BigDecimal.valueOf(100.50), inventory.getLastPrice());
        // Ensure date is set and not null
        assertNotNull(inventory.getDateLastPrice());
        assertEquals(Component.MAIN_BOARD, inventory.getComponent());
        assertEquals(Brand.SAMSUNG, inventory.getBrand());
        assertEquals(0, inventory.getCompatibleApplianceModels().size());
        assertEquals(0, inventory.getImages().size());
    }

    /**
     * Tests validation when the serial is null.
     * Ensures a constraint violation occurs when the serial is null.
     */
    @Test
    void whenSerialIsNull_thenOneConstraintViolation() {
        inventory.setSerial(null);  // Null serial

        Set<ConstraintViolation<Inventory>> violations = validator.validate(inventory);

        assertEquals(1, violations.size());
        ConstraintViolation<Inventory> violation = violations.iterator().next();
        assertEquals("Serial cannot be null", violation.getMessage());
    }

    /**
     * Tests validation when the quantity new is null.
     * Ensures a constraint violation occurs when the quantity new is null.
     */
    @Test
    void whenQuantityNewIsNull_thenOneConstraintViolation() {
        inventory.setQuantityNew(null);  // Null quantityNew

        Set<ConstraintViolation<Inventory>> violations = validator.validate(inventory);

        assertEquals(1, violations.size());
        ConstraintViolation<Inventory> violation = violations.iterator().next();
        assertEquals("quantityNew cannot be null", violation.getMessage());
    }

    /**
     * Tests validation when the quantity used is null.
     * Ensures a constraint violation occurs when the quantity used is null.
     */
    @Test
    void whenQuantityUsedIsNull_thenOneConstraintViolation() {
        inventory.setQuantityUsed(null);  // Null quantityUsed

        Set<ConstraintViolation<Inventory>> violations = validator.validate(inventory);

        assertEquals(1, violations.size());
        ConstraintViolation<Inventory> violation = violations.iterator().next();
        assertEquals("quantityUsed cannot be null", violation.getMessage());
    }

    /**
     * Tests validation when the component is null.
     * Ensures a constraint violation occurs when the component is null.
     */
    @Test
    void whenComponentIsNull_thenOneConstraintViolation() {
        inventory.setComponent(null);  // Null component

        Set<ConstraintViolation<Inventory>> violations = validator.validate(inventory);

        assertEquals(1, violations.size());
        ConstraintViolation<Inventory> violation = violations.iterator().next();
        assertEquals("Component cannot be null", violation.getMessage());
    }

    /**
     * Tests validation when the brand is null.
     * Ensures a constraint violation occurs when the brand is null.
     */
    @Test
    void whenBrandIsNull_thenOneConstraintViolation() {
        inventory.setBrand(null);  // Null brand

        Set<ConstraintViolation<Inventory>> violations = validator.validate(inventory);

        assertEquals(1, violations.size());
        ConstraintViolation<Inventory> violation = violations.iterator().next();
        assertEquals("Brand cannot be null", violation.getMessage());
    }
}
