package online.lcelectronics.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.ApplianceModel;
import online.lcelectronics.api.entities.Inventory;
import online.lcelectronics.api.enums.Brand;
import online.lcelectronics.api.enums.Component;
import online.lcelectronics.api.services.InventoryService;
import online.lcelectronics.api.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InventoryController {

    private final InventoryService inventoryService;

    // Get all inventory items
    @GetMapping
    public ResponseEntity<ApiResponse<List<Inventory>>> getAllInventoryItems() {
        List<Inventory> inventoryItems = inventoryService.getAllInventoryItems();
        ApiResponse<List<Inventory>> response = new ApiResponse<>(HttpStatus.OK.value(), "Inventory items found", inventoryItems);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get inventory item by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Inventory>> getInventoryItemById(@PathVariable Long id) {
        Inventory inventoryItem = inventoryService.getInventoryItemById(id);
        ApiResponse<Inventory> response = new ApiResponse<>(HttpStatus.OK.value(), "Inventory item found", inventoryItem);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get inventory items by serial
    @GetMapping("/serial/{serial}")
    public ResponseEntity<ApiResponse<List<Inventory>>> getInventoryItemsBySerial(@PathVariable String serial) {
        List<Inventory> inventoryItems = inventoryService.getInventoryItemsBySerial(serial);
        ApiResponse<List<Inventory>> response = new ApiResponse<>(HttpStatus.OK.value(), "Inventory items found", inventoryItems);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get inventory items by criteria
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Inventory>>> getInventoryByCriteria(
            @RequestParam(required = false) String serial,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) BigDecimal lastPrice,
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) Component component,
            @RequestParam(required = false) Brand brand,
            @RequestParam(required = false) String name) {

        Inventory inventory = new Inventory();
        inventory.setSerial(serial);
        inventory.setLocation(location);
        inventory.setLastPrice(lastPrice);
        if (modelName != null) {
            ApplianceModel model = new ApplianceModel();
            model.setModel(modelName);
            inventory.setCompatibleApplianceModels(List.of(model));
        }
        inventory.setComponent(component);
        inventory.setBrand(brand);
        inventory.setName(name);

        List<Inventory> inventoryItems = inventoryService.getInventoryByCriteria(inventory);
        ApiResponse<List<Inventory>> response = new ApiResponse<>(HttpStatus.OK.value(), "Inventory items found", inventoryItems);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Save a new inventory item
    @PostMapping
    public ResponseEntity<ApiResponse<Inventory>> saveInventoryItem(@Valid @RequestBody Inventory inventoryItem) {
        inventoryItem.setId(null);
        Inventory savedInventoryItem = inventoryService.saveInventoryItem(inventoryItem);
        ApiResponse<Inventory> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Inventory item created", savedInventoryItem);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update an existing inventory item
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Inventory>> updateInventoryItem(@PathVariable Long id, @Valid @RequestBody Inventory inventoryItem) {
        if (!id.equals(inventoryItem.getId())) {
            throw new IllegalArgumentException("ID in path does not match ID in inventory item");
        }
        Inventory updatedInventoryItem = inventoryService.updateInventoryItem(inventoryItem);
        ApiResponse<Inventory> response = new ApiResponse<>(HttpStatus.OK.value(), "Inventory item updated", updatedInventoryItem);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
