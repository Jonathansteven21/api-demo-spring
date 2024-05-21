package online.lcelectronics.api.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import online.lcelectronics.api.enums.ApplianceCategory;
import online.lcelectronics.api.enums.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Year;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApplianceModelTest {

    private ApplianceModel applianceModel;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        applianceModel = new ApplianceModel();
        applianceModel.setId(1);
        applianceModel.setModel("Test Model");
        applianceModel.setApplianceCategory(ApplianceCategory.TV);
        applianceModel.setBrand(Brand.LG);
        applianceModel.setManufactureYear(Year.of(2020));
    }

    /**
     * Tests the getter and setter methods of ApplianceModel.
     * Verifies that the values are correctly set and retrieved.
     */
    @Test
    void testGettersAndSetters() {
        assertEquals(1, applianceModel.getId());
        assertEquals("Test Model", applianceModel.getModel());
        assertEquals(ApplianceCategory.TV, applianceModel.getApplianceCategory());
        assertEquals(Brand.LG, applianceModel.getBrand());
        assertEquals(Year.of(2020), applianceModel.getManufactureYear());
    }

    /**
     * Tests model validation with invalid model names.
     * Ensures a constraint violation occurs when the model name is null or empty.
     *
     * @param invalidModel the invalid model name (null or empty).
     */
    @ParameterizedTest
    @NullAndEmptySource
    void whenInvalidModel_thenOneConstraintViolation(String invalidModel) {
        applianceModel.setModel(invalidModel);

        Set<ConstraintViolation<ApplianceModel>> violations = validator.validate(applianceModel);

        assertEquals(1, violations.size());
        ConstraintViolation<ApplianceModel> violation = violations.iterator().next();
        assertEquals("Model name cannot be blank", violation.getMessage());
    }

    /**
     * Tests appliance category validation with invalid values.
     * Ensures a constraint violation occurs when the appliance category is null.
     *
     * @param invalidCategory the invalid appliance category (null).
     */
    @ParameterizedTest
    @NullSource
    void whenInvalidApplianceCategory_thenOneConstraintViolation(ApplianceCategory invalidCategory) {
        applianceModel.setApplianceCategory(invalidCategory);

        Set<ConstraintViolation<ApplianceModel>> violations = validator.validate(applianceModel);

        assertEquals(1, violations.size());
        ConstraintViolation<ApplianceModel> violation = violations.iterator().next();
        assertEquals("Appliance category must be specified", violation.getMessage());
    }

    /**
     * Tests brand validation with invalid values.
     * Ensures a constraint violation occurs when the brand is null.
     *
     * @param invalidBrand the invalid brand (null).
     */
    @ParameterizedTest
    @NullSource
    void whenInvalidBrand_thenOneConstraintViolation(Brand invalidBrand) {
        applianceModel.setBrand(invalidBrand);

        Set<ConstraintViolation<ApplianceModel>> violations = validator.validate(applianceModel);

        assertEquals(1, violations.size());
        ConstraintViolation<ApplianceModel> violation = violations.iterator().next();
        assertEquals("Brand must be specified", violation.getMessage());
    }

    /**
     * Tests manufacture year validation with invalid years.
     * Ensures a constraint violation occurs when the manufacture year is in the future.
     *
     * @param invalidYear the invalid manufacture year (future years).
     */
    @ParameterizedTest
    @ValueSource(ints = {3000, 9999})
    void whenInvalidManufactureYear_thenOneConstraintViolation(int invalidYear) {
        applianceModel.setManufactureYear(Year.of(invalidYear));

        Set<ConstraintViolation<ApplianceModel>> violations = validator.validate(applianceModel);

        assertEquals(1, violations.size());
        ConstraintViolation<ApplianceModel> violation = violations.iterator().next();
        assertEquals("Year must be in the past or present", violation.getMessage());
    }
}
