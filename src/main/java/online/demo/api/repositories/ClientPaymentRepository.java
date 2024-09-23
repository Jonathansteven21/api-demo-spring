package online.demo.api.repositories;

import online.demo.api.entities.ClientPayment;
import online.demo.api.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

// This interface defines methods to access ClientPayment entities in the database
public interface ClientPaymentRepository extends JpaRepository<ClientPayment, Integer> {

    // Method to find a ClientPayments by its associated Order
    List<ClientPayment> findByOrder(Order order);

    // Method to find ClientPayments by their date
    List<ClientPayment> findByDate(LocalDate date);
}
