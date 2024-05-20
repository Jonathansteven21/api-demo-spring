package online.lcelectronics.api.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.ApplianceModel;
import online.lcelectronics.api.entities.Image;
import online.lcelectronics.api.entities.Inventory;
import online.lcelectronics.api.entities.specs.InventorySpecification;
import online.lcelectronics.api.exceptions.NotFoundException;
import online.lcelectronics.api.repositories.ApplianceModelRepository;
import online.lcelectronics.api.repositories.ImageRepository;
import online.lcelectronics.api.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ApplianceModelRepository applianceModelRepository;
    private final ImageRepository imageRepository;


    // Retrieve all inventory items
    public List<Inventory> getAllInventoryItems() {
        return inventoryRepository.findAll();
    }

    // Retrieve an inventory item by its ID
    public Inventory getInventoryItemById(@NotNull(message = "ID cannot be null") Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inventory item not found with ID: " + id));
    }

    // Retrieve inventory items by serial number
    public List<Inventory> getInventoryItemsBySerial(@NotEmpty(message = "Serial number cannot be empty") String serial) {
        List<Inventory> inventoryItems = inventoryRepository.findBySerial(serial);
        if (inventoryItems.isEmpty()) {
            throw new NotFoundException("No inventory items found with serial number: " + serial);
        }
        return inventoryItems;
    }

    // Retrieve inventory items based on specified criteria.
    public List<Inventory> getInventoryByCriteria(Inventory inventory) {
        Specification<Inventory> spec = Specification.where(InventorySpecification.serialContains(inventory.getSerial()))
                .and(InventorySpecification.hasLocation(inventory.getLocation()))
                .and(InventorySpecification.lastPriceLessThanOrEqual(inventory.getLastPrice()))
                .and(inventory.getCompatibleApplianceModels() != null ? InventorySpecification.hasCompatibleApplianceModel(inventory.getCompatibleApplianceModels().get(0).getModel()) : null)
                .and(InventorySpecification.hasComponent(inventory.getComponent()))
                .and(InventorySpecification.hasBrand(inventory.getBrand()))
                .and(InventorySpecification.nameContainsIgnoreCase(inventory.getName()));

        List<Inventory> inventoryItems = inventoryRepository.findAll(spec);
        if (inventoryItems.isEmpty()) {
            throw new NotFoundException("No inventory items found with these specifications");
        }
        return inventoryItems;
    }

    // Save an inventory item
    @Transactional
    public Inventory saveInventoryItem(@Valid Inventory inventoryItem) {
        validateInventoryItem(inventoryItem);
        return inventoryRepository.save(inventoryItem);
    }

    // Update an inventory item
    @Transactional
    public Inventory updateInventoryItem(@Valid Inventory inventoryItem) {
        validateInventoryItem(inventoryItem);
        if (!inventoryRepository.existsById(inventoryItem.getId())) {
            throw new NotFoundException("Inventory item not found with ID: " + inventoryItem.getId());
        }
        return inventoryRepository.saveAndFlush(inventoryItem);
    }

    // Private method to validate inventory item
    private void validateInventoryItem(Inventory inventoryItem) {
        if (inventoryItem.getLastPrice() != null && inventoryItem.getLastPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Last price must be greater than 0");
        }

        if (inventoryItem.getDateLastPrice() != null && inventoryItem.getDateLastPrice().after(new Date())) {
                throw new IllegalArgumentException("Date last price must be in the past or present");
        }

        List<ApplianceModel> applianceModels = inventoryItem.getCompatibleApplianceModels();
        if (applianceModels != null && !applianceModels.isEmpty()) {
            for (ApplianceModel model : applianceModels) {
                if (!applianceModelRepository.existsById(model.getId())) {
                    throw new NotFoundException("Appliance model not found with ID: " + model.getId());
                }
            }
        }

        List<Image> images = inventoryItem.getImages();
        if (images != null && !images.isEmpty()) {
            for (Image image : images) {
                if (!imageRepository.existsById(image.getId())) {
                    throw new NotFoundException("Image not found with ID: " + image.getId());
                }
            }
        }
    }

}
