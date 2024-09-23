package online.demo.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.demo.api.entities.ClientPayment;
import online.demo.api.entities.Order;
import online.demo.api.exceptions.NotFoundException;
import online.demo.api.repositories.ClientPaymentRepository;
import online.demo.api.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClientPaymentService {

    private final ClientPaymentRepository clientPaymentRepository;
    private final OrderRepository orderRepository;

    // Retrieve all client payments
    public List<ClientPayment> getAllClientPayments() {
        return clientPaymentRepository.findAll();
    }

    // Retrieve a client payment by its ID
    public ClientPayment getClientPaymentById(@NotNull(message = "ID cannot be null") Integer id) {
        return clientPaymentRepository.findById(id).orElseThrow(() -> new NotFoundException("Client payment not found with ID: " + id));
    }

    // Retrieve client payments by associated order
    public List<ClientPayment> getClientPaymentsByOrder(@NotNull(message = "Order cannot be null") Order order) {
        List<ClientPayment> clientPayments = clientPaymentRepository.findByOrder(order);
        if (clientPayments == null || clientPayments.isEmpty()) {
            throw new NotFoundException("Client payments not found for the given order: " + order.getId());
        }
        return clientPayments;
    }

    // Retrieve client payments by date
    public List<ClientPayment> getClientPaymentsByDate(@NotNull(message = "Date cannot be null") LocalDate date) {
        List<ClientPayment> clientPayments = clientPaymentRepository.findByDate(date);
        if (clientPayments == null || clientPayments.isEmpty()) {
            throw new NotFoundException("Client payments not found for the given date: " + date);
        }
        return clientPayments;
    }

    // Save a new client payment
    @Transactional
    public ClientPayment saveClientPayment(ClientPayment clientPayment) {
        verifyOrderExists(clientPayment.getOrder());


        return clientPaymentRepository.save(clientPayment);
    }

    // Update an existing client payment
    @Transactional
    public ClientPayment updateClientPayment(ClientPayment clientPayment) {
        verifyOrderExists(clientPayment.getOrder());
        if (!clientPaymentRepository.existsById(clientPayment.getId())) {
            throw new NotFoundException("Client payment not found with ID: " + clientPayment.getId());
        }

        return clientPaymentRepository.saveAndFlush(clientPayment);
    }

    // Verifies if the given order exists in the database; throws a NotFoundException if not.
    private void verifyOrderExists(Order order) {
        if (!orderRepository.existsById(order.getId())) {
            throw new NotFoundException("Invalid order associated with the client payment.");
        }
    }

}
