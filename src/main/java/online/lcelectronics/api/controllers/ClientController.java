package online.lcelectronics.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.Client;
import online.lcelectronics.api.responses.ApiResponse;
import online.lcelectronics.api.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClientController {

    private final ClientService clientService;

    // Get all clients
    @GetMapping
    public ResponseEntity<ApiResponse<List<Client>>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        ApiResponse<List<Client>> response = new ApiResponse<>(HttpStatus.OK.value(), "Clients found", clients);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get a client by their identity card number
    @GetMapping("/{identityCard}")
    public ResponseEntity<ApiResponse<Client>> getClientByIdentityCard(@PathVariable Long identityCard) {
        Client client = clientService.getClientByIdentityCard(identityCard);
        ApiResponse<Client> response = new ApiResponse<>(HttpStatus.OK.value(), "Client found", client);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get a client by their phone number
    @GetMapping("/phone/{phone}")
    public ResponseEntity<ApiResponse<Client>> getClientByPhone(@PathVariable Long phone) {
        Client client = clientService.getClientByPhone(phone);
        ApiResponse<Client> response = new ApiResponse<>(HttpStatus.OK.value(), "Client found", client);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get clients whose name contains the specified string
    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<List<Client>>> getClientsByName(@PathVariable String name) {
        List<Client> clients = clientService.getClientsByName(name);
        ApiResponse<List<Client>> response = new ApiResponse<>(HttpStatus.OK.value(), "Clients found", clients);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get clients whose identity card contains the specified string
    @GetMapping("/identity-card/{identityCard}")
    public ResponseEntity<ApiResponse<List<Client>>> getClientsByIdentityCard(@PathVariable String identityCard) {
        List<Client> clients = clientService.getClientsByIdentityCard(identityCard);
        ApiResponse<List<Client>> response = new ApiResponse<>(HttpStatus.OK.value(), "Clients found", clients);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Create a new client
    @PostMapping
    public ResponseEntity<ApiResponse<Client>> createClient(@Valid @RequestBody Client client) {
        Client createdClient = clientService.saveClient(client);
        ApiResponse<Client> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Client created", createdClient);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update an existing client
    @PutMapping("/{identityCard}")
    public ResponseEntity<ApiResponse<Client>> updateClient(@Valid @PathVariable Long identityCard, @RequestBody Client client) {
        if (!identityCard.equals(client.getIdentityCard())) {
            throw new IllegalArgumentException("Identity card in path does not match the one in the request body");
        }
        Client updatedClient = clientService.updateClient(client);
        ApiResponse<Client> response = new ApiResponse<>(HttpStatus.OK.value(), "Client updated", updatedClient);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
