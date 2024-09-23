package online.lcelectronics.api.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import online.demo.api.entities.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageTest {

    private Image image;
    private Validator validator;
    private byte[] imageContent;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        imageContent = new byte[]{1, 2, 3};

        image = new Image();
        image.setId(1);
        image.setMime("image/png");
        image.setName("Test Image");
        image.setContent(imageContent);
    }

    /**
     * Tests the getter and setter methods of Image.
     * Verifies that the values are correctly set and retrieved.
     */
    @Test
    void testGettersAndSetters() {
        assertEquals(1, image.getId());
        assertEquals("image/png", image.getMime());
        assertEquals("Test Image", image.getName());
        assertEquals(imageContent, image.getContent());
    }

    /**
     * Tests validation when the MIME type is null or empty.
     * Ensures a constraint violation occurs when the MIME type is null or empty.
     *
     * @param invalidMime the invalid MIME type (null or empty).
     */
    @ParameterizedTest
    @NullAndEmptySource
    void whenMimeIsNullOrEmpty_thenOneConstraintViolation(String invalidMime) {
        image.setMime(invalidMime);

        Set<ConstraintViolation<Image>> violations = validator.validate(image);

        assertEquals(1, violations.size());
        ConstraintViolation<Image> violation = violations.iterator().next();
        assertEquals("MIME type cannot be empty", violation.getMessage());
    }

    /**
     * Tests validation when the name is null or empty.
     * Ensures a constraint violation occurs when the name is null or empty.
     *
     * @param invalidName the invalid name (null or empty).
     */
    @ParameterizedTest
    @NullAndEmptySource
    void whenNameIsNullOrEmpty_thenOneConstraintViolation(String invalidName) {
        image.setName(invalidName);

        Set<ConstraintViolation<Image>> violations = validator.validate(image);

        assertEquals(1, violations.size());
        ConstraintViolation<Image> violation = violations.iterator().next();
        assertEquals("Name cannot be empty", violation.getMessage());
    }

    /**
     * Tests validation when the content is null or empty.
     * Ensures a constraint violation occurs when the content is null or empty.
     *
     * @param invalidContent the invalid content (null or empty).
     */
    @ParameterizedTest
    @NullAndEmptySource
    void whenContentIsNullOrEmpty(byte[] invalidContent) {
        image.setContent(invalidContent);

        Set<ConstraintViolation<Image>> violations = validator.validate(image);

        assertEquals(1, violations.size());
        ConstraintViolation<Image> violation = violations.iterator().next();
        assertEquals("Content cannot be empty", violation.getMessage());
    }
}
