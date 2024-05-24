package online.lcelectronics.api.controllers;

import online.lcelectronics.api.entities.Client;
import online.lcelectronics.api.util.ApiResponse;
import online.lcelectronics.api.services.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    ClientService clientService;

    @InjectMocks
    ClientController clientController;

    /**
     * Tests the getAllClients method of ClientController.
     * Verifies that all clients are retrieved successfully.
     */
    @Test
    void getAllClients() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        clients.add(new Client());

        when(clientService.getAllClients()).thenReturn(clients);

        ResponseEntity<ApiResponse<List<Client>>> responseEntity = clientController.getAllClients();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Clients found", responseEntity.getBody().getMessage());
        assertEquals(clients, responseEntity.getBody().getData());
    }

    /**
     * Tests the getClientByIdentityCard method of ClientController with an existing identity card.
     * Verifies that the client with the given identity card is retrieved successfully.
     */
    @Test
    void getClientByIdentityCard() {
        Client client = new Client();
        client.setIdentityCard(123456L);

        when(clientService.getClientByIdentityCard(123456L)).thenReturn(client);

        ResponseEntity<ApiResponse<Client>> responseEntity = clientController.getClientByIdentityCard(123456L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Client found", responseEntity.getBody().getMessage());
        assertEquals(client, responseEntity.getBody().getData());
    }

    /**
     * Tests the getClientByPhone method of ClientController with an existing phone number.
     * Verifies that the client with the given phone number is retrieved successfully.
     */
    @Test
    void getClientByPhone() {
        Client client = new Client();
        client.setPhone(1234567890L);

        when(clientService.getClientByPhone(1234567890L)).thenReturn(client);

        ResponseEntity<ApiResponse<Client>> responseEntity = clientController.getClientByPhone(1234567890L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Client found", responseEntity.getBody().getMessage());
        assertEquals(client, responseEntity.getBody().getData());
    }

    /**
     * Tests the getClientsByName method of ClientController with an existing name.
     * Verifies that the clients with the given name are retrieved successfully.
     */
    @Test
    void getClientsByName() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());

        when(clientService.getClientsByName("John")).thenReturn(clients);

        ResponseEntity<ApiResponse<List<Client>>> responseEntity = clientController.getClientsByName("John");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Clients found", responseEntity.getBody().getMessage());
        assertEquals(clients, responseEntity.getBody().getData());
    }

    /**
     * Tests the getClientsByIdentityCard method of ClientController with an existing identity card.
     * Verifies that the clients with the given identity card are retrieved successfully.
     */
    @Test
    void getClientsByIdentityCard() {
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());

        when(clientService.getClientsByIdentityCard("1234")).thenReturn(clients);

        ResponseEntity<ApiResponse<List<Client>>> responseEntity = clientController.getClientsByIdentityCard("1234");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Clients found", responseEntity.getBody().getMessage());
        assertEquals(clients, responseEntity.getBody().getData());
    }

    /**
     * Tests the createClient method of ClientController with a valid client.
     * Verifies that the client is successfully created.
     */
    @Test
    void createClient() {
        Client client = new Client();
        client.setName("John Doe");

        Client createdClient = new Client();
        createdClient.setIdentityCard(123456L);
        createdClient.setName("John Doe");

        when(clientService.saveClient(client)).thenReturn(createdClient);

        ResponseEntity<ApiResponse<Client>> responseEntity = clientController.createClient(client);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(HttpStatus.CREATED.value(), responseEntity.getBody().getStatus());
        assertEquals("Client created", responseEntity.getBody().getMessage());
        assertEquals(createdClient, responseEntity.getBody().getData());
    }

    /**
     * Tests the updateClient method of ClientController with an existing client.
     * Verifies that the client is successfully updated.
     */
    @Test
    void updateClient() {
        Client client = new Client();
        client.setIdentityCard(123456L);
        client.setName("Updated Name");

        when(clientService.updateClient(client)).thenReturn(client);

        ResponseEntity<ApiResponse<Client>> responseEntity = clientController.updateClient(123456L, client);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Client updated", responseEntity.getBody().getMessage());
        assertEquals(client, responseEntity.getBody().getData());
    }
}
