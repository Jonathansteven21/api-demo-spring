package online.lcelectronics.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.Inventory;
import online.lcelectronics.api.enums.Brand;
import online.lcelectronics.api.enums.Component;
import online.lcelectronics.api.entities.ApplianceModel;
import online.lcelectronics.api.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    // Retrieve all inventory items
    public List<Inventory> getAllInventoryItems() {
        return inventoryRepository.findAll();
    }

    // Retrieve an inventory item by its ID
    public Inventory getInventoryItemById(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    // Retrieve inventory items by serial number
    public List<Inventory> getInventoryItemsBySerial(String serial) {
        return inventoryRepository.findBySerial(serial);
    }

    // Retrieve inventory items by location
    public List<Inventory> getInventoryItemsByLocation(String location) {
        return inventoryRepository.findByLocation(location);
    }

    // Retrieve inventory items by quantity
    public List<Inventory> getInventoryItemsByQuantity(String location) {
        return inventoryRepository.findByQuantity(location);
    }

    // Retrieve inventory items by compatible appliance models
    public List<Inventory> getInventoryItemsByCompatibleApplianceModels(ApplianceModel compatibleApplianceModel) {
        return inventoryRepository.findByCompatibleApplianceModels(compatibleApplianceModel);
    }

    // Retrieve inventory items by name containing a given string
    public List<Inventory> getInventoryItemsByNameContaining(String name) {
        return inventoryRepository.findByNameContaining(name);
    }

    // Retrieve inventory items by name containing a given string and a specific component
    public List<Inventory> getInventoryItemsByNameContainingAndComponent(String name, Component component) {
        return inventoryRepository.findByNameContainingAndComponent(name, component);
    }

    // Retrieve inventory items by a specific component and compatible appliance models
    public List<Inventory> getInventoryItemsByComponentAndCompatibleApplianceModels(Component component, ApplianceModel compatibleApplianceModel) {
        return inventoryRepository.findByComponentAndCompatibleApplianceModels(component, compatibleApplianceModel);
    }

    // Retrieve inventory items by brand and a specific component
    public List<Inventory> getInventoryItemsByBrandAndComponent(Brand brand, Component component) {
        return inventoryRepository.findByBrandAndComponent(brand, component);
    }

    // Save an inventory item
    @Transactional
    public Inventory saveInventoryItem(Inventory inventoryItem) {
        return inventoryRepository.save(inventoryItem);
    }

    // Update an inventory item
    @Transactional
    public Inventory updateInventoryItem(Inventory inventoryItem) {
        return inventoryRepository.saveAndFlush(inventoryItem);
    }
}
