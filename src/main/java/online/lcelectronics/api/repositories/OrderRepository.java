package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.Client;
import online.lcelectronics.api.entities.HistoricAppliance;
import online.lcelectronics.api.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

// This interface defines methods to access Order entities in the database
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // Finds orders by the associated client
    List<Order> findByClient(Client client);

    // Finds orders by the associated historic appliance
    List<Order> findByHistoricAppliance(HistoricAppliance historicAppliance);

    // Finds orders by the created date
    List<Order> findByCreatedDate(LocalDate createdDate);
}
