package online.lcelectronics.api.controllers;

import online.lcelectronics.api.entities.ApplianceModel;
import online.lcelectronics.api.enums.ApplianceCategory;
import online.lcelectronics.api.enums.Brand;
import online.lcelectronics.api.responses.ApiResponse;
import online.lcelectronics.api.services.ApplianceModelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplianceModelControllerTest {

    @Mock
    ApplianceModelService applianceModelService;

    @InjectMocks
    ApplianceModelController applianceModelController;

    /**
     * Tests the getAllApplianceModels method of ApplianceModelController.
     * Verifies that all appliance models are retrieved successfully.
     */
    @Test
    void getAllApplianceModels() {
        List<ApplianceModel> applianceModels = new ArrayList<>();
        applianceModels.add(new ApplianceModel());
        applianceModels.add(new ApplianceModel());

        when(applianceModelService.getAllApplianceModels()).thenReturn(applianceModels);

        ResponseEntity<ApiResponse<List<ApplianceModel>>> responseEntity = applianceModelController.getAllApplianceModels();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Appliance models found", responseEntity.getBody().getMessage());
        assertEquals(applianceModels, responseEntity.getBody().getData());
    }

    /**
     * Tests the getApplianceModelById method of ApplianceModelController with an existing ID.
     * Verifies that the appliance model with the given ID is retrieved successfully.
     */
    @Test
    void getApplianceModelById() {
        ApplianceModel applianceModel = new ApplianceModel();
        applianceModel.setId(1);

        when(applianceModelService.getApplianceModelById(1)).thenReturn(applianceModel);

        ResponseEntity<ApiResponse<ApplianceModel>> responseEntity = applianceModelController.getApplianceModelById(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Appliance model found", responseEntity.getBody().getMessage());
        assertEquals(applianceModel, responseEntity.getBody().getData());
    }

    /**
     * Tests the getApplianceModelByModel method of ApplianceModelController with an existing model.
     * Verifies that the appliance model with the given model is retrieved successfully.
     */
    @Test
    void getApplianceModelByModel() {
        ApplianceModel applianceModel = new ApplianceModel();
        applianceModel.setModel("TestModel");

        when(applianceModelService.getApplianceModelByModel("TestModel")).thenReturn(applianceModel);

        ResponseEntity<ApiResponse<ApplianceModel>> responseEntity = applianceModelController.getApplianceModelByModel("TestModel");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Appliance model found", responseEntity.getBody().getMessage());
        assertEquals(applianceModel, responseEntity.getBody().getData());
    }

    /**
     * Tests the getApplianceModelsByCriteria method of ApplianceModelController with existing criteria.
     * Verifies that the appliance models matching the criteria are retrieved successfully.
     */
    @Test
    void getApplianceModelsByCriteria() {
        ApplianceModel applianceModel = new ApplianceModel();
        applianceModel.setModel("TestModel");

        List<ApplianceModel> applianceModels = new ArrayList<>();
        applianceModels.add(applianceModel);

        when(applianceModelService.getApplianceModelsByCriteria(any(ApplianceModel.class))).thenReturn(applianceModels);

        ResponseEntity<ApiResponse<List<ApplianceModel>>> responseEntity = applianceModelController.getApplianceModelsByCriteria("TestModel", ApplianceCategory.TV, Brand.SAMSUNG, Year.of(2020));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Appliance models found", responseEntity.getBody().getMessage());
        assertEquals(applianceModels, responseEntity.getBody().getData());
    }

    /**
     * Tests the createApplianceModel method of ApplianceModelController with a valid model.
     * Verifies that the appliance model is successfully created.
     */
    @Test
    void createApplianceModel() {
        ApplianceModel applianceModel = new ApplianceModel();
        applianceModel.setModel("TestModel");

        when(applianceModelService.saveApplianceModel(applianceModel)).thenReturn(applianceModel);

        ResponseEntity<ApiResponse<ApplianceModel>> responseEntity = applianceModelController.createApplianceModel(applianceModel);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(HttpStatus.CREATED.value(), responseEntity.getBody().getStatus());
        assertEquals("Appliance model created", responseEntity.getBody().getMessage());
        assertEquals(applianceModel, responseEntity.getBody().getData());
    }

    /**
     * Tests the updateApplianceModel method of ApplianceModelController with an existing model.
     * Verifies that the appliance model is successfully updated.
     */
    @Test
    void updateApplianceModel() {
        ApplianceModel applianceModel = new ApplianceModel();
        applianceModel.setId(1);
        applianceModel.setModel("UpdatedModel");

        when(applianceModelService.updateApplianceModel(applianceModel)).thenReturn(applianceModel);

        ResponseEntity<ApiResponse<ApplianceModel>> responseEntity = applianceModelController.updateApplianceModel(1, applianceModel);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Appliance model updated", responseEntity.getBody().getMessage());
        assertEquals(applianceModel, responseEntity.getBody().getData());
    }
}
