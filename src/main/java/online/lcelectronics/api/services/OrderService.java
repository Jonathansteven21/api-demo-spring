package online.lcelectronics.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.Client;
import online.lcelectronics.api.entities.HistoricAppliance;
import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {

    private final OrderRepository orderRepository;

    // Retrieve all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Retrieve an order by its ID
    public Order getOrderById(Integer id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    // Retrieve orders by the associated client
    public List<Order> getOrdersByClient(Client client) {
        return orderRepository.findByClient(client);
    }

    // Retrieve orders by the associated historic appliance
    public List<Order> getOrdersByHistoricAppliance(HistoricAppliance historicAppliance) {
        return orderRepository.findByHistoricAppliance(historicAppliance);
    }

    // Retrieve orders by the created date
    public List<Order> getOrdersByCreatedDate(LocalDate createdDate) {
        return orderRepository.findByCreatedDate(createdDate);
    }

    // Save an order
    @Transactional
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // Update an order
    @Transactional
    public Order updateOrder(Order order) {
        return orderRepository.saveAndFlush(order);
    }
}
