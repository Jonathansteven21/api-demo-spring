package online.lcelectronics.api.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HistoricApplianceTest {

    private HistoricAppliance historicAppliance;
    private Validator validator;
    private ApplianceModel applianceModel;
    private Date manufactureDate;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        applianceModel = new ApplianceModel();
        manufactureDate = new Date(System.currentTimeMillis() - 24L * 60L * 60L * 1000L); // Yesterday

        historicAppliance = new HistoricAppliance();
        historicAppliance.setSerial("12345ABC");
        historicAppliance.setModel(applianceModel);
        historicAppliance.setManufactureDate(manufactureDate);
    }

    /**
     * Tests the getter and setter methods of HistoricAppliance.
     * Verifies that the values are correctly set and retrieved.
     */
    @Test
    void testGettersAndSetters() {
        assertEquals("12345ABC", historicAppliance.getSerial());
        assertEquals(applianceModel, historicAppliance.getModel());
        assertEquals(manufactureDate, historicAppliance.getManufactureDate());
    }

    /**
     * Tests validation when the serial is null.
     * Ensures a constraint violation occurs when the serial is null.
     *
     * @param invalidSerial the invalid serial (null).
     */
    @ParameterizedTest
    @NullSource
    void whenSerialIsNull_thenOneConstraintViolation(String invalidSerial) {
        historicAppliance.setSerial(invalidSerial);

        Set<ConstraintViolation<HistoricAppliance>> violations = validator.validate(historicAppliance);

        assertEquals(1, violations.size());
        ConstraintViolation<HistoricAppliance> violation = violations.iterator().next();
        assertEquals("Serial cannot be empty", violation.getMessage());
    }

    /**
     * Tests validation when the model is null.
     * Ensures a constraint violation occurs when the model is null.
     *
     * @param invalidModel the invalid model (null).
     */
    @ParameterizedTest
    @NullSource
    void whenModelIsNull_thenOneConstraintViolation(ApplianceModel invalidModel) {
        historicAppliance.setModel(invalidModel);

        Set<ConstraintViolation<HistoricAppliance>> violations = validator.validate(historicAppliance);

        assertEquals(1, violations.size());
        ConstraintViolation<HistoricAppliance> violation = violations.iterator().next();
        assertEquals("Model must be provided", violation.getMessage());
    }

    /**
     * Tests validation when the manufacture date is null.
     * Ensures a constraint violation occurs when the manufacture date is null.
     *
     * @param invalidDate the invalid manufacture date (null).
     */
    @ParameterizedTest
    @NullSource
    void whenManufactureDateIsNull_thenOneConstraintViolation(Date invalidDate) {
        historicAppliance.setManufactureDate(invalidDate);

        Set<ConstraintViolation<HistoricAppliance>> violations = validator.validate(historicAppliance);

        assertEquals(1, violations.size());
        ConstraintViolation<HistoricAppliance> violation = violations.iterator().next();
        assertEquals("Manufacture date must be provided", violation.getMessage());
    }

    /**
     * Tests validation when the manufacture date is in the future.
     * Ensures a constraint violation occurs when the manufacture date is in the future.
     */
    @Test
    void whenManufactureDateIsInFuture_thenOneConstraintViolation() {
        historicAppliance.setManufactureDate(new Date(System.currentTimeMillis() + 24L * 60L * 60L * 1000L)); // Tomorrow

        Set<ConstraintViolation<HistoricAppliance>> violations = validator.validate(historicAppliance);

        assertEquals(1, violations.size());
        ConstraintViolation<HistoricAppliance> violation = violations.iterator().next();
        assertEquals("Manufacture date must be in the past", violation.getMessage());
    }
}
