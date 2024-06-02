package online.lcelectronics.api.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import online.lcelectronics.api.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    private Validator validator;

    /**
     * Sets up the Validator instance before each test.
     */
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Tests that a valid user does not have any validation violations.
     */
    @Test
    void testValidUser() {
        User user = new User();
        user.setUsername("validUser");
        user.setPassword("validPassword");
        user.setRole(Role.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Valid user should not have any violations");
    }

    /**
     * Tests that an empty username causes a validation violation.
     */
    @Test
    void testEmptyUsername() {
        User user = new User();
        user.setUsername("");
        user.setPassword("validPassword");
        user.setRole(Role.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Empty username should have one violation");
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Username must not be null or empty", violation.getMessage());
    }

    /**
     * Tests that an empty password causes a validation violation.
     */
    @Test
    void testEmptyPassword() {
        User user = new User();
        user.setUsername("validUser");
        user.setPassword("");
        user.setRole(Role.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Empty password should have one violation");
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Password must not be null or empty", violation.getMessage());
    }

    /**
     * Tests that a null role causes a validation violation.
     */
    @Test
    void testNullRole() {
        User user = new User();
        user.setUsername("validUser");
        user.setPassword("validPassword");
        user.setRole(null);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Null role should have one violation");
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Role cannot be null", violation.getMessage());
    }
}
