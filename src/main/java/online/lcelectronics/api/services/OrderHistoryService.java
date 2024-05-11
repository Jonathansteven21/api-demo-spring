package online.lcelectronics.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.entities.OrderHistory;
import online.lcelectronics.api.repositories.OrderHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepository;

    // Retrieve all order history entries
    public List<OrderHistory> getAllOrderHistoryEntries() {
        return orderHistoryRepository.findAll();
    }

    // Retrieve an order history entry by its ID
    public OrderHistory getOrderHistoryEntryById(Integer id) {
        Optional<OrderHistory> optionalOrderHistory = orderHistoryRepository.findById(id);
        return optionalOrderHistory.orElse(null);
    }

    // Retrieve order history entries by the associated order
    public List<OrderHistory> getOrderHistoryEntriesByOrder(Order order) {
        return orderHistoryRepository.findByOrder(order);
    }

    // Save an order history entry
    @Transactional
    public OrderHistory saveOrderHistoryEntry(OrderHistory orderHistory) {
        return orderHistoryRepository.save(orderHistory);
    }

    // Update an order history entry
    @Transactional
    public OrderHistory updateOrderHistoryEntry(OrderHistory orderHistory) {
        return orderHistoryRepository.saveAndFlush(orderHistory);
    }
}
