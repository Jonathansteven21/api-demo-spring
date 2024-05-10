package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.ApplianceModel;
import online.lcelectronics.api.enums.ApplianceCategory;
import online.lcelectronics.api.enums.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// This interface defines methods to access ApplianceModel entities in the database
public interface ApplianceModelRepository extends JpaRepository<ApplianceModel, Integer> {

    // Method to find an ApplianceModel by its model
    Optional<ApplianceModel> findByModel(String model);

    // Method to find ApplianceModels whose model contains the specified substring
    Optional<ApplianceModel> findByModelContaining(String model);

    // Method to find ApplianceModels by their ApplianceCategory and Brand
    Optional<ApplianceModel> findByApplianceCategoryAndBrand(ApplianceCategory category, Brand brand);

    // Method to find ApplianceModels by their ApplianceCategory
    Optional<ApplianceModel> findByApplianceCategory(ApplianceCategory category);

    // Method to find ApplianceModels by their Brand
    Optional<ApplianceModel> findByBrand(Brand brand);
}
