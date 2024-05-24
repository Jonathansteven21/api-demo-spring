package online.lcelectronics.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.ApplianceModel;
import online.lcelectronics.api.enums.ApplianceCategory;
import online.lcelectronics.api.enums.Brand;
import online.lcelectronics.api.services.ApplianceModelService;
import online.lcelectronics.api.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/api/appliance-models")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplianceModelController {

    private final ApplianceModelService applianceModelService;

    // Get all appliance models
    @GetMapping
    public ResponseEntity<ApiResponse<List<ApplianceModel>>> getAllApplianceModels() {
        List<ApplianceModel> applianceModels = applianceModelService.getAllApplianceModels();
        ApiResponse<List<ApplianceModel>> response = new ApiResponse<>(HttpStatus.OK.value(), "Appliance models found", applianceModels);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get an appliance model by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApplianceModel>> getApplianceModelById(@PathVariable Integer id) {
        ApplianceModel applianceModel = applianceModelService.getApplianceModelById(id);
        ApiResponse<ApplianceModel> response = new ApiResponse<>(HttpStatus.OK.value(), "Appliance model found", applianceModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get an appliance model by its model
    @GetMapping("/model/{model}")
    public ResponseEntity<ApiResponse<ApplianceModel>> getApplianceModelByModel(@PathVariable String model) {
        ApplianceModel applianceModel = applianceModelService.getApplianceModelByModel(model);
        ApiResponse<ApplianceModel> response = new ApiResponse<>(HttpStatus.OK.value(), "Appliance model found", applianceModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get appliance models by criteria
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ApplianceModel>>> getApplianceModelsByCriteria(
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
        ApiResponse<List<ApplianceModel>> response = new ApiResponse<>(HttpStatus.OK.value(), "Appliance models found", applianceModels);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Create a new appliance model
    @PostMapping
    public ResponseEntity<ApiResponse<ApplianceModel>> createApplianceModel(@Valid @RequestBody ApplianceModel applianceModel) {
        applianceModel.setId(null);
        ApplianceModel savedApplianceModel = applianceModelService.saveApplianceModel(applianceModel);
        ApiResponse<ApplianceModel> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Appliance model created", savedApplianceModel);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update an existing appliance model
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ApplianceModel>> updateApplianceModel(@Valid @PathVariable Integer id, @RequestBody ApplianceModel applianceModel) {
        if (!id.equals(applianceModel.getId())) {
            throw new IllegalArgumentException("ID in path does not match the one in the request body");
        }
        ApplianceModel updatedApplianceModel = applianceModelService.updateApplianceModel(applianceModel);
        ApiResponse<ApplianceModel> response = new ApiResponse<>(HttpStatus.OK.value(), "Appliance model updated", updatedApplianceModel);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
