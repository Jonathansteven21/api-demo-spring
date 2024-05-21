package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.ApplianceModel;
import online.lcelectronics.api.entities.HistoricAppliance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class HistoricApplianceRepositoryTest {

    @Mock
    private HistoricApplianceRepository historicApplianceRepository;

    @InjectMocks
    private HistoricAppliance historicAppliance;

    @InjectMocks
    private ApplianceModel applianceModel;

    @BeforeEach
    void setUp() {
        historicAppliance = new HistoricAppliance();
        historicAppliance.setSerial("123456");
        applianceModel = new ApplianceModel();
        applianceModel.setModel("Test Model");
        historicAppliance.setModel(applianceModel);
    }

    /**
     * Tests the findBySerialContaining method of HistoricApplianceRepository.
     * Verifies that the correct HistoricAppliance(s) are returned when searched by serial.
     */
    @Test
    void whenFindBySerialContaining_thenReturnHistoricAppliances() {
        when(historicApplianceRepository.findBySerialContaining(historicAppliance.getSerial()))
                .thenReturn(Collections.singletonList(historicAppliance));

        List<HistoricAppliance> foundAppliances = historicApplianceRepository.findBySerialContaining(historicAppliance.getSerial());

        assertFalse(foundAppliances.isEmpty());
        assertEquals(historicAppliance.getSerial(), foundAppliances.get(0).getSerial());
    }

    /**
     * Tests the findBySerialContaining method of HistoricApplianceRepository when the serial is not contained.
     * Ensures that an empty list is returned when no historic appliances are found with the provided serial.
     */
    @Test
    void whenSerialNotContained_thenReturnEmptyList() {
        String nonExistentSerial = "999999";
        when(historicApplianceRepository.findBySerialContaining(nonExistentSerial))
                .thenReturn(Collections.emptyList());

        List<HistoricAppliance> foundAppliances = historicApplianceRepository.findBySerialContaining(nonExistentSerial);

        assertTrue(foundAppliances.isEmpty());
    }

    /**
     * Tests the findByModel method of HistoricApplianceRepository.
     * Verifies that the correct HistoricAppliance is returned when searched by model.
     */
    @Test
    void whenFindByModel_thenReturnHistoricAppliance() {
        when(historicApplianceRepository.findByModel(applianceModel))
                .thenReturn(Optional.of(historicAppliance));

        Optional<HistoricAppliance> foundAppliance = historicApplianceRepository.findByModel(applianceModel);

        assertTrue(foundAppliance.isPresent());
        assertEquals(historicAppliance.getModel(), foundAppliance.get().getModel());
    }

    /**
     * Tests the findByModel method of HistoricApplianceRepository when the model is not found.
     * Ensures that an empty optional is returned when the model does not exist.
     */
    @Test
    void whenModelNotFound_thenReturnEmpty() {
        ApplianceModel nonExistentModel = new ApplianceModel();
        when(historicApplianceRepository.findByModel(nonExistentModel))
                .thenReturn(Optional.empty());

        Optional<HistoricAppliance> foundAppliance = historicApplianceRepository.findByModel(nonExistentModel);

        assertTrue(foundAppliance.isEmpty());
    }

    /**
     * Tests the findByModelNameContaining method of HistoricApplianceRepository.
     * Verifies that the correct HistoricAppliance(s) are returned when searched by model name.
     */
    @Test
    void whenFindByModelNameContaining_thenReturnHistoricAppliances() {
        when(historicApplianceRepository.findByModelNameContaining(applianceModel.getModel()))
                .thenReturn(Collections.singletonList(historicAppliance));

        List<HistoricAppliance> foundAppliances = historicApplianceRepository.findByModelNameContaining(applianceModel.getModel());

        assertFalse(foundAppliances.isEmpty());
        assertEquals(historicAppliance.getModel().getModel(), foundAppliances.get(0).getModel().getModel());
    }

    /**
     * Tests the findByModelNameContaining method of HistoricApplianceRepository when the model name is not contained.
     * Ensures that an empty list is returned when no historic appliances are found with the provided model name.
     */
    @Test
    void whenModelNameNotContained_thenReturnEmptyList() {
        String nonExistentModelName = "Non Existent Model";
        when(historicApplianceRepository.findByModelNameContaining(nonExistentModelName))
                .thenReturn(Collections.emptyList());

        List<HistoricAppliance> foundAppliances = historicApplianceRepository.findByModelNameContaining(nonExistentModelName);

        assertTrue(foundAppliances.isEmpty());
    }
}
