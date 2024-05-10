package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.Client;
import online.lcelectronics.api.entities.HistoricAppliance;
import online.lcelectronics.api.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

// This interface defines methods to access Order entities in the database
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // Finds orders by the associated client
    Optional<Order> findByClient(Client client);

    // Finds orders by the associated historic appliance
    Optional<Order> findByHistoricAppliance(HistoricAppliance historicAppliance);

    // Finds orders by the created date
    Optional<Order> findByCreatedDate(LocalDate createdDate);
}
