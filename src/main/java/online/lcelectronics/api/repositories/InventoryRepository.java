package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.Inventory;
import online.lcelectronics.api.enums.Brand;
import online.lcelectronics.api.enums.Component;
import online.lcelectronics.api.entities.ApplianceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// This interface defines methods to access Inventory entities in the database
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // Finds inventory items by its serial number
    List<Inventory> findBySerial(String serial);

    // Finds inventory items by their location
    List<Inventory> findByLocation(String location);

    // Finds inventory items by their quantity
    List<Inventory> findByQuantity(String location);

    // Finds inventory items by the appliance models they are compatible with
    List<Inventory> findByCompatibleApplianceModels(ApplianceModel compatibleApplianceModel);

    // Finds inventory items by name containing a given string
    List<Inventory> findByNameContaining(String name);

    // Finds inventory items by name containing a given string and a specific component
    List<Inventory> findByNameContainingAndComponent(String name, Component component);

    // Finds inventory items by a specific component and compatible appliance models
    List<Inventory> findByComponentAndCompatibleApplianceModels(Component component, ApplianceModel compatibleApplianceModel);

    // Finds inventory items by brand and a specific component
    List<Inventory> findByBrandAndComponent(Brand brand, Component component);
}
