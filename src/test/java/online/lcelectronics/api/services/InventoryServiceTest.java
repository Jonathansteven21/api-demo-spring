package online.lcelectronics.api.services;

import online.lcelectronics.api.entities.ApplianceModel;
import online.lcelectronics.api.entities.Image;
import online.lcelectronics.api.entities.Inventory;
import online.lcelectronics.api.exceptions.NotFoundException;
import online.lcelectronics.api.repositories.ApplianceModelRepository;
import online.lcelectronics.api.repositories.ImageRepository;
import online.lcelectronics.api.repositories.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ApplianceModelRepository applianceModelRepository;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        ApplianceModel applianceModel = new ApplianceModel();
        applianceModel.setId(1);
        applianceModel.setModel("Model A");

        Image image = new Image();
        image.setId(1);
        image.setMime("image/jpeg");
        image.setName("Test Image");
        image.setContent(new byte[]{1, 2, 3});

        inventory = new Inventory();
        inventory.setId(1L);
        inventory.setSerial("123456");
        inventory.setName("Test Item");
        inventory.setLastPrice(BigDecimal.valueOf(100));
        inventory.setDateLastPrice(new Date());
        inventory.setCompatibleApplianceModels(List.of(applianceModel));
        inventory.setImages(List.of(image));
    }

    /**
     * Tests the getAllInventoryItems method of InventoryService.
     * Verifies that all inventory items are returned.
     */
    @Test
    void getAllInventoryItems() {
        List<Inventory> inventories = new ArrayList<>();
        inventories.add(inventory);
        when(inventoryRepository.findAll()).thenReturn(inventories);

        List<Inventory> result = inventoryService.getAllInventoryItems();
        assertEquals(1, result.size());
        assertEquals(inventory, result.get(0));
    }

    /**
     * Tests the getInventoryItemById method of InventoryService with an existing ID.
     * Verifies that the correct inventory item is returned.
     */
    @Test
    void getInventoryItemById_existingId() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));

        Inventory result = inventoryService.getInventoryItemById(1L);
        assertEquals(inventory, result);
    }

    /**
     * Tests the getInventoryItemById method of InventoryService with a non-existing ID.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void getInventoryItemById_nonExistingId() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> inventoryService.getInventoryItemById(1L));
    }

    /**
     * Tests the getInventoryItemsBySerial method of InventoryService with an existing serial.
     * Verifies that the correct inventory items are returned.
     */
    @Test
    void getInventoryItemsBySerial_existingSerial() {
        List<Inventory> inventories = List.of(inventory);
        when(inventoryRepository.findBySerial("123456")).thenReturn(inventories);

        List<Inventory> result = inventoryService.getInventoryItemsBySerial("123456");
        assertEquals(1, result.size());
        assertEquals(inventory, result.get(0));
    }

    /**
     * Tests the getInventoryItemsBySerial method of InventoryService with a non-existing serial.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void getInventoryItemsBySerial_nonExistingSerial() {
        when(inventoryRepository.findBySerial("non-existing-serial")).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> inventoryService.getInventoryItemsBySerial("non-existing-serial"));
    }

    /**
     * Tests the saveInventoryItem method of InventoryService with a valid item.
     * Verifies that the item is saved successfully.
     */
    @Test
    void saveInventoryItem_validItem() {
        when(applianceModelRepository.existsById(any(Integer.class))).thenReturn(true);
        when(imageRepository.existsById(any(Integer.class))).thenReturn(true);
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);

        Inventory result = inventoryService.saveInventoryItem(inventory);
        assertEquals(inventory, result);
    }

    /**
     * Tests the saveInventoryItem method of InventoryService with an invalid price.
     * Verifies that an IllegalArgumentException is thrown.
     */
    @Test
    void saveInventoryItem_invalidPrice() {
        inventory.setLastPrice(BigDecimal.ZERO);

        assertThrows(IllegalArgumentException.class, () -> inventoryService.saveInventoryItem(inventory));
    }

    /**
     * Tests the updateInventoryItem method of InventoryService with an existing item.
     * Verifies that the item is updated successfully.
     */
    @Test
    void updateInventoryItem_existingItem() {
        when(inventoryRepository.existsById(1L)).thenReturn(true);
        when(applianceModelRepository.existsById(any(Integer.class))).thenReturn(true);
        when(imageRepository.existsById(any(Integer.class))).thenReturn(true);
        when(inventoryRepository.saveAndFlush(any(Inventory.class))).thenReturn(inventory);

        Inventory result = inventoryService.updateInventoryItem(inventory);
        assertEquals(inventory, result);
    }

    /**
     * Tests the updateInventoryItem method of InventoryService with a non-existing item.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void updateInventoryItem_nonExistingItem() {
        lenient().when(inventoryRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> inventoryService.updateInventoryItem(inventory));
    }

    /**
     * Tests the validateInventoryItem method of InventoryService with an invalid appliance model.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void validateInventoryItem_invalidApplianceModel() {
        when(applianceModelRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> inventoryService.saveInventoryItem(inventory));
    }

    /**
     * Tests the validateInventoryItem method of InventoryService with an invalid image.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void validateInventoryItem_invalidImage() {
        when(applianceModelRepository.existsById(1)).thenReturn(true);
        when(imageRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> inventoryService.saveInventoryItem(inventory));
    }
}
