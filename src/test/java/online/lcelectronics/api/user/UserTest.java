package online.lcelectronics.api.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    private Validator validator;

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
        user.setPassword("ValidPassword1!");
        user.setRole(Role.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Valid user should not have any violations");
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
        assertEquals(6, violations.size(), "Empty password should have seven violations");

        int passwordRelatedViolations = 0;
        for (ConstraintViolation<User> violation : violations) {
            if (violation.getPropertyPath().toString().equals("password")) {
                passwordRelatedViolations++;
            }
        }

        assertEquals(6, passwordRelatedViolations, "All violations should be related to password");
    }

    /**
     * Tests that a password without an uppercase letter causes a validation violation.
     */
    @Test
    void testPasswordWithoutUppercase() {
        User user = new User();
        user.setUsername("validUser");
        user.setPassword("invalidpassword1!");
        user.setRole(Role.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Password without uppercase letter should have one violation");
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Password must contain at least one uppercase letter", violation.getMessage());
    }

    /**
     * Tests that a password without a lowercase letter causes a validation violation.
     */
    @Test
    void testPasswordWithoutLowercase() {
        User user = new User();
        user.setUsername("validUser");
        user.setPassword("INVALIDPASSWORD1!");
        user.setRole(Role.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Password without lowercase letter should have one violation");
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Password must contain at least one lowercase letter", violation.getMessage());
    }

    /**
     * Tests that a password without a digit causes a validation violation.
     */
    @Test
    void testPasswordWithoutDigit() {
        User user = new User();
        user.setUsername("validUser");
        user.setPassword("InvalidPassword!");
        user.setRole(Role.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Password without digit should have one violation");
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Password must contain at least one digit", violation.getMessage());
    }

    /**
     * Tests that a password without a special character causes a validation violation.
     */
    @Test
    void testPasswordWithoutSpecialCharacter() {
        User user = new User();
        user.setUsername("validUser");
        user.setPassword("InvalidPassword1");
        user.setRole(Role.USER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Password without special character should have one violation");
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Password must contain at least one special character", violation.getMessage());
    }
}
