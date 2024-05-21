package online.lcelectronics.api.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.ApplianceModel;
import online.lcelectronics.api.entities.HistoricAppliance;
import online.lcelectronics.api.exceptions.NotFoundException;
import online.lcelectronics.api.repositories.ApplianceModelRepository;
import online.lcelectronics.api.repositories.HistoricApplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HistoricApplianceService {

    private final HistoricApplianceRepository historicApplianceRepository;
    private final ApplianceModelRepository applianceModelRepository;


    // Retrieve all historic appliances
    public List<HistoricAppliance> getAllHistoricAppliances() {
        return historicApplianceRepository.findAll();
    }

    // Retrieve a historic appliance by its exact serial number
    public HistoricAppliance getHistoricApplianceBySerial(@NotBlank(message = "Serial number cannot be null or empty") String serial) {
        return historicApplianceRepository.findById(serial)
                .orElseThrow(() -> new NotFoundException("Historic appliance not found with serial number: " + serial));
    }

    // Retrieve a historic appliance by its associated appliance model
    public HistoricAppliance getHistoricApplianceByModel(@NotBlank(message = "Model name cannot be blank") String modelName) {
        ApplianceModel model = applianceModelRepository.findByModel(modelName)
                .orElseThrow(() -> new NotFoundException("Appliance model not found with model: " + modelName));

        return historicApplianceRepository.findByModel(model)
                .orElseThrow(() -> new NotFoundException("Historic appliance not found for the given appliance model: " + modelName));
    }

    // Retrieve historic appliances based on a partial match of their serial numbers
    public List<HistoricAppliance> getHistoricAppliancesBySerial(@NotBlank(message = "Serial number cannot be null or empty") String serial) {
        List<HistoricAppliance> historicAppliances = historicApplianceRepository.findBySerialContaining(serial);
        if (historicAppliances.isEmpty()) {
            throw new NotFoundException("Historic appliances not found with serial number containing: " + serial);
        }
        return historicAppliances;
    }

    // Retrieve historic appliances by a partial match of their associated appliance model's model name
    public List<HistoricAppliance> getHistoricApplianceByModelContaining(@NotBlank(message = "Model name cannot be blank") String modelName) {
        List<HistoricAppliance> historicAppliances = historicApplianceRepository.findByModelNameContaining(modelName);
        if (historicAppliances.isEmpty()) {
            throw new NotFoundException("No historic appliances found for the given model: " + modelName);
        }
        return historicAppliances;
    }

    // Save a historic appliance
    @Transactional
    public HistoricAppliance saveHistoricAppliance(@Valid HistoricAppliance historicAppliance) {
        verifyApplianceModelExists(historicAppliance.getModel());
        return historicApplianceRepository.save(historicAppliance);
    }

    // Update a historic appliance
    @Transactional
    public HistoricAppliance updateHistoricAppliance(@Valid HistoricAppliance historicAppliance) {
        verifyApplianceModelExists(historicAppliance.getModel());
        if (!historicApplianceRepository.existsById(historicAppliance.getSerial())) {
            throw new NotFoundException("Historic appliance model not found with ID: " + historicAppliance.getSerial());
        }

        return historicApplianceRepository.saveAndFlush(historicAppliance);
    }

    // Private method to verify if the associated ApplianceModel exists
    private void verifyApplianceModelExists(ApplianceModel applianceModel) {
        Integer modelId = applianceModel.getId();
        if (modelId == null || !applianceModelRepository.existsById(modelId)) {
            throw new NotFoundException("Appliance model not found with ID: " + modelId);
        }
    }

}
