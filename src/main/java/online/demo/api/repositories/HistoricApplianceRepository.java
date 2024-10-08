package online.demo.api.repositories;

import online.demo.api.entities.ApplianceModel;
import online.demo.api.entities.HistoricAppliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// This interface defines methods to access HistoricAppliance entities in the database
public interface HistoricApplianceRepository extends JpaRepository<HistoricAppliance, String> {

    // Method to find HistoricAppliances by a partial match of their serial number
    List<HistoricAppliance> findBySerialContaining(String serial);

    // Method to find a HistoricAppliance by its associated ApplianceModel
    List<HistoricAppliance> findByModel(ApplianceModel model);

    // Method to find HistoricAppliances by a partial match of their associated ApplianceModel's model name
    @Query("SELECT h FROM HistoricAppliance h WHERE h.model.model LIKE %:modelName%")
    List<HistoricAppliance> findByModelNameContaining(String modelName);

}
