package online.lcelectronics.api.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientTest {

    private Client client;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        client = new Client();
        client.setIdentityCard(123456789);
        client.setName("Test Client");
        client.setPhone(1234567890);
        client.setAddress("123 Test Street");
    }

    /**
     * Tests the getter and setter methods of Client.
     * Verifies that the values are correctly set and retrieved.
     */
    @Test
    void testGettersAndSetters() {
        assertEquals(123456789, client.getIdentityCard());
        assertEquals("Test Client", client.getName());
        assertEquals(1234567890, client.getPhone());
        assertEquals("123 Test Street", client.getAddress());
    }

    /**
     * Tests validation when the client identity card is null.
     * Ensures a constraint violation occurs when the identity card is null.
     */
    @Test
    void whenNullIdentityCard_thenOneConstraintViolation() {
        client.setIdentityCard(null);

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        assertEquals(1, violations.size());
        ConstraintViolation<Client> violation = violations.iterator().next();
        assertEquals("Identity card cannot be null", violation.getMessage());
    }

    /**
     * Tests validation when the client name is invalid.
     * Ensures a constraint violation occurs when the name is null or empty.
     *
     * @param invalidName the invalid name (null or empty).
     */
    @ParameterizedTest
    @NullAndEmptySource
    void whenInvalidName_thenOneConstraintViolation(String invalidName) {
        client.setName(invalidName);

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        assertEquals(1, violations.size());
        ConstraintViolation<Client> violation = violations.iterator().next();
        assertEquals("Name cannot be blank", violation.getMessage());
    }

    /**
     * Tests validation when the client phone number is null.
     * Ensures a constraint violation occurs when the phone number is null.
     */
    @Test
    void whenNullPhone_thenOneConstraintViolation() {
        client.setPhone(null);

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        assertEquals(1, violations.size());
        ConstraintViolation<Client> violation = violations.iterator().next();
        assertEquals("Phone number cannot be null", violation.getMessage());
    }

    /**
     * Tests validation when the client address is invalid.
     * Ensures a constraint violation occurs when the address is null or empty.
     *
     * @param invalidAddress the invalid address (null or empty).
     */
    @ParameterizedTest
    @NullAndEmptySource
    void whenInvalidAddress_thenOneConstraintViolation(String invalidAddress) {
        client.setAddress(invalidAddress);

        Set<ConstraintViolation<Client>> violations = validator.validate(client);

        assertEquals(1, violations.size());
        ConstraintViolation<Client> violation = violations.iterator().next();
        assertEquals("Address cannot be blank", violation.getMessage());
    }
}
