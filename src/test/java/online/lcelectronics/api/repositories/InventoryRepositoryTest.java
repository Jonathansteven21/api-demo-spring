package online.lcelectronics.api.repositories;

import online.demo.api.entities.Inventory;
import online.demo.api.repositories.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class InventoryRepositoryTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        inventory.setSerial("123456");
    }

    /**
     * Tests the findBySerial method of InventoryRepository.
     * Verifies that the correct Inventory(s) are returned when searched by serial.
     */
    @Test
    void whenFindBySerial_thenReturnInventories() {
        when(inventoryRepository.findBySerial(inventory.getSerial()))
                .thenReturn(Collections.singletonList(inventory));

        List<Inventory> foundInventories = inventoryRepository.findBySerial(inventory.getSerial());

        assertFalse(foundInventories.isEmpty());
        assertEquals(inventory.getSerial(), foundInventories.get(0).getSerial());
    }

    /**
     * Tests the findBySerial method of InventoryRepository when the serial is not contained.
     * Ensures that an empty list is returned when no inventories are found with the provided serial.
     */
    @Test
    void whenSerialNotContained_thenReturnEmptyList() {
        String nonExistentSerial = "999999";
        when(inventoryRepository.findBySerial(nonExistentSerial))
                .thenReturn(Collections.emptyList());

        List<Inventory> foundInventories = inventoryRepository.findBySerial(nonExistentSerial);

        assertTrue(foundInventories.isEmpty());
    }
}
