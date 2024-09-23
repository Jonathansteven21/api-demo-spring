package online.lcelectronics.api.services;

import online.demo.api.entities.ApplianceModel;
import online.demo.api.entities.HistoricAppliance;
import online.demo.api.exceptions.NotFoundException;
import online.demo.api.repositories.ApplianceModelRepository;
import online.demo.api.repositories.HistoricApplianceRepository;
import online.demo.api.services.HistoricApplianceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoricApplianceServiceTest {

    @Mock
    private HistoricApplianceRepository historicApplianceRepository;

    @Mock
    private ApplianceModelRepository applianceModelRepository;

    @InjectMocks
    private HistoricApplianceService historicApplianceService;

    private HistoricAppliance historicAppliance;

    @BeforeEach
    void setUp() {
        ApplianceModel applianceModel = new ApplianceModel();
        applianceModel.setId(1);
        applianceModel.setModel("Model A");

        historicAppliance = new HistoricAppliance();
        historicAppliance.setSerial("ABC123");
        historicAppliance.setModel(applianceModel);
    }

    /**
     * Tests the getAllHistoricAppliances method of HistoricApplianceService.
     * Verifies that the method returns all historic appliances.
     */
    @Test
    void getAllHistoricAppliances() {
        List<HistoricAppliance> historicAppliances = new ArrayList<>();
        historicAppliances.add(historicAppliance);
        when(historicApplianceRepository.findAll()).thenReturn(historicAppliances);

        List<HistoricAppliance> result = historicApplianceService.getAllHistoricAppliances();
        assertEquals(1, result.size());
        assertEquals(historicAppliance, result.get(0));
    }

    /**
     * Tests the getHistoricApplianceBySerial method of HistoricApplianceService with an existing serial.
     * Verifies that the method returns the historic appliance with the given serial.
     */
    @Test
    void getHistoricApplianceBySerial_existingSerial() {
        when(historicApplianceRepository.findById("ABC123")).thenReturn(Optional.of(historicAppliance));

        HistoricAppliance result = historicApplianceService.getHistoricApplianceBySerial("ABC123");
        assertEquals(historicAppliance, result);
    }

    /**
     * Tests the getHistoricApplianceBySerial method of HistoricApplianceService with a non-existing serial.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void getHistoricApplianceBySerial_nonExistingSerial() {
        when(historicApplianceRepository.findById("ABC123")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> historicApplianceService.getHistoricApplianceBySerial("ABC123"));
    }

    /**
     * Tests the getHistoricApplianceByModel method of HistoricApplianceService with an existing model.
     * Verifies that the method returns the historic appliance with the given model.
     */
    @Test
    void getHistoricApplianceByModel_existingModel() {
        ApplianceModel applianceModel = new ApplianceModel();
        applianceModel.setModel("Model A");
        when(applianceModelRepository.findByModel("Model A")).thenReturn(Collections.singletonList(applianceModel));
        HistoricAppliance historicAppliance = new HistoricAppliance();
        List<HistoricAppliance> historicAppliances = Collections.singletonList(historicAppliance);
        when(historicApplianceRepository.findByModel(applianceModel)).thenReturn(historicAppliances);

        List<HistoricAppliance> result = historicApplianceService.getHistoricApplianceByModel("Model A");

        assertEquals(historicAppliances, result);
    }

    /**
     * Tests the getHistoricApplianceByModel method of HistoricApplianceService with a non-existing model.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void getHistoricApplianceByModel_nonExistingModel() {
        when(applianceModelRepository.findByModel("Model A")).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> historicApplianceService.getHistoricApplianceByModel("Model A"));
    }

    /**
     * Tests the getHistoricAppliancesBySerial method of HistoricApplianceService with an existing serial.
     * Verifies that the method returns a list of historic appliances containing the given serial.
     */
    @Test
    void getHistoricAppliancesBySerial_existingSerial() {
        List<HistoricAppliance> historicAppliances = new ArrayList<>();
        historicAppliances.add(historicAppliance);
        when(historicApplianceRepository.findBySerialContaining("ABC")).thenReturn(historicAppliances);

        List<HistoricAppliance> result = historicApplianceService.getHistoricAppliancesBySerial("ABC");
        assertEquals(1, result.size());
        assertEquals(historicAppliance, result.get(0));
    }

    /**
     * Tests the getHistoricAppliancesBySerial method of HistoricApplianceService with a non-existing serial.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void getHistoricAppliancesBySerial_nonExistingSerial() {
        when(historicApplianceRepository.findBySerialContaining("ABC")).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> historicApplianceService.getHistoricAppliancesBySerial("ABC"));
    }

    /**
     * Tests the getHistoricApplianceByModelContaining method of HistoricApplianceService with an existing model.
     * Verifies that the method returns a list of historic appliances containing the given model.
     */
    @Test
    void getHistoricApplianceByModelContaining_existingModel() {
        List<HistoricAppliance> historicAppliances = new ArrayList<>();
        historicAppliances.add(historicAppliance);
        when(historicApplianceRepository.findByModelNameContaining("Model")).thenReturn(historicAppliances);

        List<HistoricAppliance> result = historicApplianceService.getHistoricApplianceByModelContaining("Model");
        assertEquals(1, result.size());
        assertEquals(historicAppliance, result.get(0));
    }

    /**
     * Tests the getHistoricApplianceByModelContaining method of HistoricApplianceService with a non-existing model.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void getHistoricApplianceByModelContaining_nonExistingModel() {
        when(historicApplianceRepository.findByModelNameContaining("Model")).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> historicApplianceService.getHistoricApplianceByModelContaining("Model"));
    }

    /**
     * Tests the saveHistoricAppliance method of HistoricApplianceService with a valid historic appliance.
     * Verifies that the historic appliance is successfully saved.
     */
    @Test
    void saveHistoricAppliance_validHistoricAppliance() {
        when(applianceModelRepository.existsById(1)).thenReturn(true);
        when(historicApplianceRepository.save(historicAppliance)).thenReturn(historicAppliance);

        HistoricAppliance result = historicApplianceService.saveHistoricAppliance(historicAppliance);
        assertEquals(historicAppliance, result);
    }

    /**
     * Tests the saveHistoricAppliance method of HistoricApplianceService with a non-existing model.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void saveHistoricAppliance_nonExistingModel() {
        when(applianceModelRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> historicApplianceService.saveHistoricAppliance(historicAppliance));
    }

    /**
     * Tests the updateHistoricAppliance method of HistoricApplianceService with an existing historic appliance.
     * Verifies that the historic appliance is successfully updated.
     */
    @Test
    void updateHistoricAppliance_existingHistoricAppliance() {
        when(applianceModelRepository.existsById(1)).thenReturn(true);
        when(historicApplianceRepository.existsById("ABC123")).thenReturn(true);
        when(historicApplianceRepository.saveAndFlush(historicAppliance)).thenReturn(historicAppliance);

        HistoricAppliance result = historicApplianceService.updateHistoricAppliance(historicAppliance);
        assertEquals(historicAppliance, result);
    }

    /**
     * Tests the updateHistoricAppliance method of HistoricApplianceService with a non-existing historic appliance.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void updateHistoricAppliance_nonExistingHistoricAppliance() {
        when(applianceModelRepository.existsById(1)).thenReturn(true);
        when(historicApplianceRepository.existsById("ABC123")).thenReturn(false);

        assertThrows(NotFoundException.class, () -> historicApplianceService.updateHistoricAppliance(historicAppliance));
    }

}
