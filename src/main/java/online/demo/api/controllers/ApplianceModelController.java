package online.demo.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online.demo.api.util.ApiResponse;
import online.demo.api.converters.ApplianceModelConverter;
import online.demo.api.dto.ApplianceModelDTO;
import online.demo.api.entities.ApplianceModel;
import online.demo.api.enums.ApplianceCategory;
import online.demo.api.enums.Brand;
import online.demo.api.services.ApplianceModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/appliance-models")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplianceModelController {

    private final ApplianceModelService applianceModelService;
    private final ApplianceModelConverter applianceModelConverter;

    // Get all appliance models
    @GetMapping
    public ResponseEntity<ApiResponse<List<ApplianceModelDTO>>> getAllApplianceModels() {
        List<ApplianceModel> applianceModels = applianceModelService.getAllApplianceModels();
        List<ApplianceModelDTO> applianceModelDTOs = applianceModels.stream()
                .map(applianceModelConverter::toDto).toList();
        ApiResponse<List<ApplianceModelDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "Appliance models found", applianceModelDTOs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get an appliance model by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApplianceModelDTO>> getApplianceModelById(@PathVariable Integer id) {
        ApplianceModel applianceModel = applianceModelService.getApplianceModelById(id);
        ApplianceModelDTO applianceModelDTO = applianceModelConverter.toDto(applianceModel);
        ApiResponse<ApplianceModelDTO> response = new ApiResponse<>(HttpStatus.OK.value(), "Appliance model found", applianceModelDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get an appliance model by its model
    @GetMapping("/model/{model}")
    public ResponseEntity<ApiResponse<List<ApplianceModelDTO>>> getApplianceModelByModel(@PathVariable String model) {
        List<ApplianceModel> applianceModels = applianceModelService.getApplianceModelByModel(model);
        List<ApplianceModelDTO> applianceModelDTOs = applianceModels.stream()
                .map(applianceModelConverter::toDto).toList();
        ApiResponse<List<ApplianceModelDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "Appliance models found", applianceModelDTOs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get appliance models by criteria
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ApplianceModelDTO>>> getApplianceModelsByCriteria(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) ApplianceCategory applianceCategory,
            @RequestParam(required = false) Brand brand,
            @RequestParam(required = false) Year manufactureYear) {

        ApplianceModel searchCriteria = new ApplianceModel();
        searchCriteria.setModel(model);
        searchCriteria.setApplianceCategory(applianceCategory);
        searchCriteria.setBrand(brand);
        searchCriteria.setManufactureYear(manufactureYear);

        List<ApplianceModel> applianceModels = applianceModelService.getApplianceModelsByCriteria(searchCriteria);
        List<ApplianceModelDTO> applianceModelDTOs = applianceModels.stream()
                .map(applianceModelConverter::toDto)
                .toList();
        ApiResponse<List<ApplianceModelDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "Appliance models found", applianceModelDTOs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Create a new appliance model
    @PostMapping
    public ResponseEntity<ApiResponse<ApplianceModelDTO>> createApplianceModel(@Valid @RequestBody ApplianceModel applianceModel) {
        applianceModel.setId(null);
        ApplianceModel savedApplianceModel = applianceModelService.saveApplianceModel(applianceModel);
        ApplianceModelDTO savedApplianceModelDTO = applianceModelConverter.toDto(savedApplianceModel);
        ApiResponse<ApplianceModelDTO> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Appliance model created", savedApplianceModelDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ApplianceModelDTO>> updateApplianceModel(@Valid @PathVariable Integer id, @RequestBody ApplianceModel applianceModel) {
        if (!id.equals(applianceModel.getId())) {
            throw new IllegalArgumentException("ID in path does not match the one in the request body");
        }
        ApplianceModel updatedApplianceModel = applianceModelService.updateApplianceModel(applianceModel);
        ApplianceModelDTO updatedApplianceModelDTO = applianceModelConverter.toDto(updatedApplianceModel);
        ApiResponse<ApplianceModelDTO> response = new ApiResponse<>(HttpStatus.OK.value(), "Appliance model updated", updatedApplianceModelDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
