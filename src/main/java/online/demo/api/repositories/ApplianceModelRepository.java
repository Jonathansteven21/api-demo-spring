package online.demo.api.repositories;

import online.demo.api.entities.ApplianceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

// This interface defines methods to access ApplianceModel entities in the database
public interface ApplianceModelRepository extends JpaRepository<ApplianceModel, Integer>, JpaSpecificationExecutor<ApplianceModel> {

    // Method to find an ApplianceModel by its model
    List<ApplianceModel> findByModel(String model);
    boolean existsByModel(String model);

}
