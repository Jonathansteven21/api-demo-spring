package online.lcelectronics.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.Client;
import online.lcelectronics.api.entities.HistoricAppliance;
import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.enums.OrderStatus;
import online.lcelectronics.api.services.OrderService;
import online.lcelectronics.api.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final OrderService orderService;

    // Get all orders
    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        ApiResponse<List<Order>> response = new ApiResponse<>(HttpStatus.OK.value(), "Orders retrieved successfully", orders);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Order>> getOrderById(@PathVariable Integer id) {
        Order order = orderService.getOrderById(id);
        ApiResponse<Order> response = new ApiResponse<>(HttpStatus.OK.value(), "Order retrieved successfully", order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get orders by criteria
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Order>>> getOrdersByCriteria(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Long clientIdentityCard,
            @RequestParam(required = false) String historicApplianceSerial,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDate) {

        Order order = new Order();
        order.setId(id);
        if (clientIdentityCard != null) {
            Client client = new Client();
            client.setIdentityCard(clientIdentityCard);
            order.setClient(client);
        }
        if (historicApplianceSerial != null) {
            HistoricAppliance historicAppliance = new HistoricAppliance();
            historicAppliance.setSerial(historicApplianceSerial);
            order.setHistoricAppliance(historicAppliance);
        }
        order.setStatus(status);
        order.setCreatedDate(createdDate);

        List<Order> orders = orderService.getOrdersByCriteria(order);
        ApiResponse<List<Order>> response = new ApiResponse<>(HttpStatus.OK.value(), "Orders found", orders);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Save a new order
    @PostMapping
    public ResponseEntity<ApiResponse<Order>> saveOrder(@Valid @RequestBody Order order) {
        order.setId(null);
        order.setCreatedDate(null);
        Order savedOrder = orderService.saveOrder(order);
        ApiResponse<Order> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Order created successfully", savedOrder);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update status from an existing order
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Order>> updateOrderStatus(@PathVariable Integer id, @RequestParam OrderStatus status) {
        Order updatedOrder = orderService.updateOrderStatus(id, status);
        ApiResponse<Order> response = new ApiResponse<>(HttpStatus.OK.value(), "Order status updated successfully", updatedOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
