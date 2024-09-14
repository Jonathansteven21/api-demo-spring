package online.lcelectronics.api.services;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.Image;
import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.entities.specs.OrderSpecification;
import online.lcelectronics.api.enums.OrderStatus;
import online.lcelectronics.api.exceptions.NotFoundException;
import online.lcelectronics.api.repositories.ClientRepository;
import online.lcelectronics.api.repositories.HistoricApplianceRepository;
import online.lcelectronics.api.repositories.ImageRepository;
import online.lcelectronics.api.repositories.OrderRepository;
import online.lcelectronics.api.util.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

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
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found with ID: " + id));
    }

    // Retrieve the last 5 orders created
    public List<Order> getLastFiveOrders() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("createdDate").descending());
        return orderRepository.findAll(pageable).getContent();
    }

    // Retrieve order list by pageable
    public Page<Order> getOrdersByPageable(Integer page, Integer size, String sortBy, String sortDirection) {
        Pageable pageable = PageableUtil.createPageable(page, size, sortBy, sortDirection, "createdDate");
        Page<Order> orderPage = orderRepository.findAll(pageable);
        if (!orderPage.hasContent()) {
            throw new NotFoundException("Orders not found with these specifications");
        }
        return orderPage;
    }

    // Retrieve an order by its reference code
    public Order getOrderByReferenceCode(@NotNull(message = "Reference code cannot be null") String referenceCode) {
        return orderRepository.findByReferenceCode(referenceCode).orElseThrow(() -> new NotFoundException("Order not found with reference code: " + referenceCode));
    }

    // Retrieves a list of orders based on the provided criteria.
    // The criteria include the order ID, client's identity card, historic appliance serial number, and order status.
    // Constructs a dynamic specification based on the provided criteria.
    // Filters orders by ID containing the specified value.
    // Filters orders by client's identity card.
    // Filters orders by historic appliance serial number.
    // Filters orders by status.
    // Filters orders by created date.
    // Returns a list of orders that match the specified criteria.
    public List<Order> getOrdersByCriteria(Order order) {
        Specification<Order> spec = Specification.where(null);

        if (order.getId() != null) {
            spec = spec.and(OrderSpecification.withId(String.valueOf(order.getId())));
        }

        if (order.getClient() != null && order.getClient().getIdentityCard() != null) {
            spec = spec.and(OrderSpecification.withClientIdentityCard(order.getClient().getIdentityCard()));
        }

        if (order.getHistoricAppliance() != null && order.getHistoricAppliance().getSerial() != null) {
            spec = spec.and(OrderSpecification.withHistoricAppliance(order.getHistoricAppliance().getSerial()));
        }

        if (order.getStatus() != null) {
            spec = spec.and(OrderSpecification.withStatus(order.getStatus()));
        }

        if (order.getCreatedDate() != null) {
            spec = spec.and(OrderSpecification.withCreatedDate(order.getCreatedDate()));
        }

        if (order.getWarranty() != null) {
            spec = spec.and(OrderSpecification.withWarranty(order.getWarranty()));
        }

        List<Order> orderList = orderRepository.findAll(spec);
        if (orderList.isEmpty()) {
            throw new NotFoundException("Orders not found with these specifications");
        }
        return orderList;
    }

    // Save an order
    @Transactional
    public Order saveOrder(Order order) {
        validateOrder(order);
        String referenceCode = UUID.randomUUID().toString();
        order.setReferenceCode(referenceCode);
        return orderRepository.save(order);
    }

    // Update an order status
    @Transactional
    public Order updateOrderStatus(@NotNull Integer id, @NotNull OrderStatus status) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found with ID: " + id));

        existingOrder.setStatus(status);
        return orderRepository.saveAndFlush(existingOrder);
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
