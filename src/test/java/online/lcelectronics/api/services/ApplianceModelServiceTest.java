package online.lcelectronics.api.services;

import jakarta.validation.ConstraintViolationException;
import online.demo.api.entities.ApplianceModel;
import online.demo.api.enums.ApplianceCategory;
import online.demo.api.enums.Brand;
import online.demo.api.exceptions.NotFoundException;
import online.demo.api.repositories.ApplianceModelRepository;
import online.demo.api.services.ApplianceModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplianceModelServiceTest {

    @Mock
    private ApplianceModelRepository applianceModelRepository;

    @InjectMocks
    private ApplianceModelService applianceModelService;

    private ApplianceModel applianceModel;

    @BeforeEach
    void setUp() {
        applianceModel = new ApplianceModel();
        applianceModel.setId(1);
        applianceModel.setModel("Test Model");
        applianceModel.setApplianceCategory(ApplianceCategory.TV);
        applianceModel.setBrand(Brand.LG);
        applianceModel.setManufactureYear(Year.of(2020));
    }

    /**
     * Tests the getAllApplianceModels method of ApplianceModelService.
     * Verifies that all appliance models are retrieved successfully.
     */
    @Test
    void getAllApplianceModels() {
        List<ApplianceModel> models = new ArrayList<>();
        models.add(applianceModel);
        when(applianceModelRepository.findAll()).thenReturn(models);

        List<ApplianceModel> result = applianceModelService.getAllApplianceModels();
        assertEquals(1, result.size());
        assertEquals(applianceModel, result.get(0));
    }

    /**
     * Tests the getApplianceModelById method of ApplianceModelService with an existing ID.
     * Verifies that the appliance model with the given ID is retrieved successfully.
     */
    @Test
    void getApplianceModelById_existingId() throws NotFoundException {
        when(applianceModelRepository.findById(1)).thenReturn(Optional.of(applianceModel));

        ApplianceModel result = applianceModelService.getApplianceModelById(1);
        assertEquals(applianceModel, result);
    }

    /**
     * Tests the getApplianceModelById method of ApplianceModelService with a non-existing ID.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void getApplianceModelById_nonExistingId() {
        when(applianceModelRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> applianceModelService.getApplianceModelById(1));
    }

    /**
     * Tests the getApplianceModelByModel method of ApplianceModelService with an existing model.
     * Verifies that the appliance model with the given model is retrieved successfully.
     */
    @Test
    void getApplianceModelByModel_existingModel() throws NotFoundException {
        when(applianceModelRepository.findByModel("Test Model")).thenReturn(Collections.singletonList(applianceModel));

        List<ApplianceModel> result = applianceModelService.getApplianceModelByModel("Test Model");
        assertEquals(applianceModel, result.get(0));
    }

    /**
     * Tests the getApplianceModelByModel method of ApplianceModelService with a non-existing model.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void getApplianceModelByModel_nonExistingModel() {
        when(applianceModelRepository.findByModel("Test Model")).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> applianceModelService.getApplianceModelByModel("Test Model"));
    }

    /**
     * Tests the getApplianceModelsByCriteria method of ApplianceModelService with existing criteria.
     * Verifies that appliance models with the given criteria are retrieved successfully.
     */
    @Test
    void getApplianceModelsByCriteria_existingCriteria() {
        List<ApplianceModel> models = new ArrayList<>();
        models.add(applianceModel);
        when(applianceModelRepository.findAll(any(Specification.class))).thenReturn(models);

        List<ApplianceModel> result = applianceModelService.getApplianceModelsByCriteria(applianceModel);
        assertEquals(1, result.size());
        assertEquals(applianceModel, result.get(0));
    }

    /**
     * Tests the getApplianceModelsByCriteria method of ApplianceModelService with non-existing criteria.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void getApplianceModelsByCriteria_nonExistingCriteria() {
        when(applianceModelRepository.findAll(any(Specification.class))).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> applianceModelService.getApplianceModelsByCriteria(applianceModel));
    }

    /**
     * Tests the saveApplianceModel method of ApplianceModelService with a valid model.
     * Verifies that the appliance model is successfully saved.
     */
    @Test
    void saveApplianceModel_validModel() {
        when(applianceModelRepository.save(applianceModel)).thenReturn(applianceModel);

        ApplianceModel result = applianceModelService.saveApplianceModel(applianceModel);
        assertEquals(applianceModel, result);
    }

    /**
     * Tests the saveApplianceModel method of ApplianceModelService with an invalid model.
     * Verifies that a ConstraintViolationException is thrown.
     */
    @Test
    void saveApplianceModel_invalidModel() {
        doThrow(ConstraintViolationException.class).when(applianceModelRepository).save(any());

        assertThrows(ConstraintViolationException.class, () -> applianceModelService.saveApplianceModel(applianceModel));
    }

    /**
     * Tests the updateApplianceModel method of ApplianceModelService with an existing model.
     * Verifies that the appliance model is successfully updated.
     */
    @Test
    void updateApplianceModel_existingModel() {
        when(applianceModelRepository.existsById(1)).thenReturn(true);
        when(applianceModelRepository.saveAndFlush(applianceModel)).thenReturn(applianceModel);

        ApplianceModel result = applianceModelService.updateApplianceModel(applianceModel);
        assertEquals(applianceModel, result);
    }

    /**
     * Tests the updateApplianceModel method of ApplianceModelService with a non-existing model.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void updateApplianceModel_nonExistingModel() {
        when(applianceModelRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> applianceModelService.updateApplianceModel(applianceModel));
    }

    /**
     * Tests the updateApplianceModel method of ApplianceModelService with a null ID.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    void updateApplianceModel_nullId() {
        applianceModel.setId(null);

        assertThrows(IllegalArgumentException.class, () -> applianceModelService.updateApplianceModel(applianceModel));
    }

}
