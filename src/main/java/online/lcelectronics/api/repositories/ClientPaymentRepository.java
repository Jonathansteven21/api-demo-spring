package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.ClientPayment;
import online.lcelectronics.api.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

// This interface defines methods to access ClientPayment entities in the database
public interface ClientPaymentRepository extends JpaRepository<ClientPayment, Integer> {

    // Method to find a ClientPayment by its associated Order
    Optional<ClientPayment> findByOrder(Order order);

    // Method to find ClientPayments by their date
    Optional<ClientPayment> findByDate(LocalDate date);
}
