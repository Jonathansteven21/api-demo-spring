package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

// This interface defines methods to access Inventory entities in the database
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>, JpaSpecificationExecutor<Inventory> {

    // Finds inventory items by its serial number
    List<Inventory> findBySerial(String serial);

}
