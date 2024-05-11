package online.lcelectronics.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.ApplianceModel;
import online.lcelectronics.api.entities.HistoricAppliance;
import online.lcelectronics.api.repositories.HistoricApplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HistoricApplianceService {

    private final HistoricApplianceRepository historicApplianceRepository;

    // Retrieve all historic appliances
    public List<HistoricAppliance> getAllHistoricAppliances() {
        return historicApplianceRepository.findAll();
    }

    // Retrieve a historic appliance by its exact serial number
    public HistoricAppliance getHistoricApplianceBySerial(String serial) {
        Optional<HistoricAppliance> optionalHistoricAppliance = historicApplianceRepository.findById(serial);
        return optionalHistoricAppliance.orElse(null);
    }

    // Retrieve historic appliances based on a partial match of their serial numbers
    public List<HistoricAppliance> getHistoricAppliancesBySerial(String serial) {
        return historicApplianceRepository.findBySerialContaining(serial);
    }

    // Retrieve a historic appliance by its associated appliance model
    public HistoricAppliance getHistoricApplianceByModel(ApplianceModel model) {
        Optional<HistoricAppliance> optionalHistoricAppliance = historicApplianceRepository.findByModel(model);
        return optionalHistoricAppliance.orElse(null);
    }

    // Retrieve historic appliances by a partial match of their associated appliance model's model name
    public List<HistoricAppliance> getHistoricApplianceByModelContaining(String model) {
        return historicApplianceRepository.findByModelContaining(model);
    }

    // Save a historic appliance
    @Transactional
    public HistoricAppliance saveHistoricAppliance(HistoricAppliance historicAppliance) {
        return historicApplianceRepository.save(historicAppliance);
    }

    // Update a historic appliance
    @Transactional
    public HistoricAppliance updateHistoricAppliance(HistoricAppliance historicAppliance) {
        return historicApplianceRepository.saveAndFlush(historicAppliance);
    }
}
