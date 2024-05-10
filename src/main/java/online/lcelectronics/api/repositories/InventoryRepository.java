package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.Inventory;
import online.lcelectronics.api.enums.Brand;
import online.lcelectronics.api.enums.Component;
import online.lcelectronics.api.entities.ApplianceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// This interface defines methods to access Inventory entities in the database
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // Finds an inventory item by its serial number
    Optional<Inventory> findBySerial(String serial);

    // Finds inventory items by their location
    Optional<Inventory> findByLocation(String location);

    // Finds inventory items by their quantity
    Optional<Inventory> findByQuantity(String location);

    // Finds inventory items by the appliance models they are compatible with
    Optional<Inventory> findByCompatibleApplianceModels(ApplianceModel compatibleApplianceModel);

    // Finds inventory items by name containing a given string
    Optional<Inventory> findByNameContaining(String name);

    // Finds inventory items by name containing a given string and a specific component
    Optional<Inventory> findByNameContainingAndComponent(String name, Component component);

    // Finds inventory items by a specific component and compatible appliance models
    Optional<Inventory> findByComponentAndCompatibleApplianceModels(Component component, ApplianceModel compatibleApplianceModel);

    // Finds inventory items by brand and a specific component
    Optional<Inventory> findByBrandAndComponent(Brand brand, Component component);
}
