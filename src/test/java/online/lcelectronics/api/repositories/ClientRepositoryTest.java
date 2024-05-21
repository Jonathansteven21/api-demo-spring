package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class ClientRepositoryTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setPhone(1234567890);
        client.setIdentityCard(123456);
        client.setName("Test Client");
    }

    /**
     * Tests the findByPhone method of ClientRepository.
     * Verifies that the correct Client is returned when searched by phone.
     */
    @Test
    void whenFindByPhone_thenReturnClient() {
        when(clientRepository.findByPhone(client.getPhone()))
                .thenReturn(Optional.of(client));

        Optional<Client> foundClient = clientRepository.findByPhone(client.getPhone());

        assertTrue(foundClient.isPresent());
        assertEquals(client.getPhone(), foundClient.get().getPhone());
    }

    /**
     * Tests the findByPhone method of ClientRepository when the phone number is not found.
     * Ensures that an empty optional is returned when the phone number does not exist.
     */
    @Test
    void whenPhoneNotFound_thenReturnEmpty() {
        int nonExistentPhone = 999999999;
        when(clientRepository.findByPhone(nonExistentPhone))
                .thenReturn(Optional.empty());

        Optional<Client> foundClient = clientRepository.findByPhone(nonExistentPhone);

        assertTrue(foundClient.isEmpty());
    }

    /**
     * Tests the findByIdentityCardContaining method of ClientRepository.
     * Verifies that the correct Client(s) are returned when searched by identity card.
     */
    @Test
    void whenFindByIdentityCardContaining_thenReturnClients() {
        when(clientRepository.findByIdentityCardContaining(client.getIdentityCard().toString()))
                .thenReturn(Collections.singletonList(client));

        List<Client> foundClients = clientRepository.findByIdentityCardContaining(client.getIdentityCard().toString());

        assertFalse(foundClients.isEmpty());
        assertEquals(client.getIdentityCard().toString(), foundClients.get(0).getIdentityCard().toString());
    }

    /**
     * Tests the findByIdentityCardContaining method of ClientRepository when the identity card is not contained.
     * Ensures that an empty list is returned when no clients are found with the provided identity card.
     */
    @Test
    void whenIdentityCardNotContained_thenReturnEmptyList() {
        String nonExistentIdentityCard = "999999";
        when(clientRepository.findByIdentityCardContaining(nonExistentIdentityCard))
                .thenReturn(Collections.emptyList());

        List<Client> foundClients = clientRepository.findByIdentityCardContaining(nonExistentIdentityCard);

        assertTrue(foundClients.isEmpty());
    }

    /**
     * Tests the findByNameContaining method of ClientRepository.
     * Verifies that the correct Client(s) are returned when searched by name.
     */
    @Test
    void whenFindByNameContaining_thenReturnClients() {
        when(clientRepository.findByNameContaining(client.getName()))
                .thenReturn(Collections.singletonList(client));

        List<Client> foundClients = clientRepository.findByNameContaining(client.getName());

        assertFalse(foundClients.isEmpty());
        assertEquals(client.getName(), foundClients.get(0).getName());
    }

    /**
     * Tests the findByNameContaining method of ClientRepository when the name is not contained.
     * Ensures that an empty list is returned when no clients are found with the provided name.
     */
    @Test
    void whenNameNotContained_thenReturnEmptyList() {
        String nonExistentName = "Non Existent Client";
        when(clientRepository.findByNameContaining(nonExistentName))
                .thenReturn(Collections.emptyList());

        List<Client> foundClients = clientRepository.findByNameContaining(nonExistentName);

        assertTrue(foundClients.isEmpty());
    }
}
