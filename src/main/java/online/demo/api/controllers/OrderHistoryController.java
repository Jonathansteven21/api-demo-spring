package online.demo.api.controllers;

import lombok.RequiredArgsConstructor;
import online.demo.api.entities.Order;
import online.demo.api.entities.OrderHistory;
import online.demo.api.services.OrderHistoryService;
import online.demo.api.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/order-history")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class OrderHistoryController {

    private final OrderHistoryService orderHistoryService;

    // Retrieve all order history entries
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderHistory>>> getAllOrderHistoryEntries() {
        List<OrderHistory> orderHistories = orderHistoryService.getAllOrderHistoryEntries();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Order histories retrieved successfully", orderHistories));
    }

    // Retrieve an order history entry by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderHistory>> getOrderHistoryEntryById(@PathVariable @NotNull Integer id) {
        OrderHistory orderHistory = orderHistoryService.getOrderHistoryEntryById(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Order history retrieved successfully", orderHistory));
    }

    // Retrieve order history entries by the associated order ID
    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse<List<OrderHistory>>> getOrderHistoryEntriesByOrder(@PathVariable @NotNull Integer orderId) {
        Order order= new Order();
        order.setId(orderId);
        List<OrderHistory> orderHistories = orderHistoryService.getOrderHistoryEntriesByOrder(order);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Order histories retrieved successfully for order ID: " + orderId, orderHistories));
    }

    // Save an order history entry
    @PostMapping
    public ResponseEntity<ApiResponse<OrderHistory>> saveOrderHistoryEntry(@RequestBody @Valid OrderHistory orderHistory) {
        orderHistory.setId(null);
        orderHistory.setEventDate(null);
        OrderHistory savedOrderHistory = orderHistoryService.saveOrderHistoryEntry(orderHistory);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.value(), "Order history entry created successfully", savedOrderHistory));
    }

    // Update an order history entry
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderHistory>> updateOrderHistoryEntry(@PathVariable @NotNull Integer id, @RequestBody @Valid OrderHistory orderHistory) {
        orderHistory.setId(id);
        OrderHistory updatedOrderHistory = orderHistoryService.updateOrderHistoryEntry(orderHistory);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Order history entry updated successfully", updatedOrderHistory));
    }
}
