package online.lcelectronics.api.controllers;

import online.demo.api.controllers.ApplianceModelController;
import online.demo.api.converters.ApplianceModelConverter;
import online.demo.api.dto.ApplianceModelDTO;
import online.demo.api.entities.ApplianceModel;
import online.demo.api.enums.ApplianceCategory;
import online.demo.api.enums.Brand;
import online.demo.api.util.ApiResponse;
import online.demo.api.services.ApplianceModelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplianceModelControllerTest {

    @Mock
    ApplianceModelService applianceModelService;

    @Mock
    ApplianceModelConverter applianceModelConverter;

    @InjectMocks
    ApplianceModelController applianceModelController;

    /**
     * Tests the getAllApplianceModels method of ApplianceModelController.
     * Verifies that all appliance models are retrieved successfully.
     */
    @Test
    void getAllApplianceModels() {
        List<ApplianceModelDTO> applianceModelDTOs = new ArrayList<>();
        applianceModelDTOs.add(new ApplianceModelDTO());
        applianceModelDTOs.add(new ApplianceModelDTO());

        List<ApplianceModel> applianceModels = applianceModelDTOs.stream()
                .map(dto -> {
                    ApplianceModel model = new ApplianceModel();
                    return model;
                })
                .collect(Collectors.toList());

        when(applianceModelService.getAllApplianceModels()).thenReturn(applianceModels);
        when(applianceModelConverter.toDto(any(ApplianceModel.class))).thenReturn(applianceModelDTOs.get(0));

        ResponseEntity<ApiResponse<List<ApplianceModelDTO>>> responseEntity = applianceModelController.getAllApplianceModels();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Appliance models found", responseEntity.getBody().getMessage());
        assertEquals(applianceModelDTOs, responseEntity.getBody().getData());
    }

    /**
     * Tests the getApplianceModelById method of ApplianceModelController with an existing ID.
     * Verifies that the appliance model with the given ID is retrieved successfully.
     */
    @Test
    void getApplianceModelById() {
        ApplianceModelDTO applianceModelDTO = new ApplianceModelDTO();
        applianceModelDTO.setId(1);
        ApplianceModel applianceModel = new ApplianceModel();
        applianceModel.setId(1);

        when(applianceModelService.getApplianceModelById(1)).thenReturn(applianceModel);
        when(applianceModelConverter.toDto(applianceModel)).thenReturn(applianceModelDTO);

        ResponseEntity<ApiResponse<ApplianceModelDTO>> responseEntity = applianceModelController.getApplianceModelById(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Appliance model found", responseEntity.getBody().getMessage());
        assertEquals(applianceModelDTO, responseEntity.getBody().getData());
    }

    /**
     * Tests the getApplianceModelByModel method of ApplianceModelController with an existing model.
     * Verifies that the appliance model with the given model is retrieved successfully.
     */
    @Test
    void getApplianceModelByModel() {
        ApplianceModelDTO applianceModelDTO = new ApplianceModelDTO();
        applianceModelDTO.setModel("TestModel");
        ApplianceModel applianceModel = new ApplianceModel();
        applianceModel.setModel("TestModel");

        when(applianceModelService.getApplianceModelByModel("TestModel")).thenReturn(Collections.singletonList(applianceModel));
        when(applianceModelConverter.toDto(applianceModel)).thenReturn(applianceModelDTO);

        ResponseEntity<ApiResponse<List<ApplianceModelDTO>>> responseEntity = applianceModelController.getApplianceModelByModel("TestModel");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Appliance models found", responseEntity.getBody().getMessage());
        assertEquals(Collections.singletonList(applianceModelDTO), responseEntity.getBody().getData());
    }

    /**
     * Tests the getApplianceModelsByCriteria method of ApplianceModelController with existing criteria.
     * Verifies that the appliance models matching the criteria are retrieved successfully.
     */
    @Test
    void getApplianceModelsByCriteria() {
        ApplianceModelDTO applianceModelDTO = new ApplianceModelDTO();
        applianceModelDTO.setModel("TestModel");
        ApplianceModel applianceModel = new ApplianceModel();
        applianceModel.setModel("TestModel");

        List<ApplianceModelDTO> applianceModelDTOs = Collections.singletonList(applianceModelDTO);
        List<ApplianceModel> applianceModels = applianceModelDTOs.stream()
                .map(dto -> {
                    ApplianceModel model = new ApplianceModel();
                    // Populate model fields from dto as needed
                    return model;
                })
                .collect(Collectors.toList());

        when(applianceModelService.getApplianceModelsByCriteria(any(ApplianceModel.class))).thenReturn(applianceModels);
        when(applianceModelConverter.toDto(any(ApplianceModel.class))).thenReturn(applianceModelDTO);

        ResponseEntity<ApiResponse<List<ApplianceModelDTO>>> responseEntity = applianceModelController.getApplianceModelsByCriteria("TestModel", ApplianceCategory.TV, Brand.SAMSUNG, Year.of(2020));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Appliance models found", responseEntity.getBody().getMessage());
        assertEquals(applianceModelDTOs, responseEntity.getBody().getData());
    }

    /**
     * Tests the createApplianceModel method of ApplianceModelController with a valid model.
     * Verifies that the appliance model is successfully created.
     */
    @Test
    void createApplianceModel() {
        ApplianceModelDTO applianceModelDTO = new ApplianceModelDTO();
        applianceModelDTO.setModel("TestModel");
        ApplianceModel applianceModel = new ApplianceModel();
        applianceModel.setModel("TestModel");

        when(applianceModelService.saveApplianceModel(applianceModel)).thenReturn(applianceModel);
        when(applianceModelConverter.toDto(applianceModel)).thenReturn(applianceModelDTO);

        ResponseEntity<ApiResponse<ApplianceModelDTO>> expectedResponseEntity = new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.CREATED.value(), "Appliance model created", applianceModelDTO),
                HttpStatus.CREATED
        );

        ResponseEntity<ApiResponse<ApplianceModelDTO>> responseEntity = applianceModelController.createApplianceModel(applianceModel);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(HttpStatus.CREATED.value(), responseEntity.getBody().getStatus());
        assertEquals("Appliance model created", responseEntity.getBody().getMessage());
        assertEquals(applianceModelDTO, responseEntity.getBody().getData());
    }

    /**
     * Tests the updateApplianceModel method of ApplianceModelController with an existing model.
     * Verifies that the appliance model is successfully updated.
     */
    @Test
    void updateApplianceModel() {
        ApplianceModelDTO applianceModelDTO = new ApplianceModelDTO();
        applianceModelDTO.setId(1);
        applianceModelDTO.setModel("UpdatedModel");
        ApplianceModel applianceModel = new ApplianceModel();
        applianceModel.setId(1);
        applianceModel.setModel("UpdatedModel");

        when(applianceModelService.updateApplianceModel(applianceModel)).thenReturn(applianceModel);
        when(applianceModelConverter.toDto(applianceModel)).thenReturn(applianceModelDTO);

        ResponseEntity<ApiResponse<ApplianceModelDTO>> expectedResponseEntity = new ResponseEntity<>(
                new ApiResponse<>(HttpStatus.OK.value(), "Appliance model updated", applianceModelDTO),
                HttpStatus.OK
        );

        ResponseEntity<ApiResponse<ApplianceModelDTO>> responseEntity = applianceModelController.updateApplianceModel(1, applianceModel);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Appliance model updated", responseEntity.getBody().getMessage());
        assertEquals(applianceModelDTO, responseEntity.getBody().getData());
    }
}
