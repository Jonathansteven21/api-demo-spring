package online.lcelectronics.api.controllers;

import online.lcelectronics.api.entities.ApplianceModel;
import online.lcelectronics.api.entities.HistoricAppliance;
import online.lcelectronics.api.util.ApiResponse;
import online.lcelectronics.api.services.HistoricApplianceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HistoricApplianceControllerTest {

    @Mock
    HistoricApplianceService historicApplianceService;

    @InjectMocks
    HistoricApplianceController historicApplianceController;

    /**
     * Tests the getAllHistoricAppliances method of HistoricApplianceController.
     * Verifies that all historic appliances are retrieved successfully.
     */
    @Test
    void getAllHistoricAppliances() {
        List<HistoricAppliance> historicAppliances = new ArrayList<>();
        historicAppliances.add(new HistoricAppliance());
        historicAppliances.add(new HistoricAppliance());

        when(historicApplianceService.getAllHistoricAppliances()).thenReturn(historicAppliances);

        ResponseEntity<ApiResponse<List<HistoricAppliance>>> responseEntity = historicApplianceController.getAllHistoricAppliances();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Historic appliances found", responseEntity.getBody().getMessage());
        assertEquals(historicAppliances, responseEntity.getBody().getData());
    }

    /**
     * Tests the getHistoricApplianceBySerial method of HistoricApplianceController with an existing serial.
     * Verifies that the historic appliance with the given serial is retrieved successfully.
     */
    @Test
    void getHistoricApplianceBySerial() {
        HistoricAppliance historicAppliance = new HistoricAppliance();
        historicAppliance.setSerial("12345");

        when(historicApplianceService.getHistoricApplianceBySerial("12345")).thenReturn(historicAppliance);

        ResponseEntity<ApiResponse<HistoricAppliance>> responseEntity = historicApplianceController.getHistoricApplianceBySerial("12345");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Historic appliance found", responseEntity.getBody().getMessage());
        assertEquals(historicAppliance, responseEntity.getBody().getData());
    }

    /**
     * Tests the getHistoricApplianceByModel method of HistoricApplianceController with an existing model name.
     * Verifies that the historic appliances with the given model name are retrieved successfully.
     */
    @Test
    void getHistoricApplianceByModel() {
        List<HistoricAppliance> historicAppliances = new ArrayList<>();
        historicAppliances.add(new HistoricAppliance());

        when(historicApplianceService.getHistoricApplianceByModel("ModelName")).thenReturn(historicAppliances);

        ResponseEntity<ApiResponse<List<HistoricAppliance>>> responseEntity = historicApplianceController.getHistoricApplianceByModel("ModelName");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Historic appliances found", responseEntity.getBody().getMessage());
        assertEquals(historicAppliances, responseEntity.getBody().getData());
    }

    /**
     * Tests the getHistoricAppliancesBySerial method of HistoricApplianceController with an existing serial.
     * Verifies that the historic appliances with the given serial are retrieved successfully.
     */
    @Test
    void getHistoricAppliancesBySerial() {
        List<HistoricAppliance> historicAppliances = new ArrayList<>();
        historicAppliances.add(new HistoricAppliance());

        when(historicApplianceService.getHistoricAppliancesBySerial("12345")).thenReturn(historicAppliances);

        ResponseEntity<ApiResponse<List<HistoricAppliance>>> responseEntity = historicApplianceController.getHistoricAppliancesBySerial("12345");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Historic appliances found", responseEntity.getBody().getMessage());
        assertEquals(historicAppliances, responseEntity.getBody().getData());
    }

    /**
     * Tests the getHistoricApplianceByModelContaining method of HistoricApplianceController with a model substring.
     * Verifies that the historic appliances containing the given model substring are retrieved successfully.
     */
    @Test
    void getHistoricApplianceByModelContaining() {
        List<HistoricAppliance> historicAppliances = new ArrayList<>();
        historicAppliances.add(new HistoricAppliance());

        when(historicApplianceService.getHistoricApplianceByModelContaining("Model")).thenReturn(historicAppliances);

        ResponseEntity<ApiResponse<List<HistoricAppliance>>> responseEntity = historicApplianceController.getHistoricApplianceByModelContaining("Model");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Historic appliances found", responseEntity.getBody().getMessage());
        assertEquals(historicAppliances, responseEntity.getBody().getData());
    }

    /**
     * Tests the saveHistoricAppliance method of HistoricApplianceController with a valid historic appliance.
     * Verifies that the historic appliance is successfully saved.
     */
    @Test
    void saveHistoricAppliance() {
        ApplianceModel model = new ApplianceModel();
        HistoricAppliance historicAppliance = new HistoricAppliance();
        historicAppliance.setSerial("12345");
        historicAppliance.setModel(model);
        historicAppliance.setManufactureDate(new Date());

        when(historicApplianceService.saveHistoricAppliance(historicAppliance)).thenReturn(historicAppliance);

        ResponseEntity<ApiResponse<HistoricAppliance>> responseEntity = historicApplianceController.saveHistoricAppliance(historicAppliance);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(HttpStatus.CREATED.value(), responseEntity.getBody().getStatus());
        assertEquals("Historic appliance saved", responseEntity.getBody().getMessage());
        assertEquals(historicAppliance, responseEntity.getBody().getData());
    }

    /**
     * Tests the updateHistoricAppliance method of HistoricApplianceController with an existing historic appliance.
     * Verifies that the historic appliance is successfully updated.
     */
    @Test
    void updateHistoricAppliance() {
        ApplianceModel model = new ApplianceModel();
        HistoricAppliance historicAppliance = new HistoricAppliance();
        historicAppliance.setSerial("12345");
        historicAppliance.setModel(model);
        historicAppliance.setManufactureDate(new Date());

        when(historicApplianceService.updateHistoricAppliance(historicAppliance)).thenReturn(historicAppliance);

        ResponseEntity<ApiResponse<HistoricAppliance>> responseEntity = historicApplianceController.updateHistoricAppliance("12345", historicAppliance);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Historic appliance updated", responseEntity.getBody().getMessage());
        assertEquals(historicAppliance, responseEntity.getBody().getData());
    }
}
