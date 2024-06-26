package online.lcelectronics.api.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import online.lcelectronics.api.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OrderTest {

    private Order order;
    private Validator validator;
    private Client client;
    private HistoricAppliance historicAppliance;
    private Image image;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        client = new Client();
        historicAppliance = new HistoricAppliance();
        image = new Image();
        List<Image> images = new ArrayList<>();
        images.add(image);

        order = new Order();
        order.setId(1);
        order.setClient(client);
        order.setIssue("Test issue");
        order.setProductReceivedNotes("Test notes");
        order.setHistoricAppliance(historicAppliance);
        order.setImages(images);
        order.setStatus(OrderStatus.COMPLETED);
        order.setReferenceCode("unique-reference-code");
    }

    /**
     * Tests the getter and setter methods of Order.
     * Verifies that the values are correctly set and retrieved.
     */
    @Test
    void testGettersAndSetters() {
        assertEquals(1, order.getId());
        assertEquals(client, order.getClient());
        assertEquals("Test issue", order.getIssue());
        assertEquals("Test notes", order.getProductReceivedNotes());
        assertEquals(historicAppliance, order.getHistoricAppliance());
        assertEquals(1, order.getImages().size());
        assertEquals(image, order.getImages().get(0));
        assertEquals(OrderStatus.COMPLETED, order.getStatus());
        assertEquals("unique-reference-code", order.getReferenceCode());
        // Ensure createdDate is null
        assertNull(order.getCreatedDate());
    }

    /**
     * Tests validation when the client is null.
     * Ensures a constraint violation occurs when the client is null.
     */
    @Test
    void whenClientIsNull_thenOneConstraintViolation() {
        order.setClient(null);

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertEquals(1, violations.size());
        ConstraintViolation<Order> violation = violations.iterator().next();
        assertEquals("Client must not be null", violation.getMessage());
    }

    /**
     * Tests validation when the issue is empty.
     * Ensures a constraint violation occurs when the issue is empty.
     */
    @Test
    void whenIssueIsEmpty_thenOneConstraintViolation() {
        order.setIssue("");

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertEquals(1, violations.size());
        ConstraintViolation<Order> violation = violations.iterator().next();
        assertEquals("Issue must not be empty", violation.getMessage());
    }

    /**
     * Tests validation when the product received notes is empty.
     * Ensures a constraint violation occurs when the product received notes is empty.
     */
    @Test
    void whenProductReceivedNotesIsEmpty_thenOneConstraintViolation() {
        order.setProductReceivedNotes("");

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertEquals(1, violations.size());
        ConstraintViolation<Order> violation = violations.iterator().next();
        assertEquals("Product received notes must not be empty", violation.getMessage());
    }

    /**
     * Tests validation when the historic appliance is null.
     * Ensures a constraint violation occurs when the historic appliance is null.
     */
    @Test
    void whenHistoricApplianceIsNull_thenOneConstraintViolation() {
        order.setHistoricAppliance(null);

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertEquals(1, violations.size());
        ConstraintViolation<Order> violation = violations.iterator().next();
        assertEquals("Historic appliance must not be null", violation.getMessage());
    }

    /**
     * Tests validation when the images list is empty.
     * Ensures a constraint violation occurs when the images list is empty.
     */
    @Test
    void whenImagesIsEmpty_thenOneConstraintViolation() {
        order.setImages(new ArrayList<>());  // Empty list

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertEquals(1, violations.size());
        ConstraintViolation<Order> violation = violations.iterator().next();
        assertEquals("Images must not be empty", violation.getMessage());
    }

    /**
     * Tests validation when the status is null.
     * Ensures a constraint violation occurs when the status is null.
     */
    @Test
    void whenStatusIsNull_thenOneConstraintViolation() {
        order.setStatus(null);

        Set<ConstraintViolation<Order>> violations = validator.validate(order);

        assertEquals(1, violations.size());
        ConstraintViolation<Order> violation = violations.iterator().next();
        assertEquals("Status must not be null", violation.getMessage());
    }
}
