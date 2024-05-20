package online.lcelectronics.api.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.entities.OrderHistory;
import online.lcelectronics.api.exceptions.NotFoundException;
import online.lcelectronics.api.repositories.OrderHistoryRepository;
import online.lcelectronics.api.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderRepository orderRepository;

    // Retrieve all order history entries
    public List<OrderHistory> getAllOrderHistoryEntries() {
        return orderHistoryRepository.findAll();
    }

    // Retrieve an order history entry by its ID
    public OrderHistory getOrderHistoryEntryById(@NotNull(message = "ID cannot be null") Integer id) {
        return orderHistoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order history entry not found with ID: " + id));
    }

    // Retrieve order history entries by the associated order
    public List<OrderHistory> getOrderHistoryEntriesByOrder(Order order) {
        List<OrderHistory> orderHistories = orderHistoryRepository.findByOrder(order);
        if (orderHistories == null || orderHistories.isEmpty()) {
            throw new NotFoundException("Order Histories not found for the given order: " + order.getId());
        }
        return orderHistories;
    }

    // Save an order history entry
    @Transactional
    public OrderHistory saveOrderHistoryEntry(@Valid OrderHistory orderHistory) {
        verifyOrderExists(orderHistory.getOrder());
        return orderHistoryRepository.save(orderHistory);
    }

    // Update an order history entry
    @Transactional
    public OrderHistory updateOrderHistoryEntry(@Valid OrderHistory orderHistory) {
        verifyOrderExists(orderHistory.getOrder());
        if (!orderHistoryRepository.existsById(orderHistory.getId())) {
            throw new NotFoundException("Order history entry not found with ID: " + orderHistory.getId());
        }

        return orderHistoryRepository.saveAndFlush(orderHistory);
    }

    // Verifies if the given order exists in the database; throws a NotFoundException if not.
    private void verifyOrderExists(Order order) {
        if (!orderRepository.existsById(order.getId())) {
            throw new NotFoundException("Invalid order associated with the client payment.");
        }
    }
}

