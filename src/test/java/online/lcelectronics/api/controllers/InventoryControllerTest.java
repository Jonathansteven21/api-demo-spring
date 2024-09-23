package online.lcelectronics.api.controllers;

import online.demo.api.controllers.InventoryController;
import online.demo.api.entities.Inventory;
import online.demo.api.enums.Brand;
import online.demo.api.enums.Component;
import online.demo.api.services.InventoryService;
import online.demo.api.util.ApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    @Mock
    InventoryService inventoryService;

    @InjectMocks
    InventoryController inventoryController;

    /**
     * Tests the getAllInventoryItems method of InventoryController.
     * Verifies that all inventory items are retrieved successfully.
     */
    @Test
    void getAllInventoryItems() {
        List<Inventory> inventoryItems = new ArrayList<>();
        inventoryItems.add(new Inventory());
        inventoryItems.add(new Inventory());

        when(inventoryService.getAllInventoryItems()).thenReturn(inventoryItems);

        ResponseEntity<ApiResponse<List<Inventory>>> responseEntity = inventoryController.getAllInventoryItems();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Inventory items found", responseEntity.getBody().getMessage());
        assertEquals(inventoryItems, responseEntity.getBody().getData());
    }

    /**
     * Tests the getInventoryItemById method of InventoryController with an existing ID.
     * Verifies that the inventory item with the given ID is retrieved successfully.
     */
    @Test
    void getInventoryItemById() {
        Inventory inventoryItem = new Inventory();
        inventoryItem.setId(1L);

        when(inventoryService.getInventoryItemById(1L)).thenReturn(inventoryItem);

        ResponseEntity<ApiResponse<Inventory>> responseEntity = inventoryController.getInventoryItemById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Inventory item found", responseEntity.getBody().getMessage());
        assertEquals(inventoryItem, responseEntity.getBody().getData());
    }

    /**
     * Tests the getInventoryItemsBySerial method of InventoryController with an existing serial.
     * Verifies that the inventory items with the given serial are retrieved successfully.
     */
    @Test
    void getInventoryItemsBySerial() {
        List<Inventory> inventoryItems = new ArrayList<>();
        inventoryItems.add(new Inventory());
        inventoryItems.add(new Inventory());

        when(inventoryService.getInventoryItemsBySerial("serial")).thenReturn(inventoryItems);

        ResponseEntity<ApiResponse<List<Inventory>>> responseEntity = inventoryController.getInventoryItemsBySerial("serial");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Inventory items found", responseEntity.getBody().getMessage());
        assertEquals(inventoryItems, responseEntity.getBody().getData());
    }

    /**
     * Tests the getInventoryByCriteria method of InventoryController with specified criteria.
     * Verifies that the inventory items matching the criteria are retrieved successfully.
     */
    @Test
    void getInventoryByCriteria() {
        Inventory inventory = new Inventory();
        List<Inventory> inventoryItems = Collections.singletonList(inventory);

        when(inventoryService.getInventoryByCriteria(any(Inventory.class))).thenReturn(inventoryItems);
        ResponseEntity<ApiResponse<List<Inventory>>> responseEntity = inventoryController.getInventoryByCriteria("ABC123", "Warehouse1", new BigDecimal("199.99"), "ModelX", Component.ACCESSORIES, Brand.LG, "InventoryItem1");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Inventory items found", responseEntity.getBody().getMessage());
        assertEquals(inventoryItems, responseEntity.getBody().getData());
    }

    /**
     * Tests the saveInventoryItem method of InventoryController with a valid inventory item.
     * Verifies that the inventory item is successfully saved.
     */
    @Test
    void saveInventoryItem() {
        Inventory inventoryItem = new Inventory();
        inventoryItem.setId(1L);

        when(inventoryService.saveInventoryItem(inventoryItem)).thenReturn(inventoryItem);

        ResponseEntity<ApiResponse<Inventory>> responseEntity = inventoryController.saveInventoryItem(inventoryItem);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(HttpStatus.CREATED.value(), responseEntity.getBody().getStatus());
        assertEquals("Inventory item created", responseEntity.getBody().getMessage());
        assertEquals(inventoryItem, responseEntity.getBody().getData());
    }

    /**
     * Tests the updateInventoryItem method of InventoryController with an existing inventory item.
     * Verifies that the inventory item is successfully updated.
     */
    @Test
    void updateInventoryItem() {
        Inventory inventoryItem = new Inventory();
        inventoryItem.setId(1L);

        when(inventoryService.updateInventoryItem(inventoryItem)).thenReturn(inventoryItem);

        ResponseEntity<ApiResponse<Inventory>> responseEntity = inventoryController.updateInventoryItem(1L, inventoryItem);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Inventory item updated", responseEntity.getBody().getMessage());
        assertEquals(inventoryItem, responseEntity.getBody().getData());
    }
}
