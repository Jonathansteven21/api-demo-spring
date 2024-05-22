package online.lcelectronics.api.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.Image;
import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.entities.specs.OrderSpecification;
import online.lcelectronics.api.exceptions.NotFoundException;
import online.lcelectronics.api.repositories.ClientRepository;
import online.lcelectronics.api.repositories.HistoricApplianceRepository;
import online.lcelectronics.api.repositories.ImageRepository;
import online.lcelectronics.api.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {

    // Retrieve all orders
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final HistoricApplianceRepository historicApplianceRepository;
    private final ImageRepository imageRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Retrieve an order by its ID
    public Order getOrderById(@NotNull(message = "ID cannot be null") Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + id));
    }

    // Retrieves a list of orders based on the provided criteria.
    // The criteria include the order ID, client's identity card, historic appliance serial number, and order status.
    // Constructs a dynamic specification based on the provided criteria.
    // Filters orders by ID containing the specified value.
    // Filters orders by client's identity card.
    // Filters orders by historic appliance serial number.
    // Filters orders by status.
    // Returns a list of orders that match the specified criteria.
    public List<Order> getOrdersByCriteria(Order order) {
        Specification<Order> spec = Specification.where(OrderSpecification.idContains(String.valueOf(order.getId())))
                .and(OrderSpecification.hasClient(order.getClient() != null ? order.getClient().getIdentityCard() : null))
                .and(OrderSpecification.hasHistoricAppliance(order.getHistoricAppliance() != null ? order.getHistoricAppliance().getSerial() : null))
                .and(OrderSpecification.hasStatus(order.getStatus()));

        List<Order> orderList = orderRepository.findAll(spec);
        if (orderList.isEmpty()) {
            throw new NotFoundException("Orders not found with these specifications");
        }
        return orderList;
    }

    // Save an order
    @Transactional
    public Order saveOrder(@Valid Order order) {
        validateOrder(order);
        return orderRepository.save(order);
    }

    // Update an order
    @Transactional
    public Order updateOrder(@Valid Order order) {
        validateOrder(order);
        if (!orderRepository.existsById(order.getId())) {
            throw new NotFoundException("Order entry not found with ID: " + order.getId());
        }
        return orderRepository.saveAndFlush(order);
    }

    // Private method to validate order
    private void validateOrder(Order order) {
        if (order.getClient() != null && !clientRepository.existsById(order.getClient().getIdentityCard())) {
            throw new NotFoundException("Client not found with ID: " + order.getClient().getIdentityCard());
        }

        if (order.getHistoricAppliance() != null && !historicApplianceRepository.existsById(order.getHistoricAppliance().getSerial())) {
            throw new NotFoundException("Historic appliance not found with ID: " + order.getHistoricAppliance().getSerial());
        }

        if (order.getImages() != null && !order.getImages().isEmpty()) {
            for (Image image : order.getImages()) {
                if (!imageRepository.existsById(image.getId())) {
                    throw new NotFoundException("Image not found with ID: " + image.getId());
                }
            }
        }
    }
}
