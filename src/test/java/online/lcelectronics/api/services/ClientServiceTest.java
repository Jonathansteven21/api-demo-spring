package online.lcelectronics.api.services;

import online.lcelectronics.api.entities.Client;
import online.lcelectronics.api.exceptions.NotFoundException;
import online.lcelectronics.api.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setIdentityCard(12345);
        client.setName("John Doe");
        client.setPhone(987654321);
    }

    /**
     * Tests the getAllClients method of ClientService.
     * Verifies that all clients are retrieved successfully.
     */
    @Test
    void getAllClients() {
        List<Client> clients = new ArrayList<>();
        clients.add(client);
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.getAllClients();
        assertEquals(1, result.size());
        assertEquals(client, result.get(0));
    }

    /**
     * Tests the getClientByIdentityCard method of ClientService with an existing identity card.
     * Verifies that the client with the given identity card is retrieved successfully.
     */
    @Test
    void getClientByIdentityCard_existingId() {
        when(clientRepository.findById(12345)).thenReturn(Optional.of(client));

        Client result = clientService.getClientByIdentityCard(12345);
        assertEquals(client, result);
    }

    /**
     * Tests the getClientByIdentityCard method of ClientService with a non-existing identity card.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void getClientByIdentityCard_nonExistingId() {
        when(clientRepository.findById(12345)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clientService.getClientByIdentityCard(12345));
    }

    /**
     * Tests the getClientByPhone method of ClientService with an existing phone number.
     * Verifies that the client with the given phone number is retrieved successfully.
     */
    @Test
    void getClientByPhone_existingPhone() {
        when(clientRepository.findByPhone(987654321)).thenReturn(Optional.of(client));

        Client result = clientService.getClientByPhone(987654321);
        assertEquals(client, result);
    }

    /**
     * Tests the getClientByPhone method of ClientService with a non-existing phone number.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void getClientByPhone_nonExistingPhone() {
        when(clientRepository.findByPhone(987654321)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clientService.getClientByPhone(987654321));
    }

    /**
     * Tests the getClientsByName method of ClientService with an existing name.
     * Verifies that clients with the given name are retrieved successfully.
     */
    @Test
    void getClientsByName_existingName() {
        List<Client> clients = new ArrayList<>();
        clients.add(client);
        when(clientRepository.findByNameContaining("John")).thenReturn(clients);

        List<Client> result = clientService.getClientsByName("John");
        assertEquals(1, result.size());
        assertEquals(client, result.get(0));
    }

    /**
     * Tests the getClientsByName method of ClientService with a non-existing name.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void getClientsByName_nonExistingName() {
        when(clientRepository.findByNameContaining("John")).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> clientService.getClientsByName("John"));
    }

    /**
     * Tests the getClientsByIdentityCard method of ClientService with an existing identity card.
     * Verifies that clients with the given identity card are retrieved successfully.
     */
    @Test
    void getClientsByIdentityCard_existingIdentityCard() {
        List<Client> clients = new ArrayList<>();
        clients.add(client);
        when(clientRepository.findByIdentityCardContaining("12345")).thenReturn(clients);

        List<Client> result = clientService.getClientsByIdentityCard("12345");
        assertEquals(1, result.size());
        assertEquals(client, result.get(0));
    }

    /**
     * Tests the getClientsByIdentityCard method of ClientService with a non-existing identity card.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void getClientsByIdentityCard_nonExistingIdentityCard() {
        when(clientRepository.findByIdentityCardContaining("12345")).thenReturn(new ArrayList<>());

        assertThrows(NotFoundException.class, () -> clientService.getClientsByIdentityCard("12345"));
    }

    /**
     * Tests the saveClient method of ClientService with a valid client.
     * Verifies that the client is successfully saved.
     */
    @Test
    void saveClient_validClient() {
        when(clientRepository.save(client)).thenReturn(client);

        Client result = clientService.saveClient(client);
        assertEquals(client, result);
    }

    /**
     * Tests the updateClient method of ClientService with an existing client.
     * Verifies that the client is successfully updated.
     */
    @Test
    void updateClient_existingClient() {
        when(clientRepository.existsById(client.getIdentityCard())).thenReturn(true);
        when(clientRepository.saveAndFlush(client)).thenReturn(client);

        Client result = clientService.updateClient(client);
        assertEquals(client, result);
    }

    /**
     * Tests the updateClient method of ClientService with a non-existing client.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void updateClient_nonExistingClient() {
        when(clientRepository.existsById(client.getIdentityCard())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> clientService.updateClient(client));
    }
}
