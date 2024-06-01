package online.lcelectronics.api.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.ClientPayment;
import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.services.ClientPaymentService;
import online.lcelectronics.api.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/client-payments")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClientPaymentController {

    private final ClientPaymentService clientPaymentService;

    // Retrieve all client payments
    @GetMapping
    public ResponseEntity<ApiResponse<List<ClientPayment>>> getAllClientPayments() {
        List<ClientPayment> clientPayments = clientPaymentService.getAllClientPayments();
        ApiResponse<List<ClientPayment>> response = new ApiResponse<>(HttpStatus.OK.value(), "Client payments retrieved successfully", clientPayments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Retrieve a client payment by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientPayment>> getClientPaymentById(@PathVariable @NotNull Integer id) {
        ClientPayment clientPayment = clientPaymentService.getClientPaymentById(id);
        ApiResponse<ClientPayment> response = new ApiResponse<>(HttpStatus.OK.value(), "Client payment retrieved successfully", clientPayment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Retrieve client payments by associated order
    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<ApiResponse<List<ClientPayment>>> getClientPaymentsByOrder(@PathVariable @NotNull Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        List<ClientPayment> clientPayments = clientPaymentService.getClientPaymentsByOrder(order);
        ApiResponse<List<ClientPayment>> response = new ApiResponse<>(HttpStatus.OK.value(), "Client payments retrieved successfully for the order", clientPayments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Retrieve client payments by date
    @GetMapping("/by-date")
    public ResponseEntity<ApiResponse<List<ClientPayment>>> getClientPaymentsByDate(@RequestParam @NotNull LocalDate date) {
        List<ClientPayment> clientPayments = clientPaymentService.getClientPaymentsByDate(date);
        ApiResponse<List<ClientPayment>> response = new ApiResponse<>(HttpStatus.OK.value(), "Client payments retrieved successfully for the date", clientPayments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Save a new client payment
    @PostMapping
    public ResponseEntity<ApiResponse<ClientPayment>> saveClientPayment(@RequestBody @Valid ClientPayment clientPayment) {
        clientPayment.setDate(null);
        ClientPayment savedClientPayment = clientPaymentService.saveClientPayment(clientPayment);
        ApiResponse<ClientPayment> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Client payment saved successfully", savedClientPayment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update an existing client payment
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientPayment>> updateClientPayment(@PathVariable Integer id, @RequestBody @Valid ClientPayment clientPayment) {
        clientPayment.setId(id);
        ClientPayment updatedClientPayment = clientPaymentService.updateClientPayment(clientPayment);
        ApiResponse<ClientPayment> response = new ApiResponse<>(HttpStatus.OK.value(), "Client payment updated successfully", updatedClientPayment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
