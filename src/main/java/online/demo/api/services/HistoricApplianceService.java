package online.demo.api.services;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import online.demo.api.entities.ApplianceModel;
import online.demo.api.entities.HistoricAppliance;
import online.demo.api.exceptions.NotFoundException;
import online.demo.api.repositories.ApplianceModelRepository;
import online.demo.api.repositories.HistoricApplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
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

    // Retrieve historic appliances by their associated appliance models
    public List<HistoricAppliance> getHistoricApplianceByModel(@NotBlank(message = "Model name cannot be blank") String modelName) {
        List<ApplianceModel> models = applianceModelRepository.findByModel(modelName);
        if (models.isEmpty()) {
            throw new NotFoundException("Appliance models not found with model: " + modelName);
        }
        List<HistoricAppliance> mergedHistoricAppliances = new ArrayList<>();
        for (ApplianceModel model : models) {
            List<HistoricAppliance> historicAppliances = historicApplianceRepository.findByModel(model);
            mergedHistoricAppliances.addAll(historicAppliances);
        }

        if (mergedHistoricAppliances.isEmpty()) {
            throw new NotFoundException("Historic appliances not found for the given appliance model: " + modelName);
        }

        return mergedHistoricAppliances;
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
    public HistoricAppliance saveHistoricAppliance(HistoricAppliance historicAppliance) {
        verifyApplianceModelExists(historicAppliance.getModel());
        return historicApplianceRepository.save(historicAppliance);
    }

    // Update a historic appliance
    @Transactional
    public HistoricAppliance updateHistoricAppliance(HistoricAppliance historicAppliance) {
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
