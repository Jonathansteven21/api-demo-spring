package online.lcelectronics.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.ClientPayment;
import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.repositories.ClientPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClientPaymentService {

    private final ClientPaymentRepository clientPaymentRepository;

    // Retrieve all client payments
    public List<ClientPayment> getAllClientPayments() {
        return clientPaymentRepository.findAll();
    }

    // Retrieve a client payment by its ID
    public ClientPayment getClientPaymentById(Integer id) {
        Optional<ClientPayment> optionalClientPayment = clientPaymentRepository.findById(id);
        return optionalClientPayment.orElse(null);
    }

    // Retrieve client payments by associated order
    public List<ClientPayment> getClientPaymentsByOrder(Order order) {
        return clientPaymentRepository.findByOrder(order);
    }

    // Retrieve client payments by date
    public List<ClientPayment> getClientPaymentsByDate(LocalDate date) {
        return clientPaymentRepository.findByDate(date);
    }

    // Save a new client payment
    @Transactional
    public ClientPayment saveClientPayment(ClientPayment clientPayment) {
        return clientPaymentRepository.save(clientPayment);
    }

    // Update an existing client payment
    @Transactional
    public ClientPayment updateClientPayment(ClientPayment clientPayment) {
        return clientPaymentRepository.saveAndFlush(clientPayment);
    }
}
