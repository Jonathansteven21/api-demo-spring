package online.demo.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import online.demo.api.entities.HistoricAppliance;
import online.demo.api.services.HistoricApplianceService;
import online.demo.api.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/historic-appliances")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HistoricApplianceController {

    private final HistoricApplianceService historicApplianceService;

    // Get all historic appliances
    @GetMapping
    public ResponseEntity<ApiResponse<List<HistoricAppliance>>> getAllHistoricAppliances() {
        List<HistoricAppliance> historicAppliances = historicApplianceService.getAllHistoricAppliances();
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Historic appliances found", historicAppliances), HttpStatus.OK);
    }

    // Get historic appliance by serial
    @GetMapping("/{serial}")
    public ResponseEntity<ApiResponse<HistoricAppliance>> getHistoricApplianceBySerial(@PathVariable String serial) {
        HistoricAppliance historicAppliance = historicApplianceService.getHistoricApplianceBySerial(serial);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Historic appliance found", historicAppliance), HttpStatus.OK);
    }

    // Get historic appliance by model
    @GetMapping("/model/{modelName}")
    public ResponseEntity<ApiResponse<List<HistoricAppliance>>> getHistoricApplianceByModel(@PathVariable String modelName) {
        List<HistoricAppliance> historicAppliances = historicApplianceService.getHistoricApplianceByModel(modelName);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Historic appliances found", historicAppliances), HttpStatus.OK);
    }

    // Get historic appliances by serial containing
    @GetMapping("/serial")
    public ResponseEntity<ApiResponse<List<HistoricAppliance>>> getHistoricAppliancesBySerial(@RequestParam String serial) {
        List<HistoricAppliance> historicAppliances = historicApplianceService.getHistoricAppliancesBySerial(serial);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Historic appliances found", historicAppliances), HttpStatus.OK);
    }

    // Get historic appliance by model containing
    @GetMapping("/model-containing")
    public ResponseEntity<ApiResponse<List<HistoricAppliance>>> getHistoricApplianceByModelContaining(@RequestParam String modelName) {
        List<HistoricAppliance> historicAppliances = historicApplianceService.getHistoricApplianceByModelContaining(modelName);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Historic appliances found", historicAppliances), HttpStatus.OK);
    }

    // Save a historic appliance
    @PostMapping
    public ResponseEntity<ApiResponse<HistoricAppliance>> saveHistoricAppliance(@Valid @RequestBody HistoricAppliance historicAppliance) {
        HistoricAppliance savedAppliance = historicApplianceService.saveHistoricAppliance(historicAppliance);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CREATED.value(), "Historic appliance saved", savedAppliance), HttpStatus.CREATED);
    }

    // Update a historic appliance
    @PutMapping("/{serial}")
    public ResponseEntity<ApiResponse<HistoricAppliance>> updateHistoricAppliance(@PathVariable String serial, @Valid @RequestBody HistoricAppliance historicAppliance) {
        if (!serial.equals(historicAppliance.getSerial())) {
            throw new IllegalArgumentException("Serial number in path does not match the one in the request body");
        }
        HistoricAppliance updatedAppliance = historicApplianceService.updateHistoricAppliance(historicAppliance);
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Historic appliance updated", updatedAppliance), HttpStatus.OK);
    }
}
