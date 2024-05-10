package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.ApplianceModel;
import online.lcelectronics.api.entities.HistoricAppliance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// This interface defines methods to access HistoricAppliance entities in the database
public interface HistoricApplianceRepository extends JpaRepository<HistoricAppliance, String> {

    // Method to find a HistoricAppliance by a partial match of its serial number
    Optional<HistoricAppliance> findBySerialContaining(String serial);

    // Method to find a HistoricAppliance by its associated ApplianceModel
    Optional<HistoricAppliance> findByModel(ApplianceModel model);

    // Method to find a HistoricAppliance by a partial match of its associated ApplianceModel's model name
    Optional<HistoricAppliance> findByModelContaining(String model);
}
