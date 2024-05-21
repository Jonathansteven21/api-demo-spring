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

    @Test
    void whenFindByPhone_thenReturnClient() {
        when(clientRepository.findByPhone(client.getPhone()))
                .thenReturn(Optional.of(client));

        Optional<Client> foundClient = clientRepository.findByPhone(client.getPhone());

        assertTrue(foundClient.isPresent());
        assertEquals(client.getPhone(), foundClient.get().getPhone());
    }

    @Test
    void whenPhoneNotFound_thenReturnEmpty() {
        int nonExistentPhone = 999999999;
        when(clientRepository.findByPhone(nonExistentPhone))
                .thenReturn(Optional.empty());

        Optional<Client> foundClient = clientRepository.findByPhone(nonExistentPhone);

        assertTrue(foundClient.isEmpty());
    }

    @Test
    void whenFindByIdentityCardContaining_thenReturnClients() {
        when(clientRepository.findByIdentityCardContaining(client.getIdentityCard().toString()))
                .thenReturn(Collections.singletonList(client));

        List<Client> foundClients = clientRepository.findByIdentityCardContaining(client.getIdentityCard().toString());

        assertFalse(foundClients.isEmpty());
        assertEquals(client.getIdentityCard().toString(), foundClients.get(0).getIdentityCard().toString());
    }

    @Test
    void whenIdentityCardNotContained_thenReturnEmptyList() {
        String nonExistentIdentityCard = "999999";
        when(clientRepository.findByIdentityCardContaining(nonExistentIdentityCard))
                .thenReturn(Collections.emptyList());

        List<Client> foundClients = clientRepository.findByIdentityCardContaining(nonExistentIdentityCard);

        assertTrue(foundClients.isEmpty());
    }

    @Test
    void whenFindByNameContaining_thenReturnClients() {
        when(clientRepository.findByNameContaining(client.getName()))
                .thenReturn(Collections.singletonList(client));

        List<Client> foundClients = clientRepository.findByNameContaining(client.getName());

        assertFalse(foundClients.isEmpty());
        assertEquals(client.getName(), foundClients.get(0).getName());
    }

    @Test
    void whenNameNotContained_thenReturnEmptyList() {
        String nonExistentName = "Non Existent Client";
        when(clientRepository.findByNameContaining(nonExistentName))
                .thenReturn(Collections.emptyList());

        List<Client> foundClients = clientRepository.findByNameContaining(nonExistentName);

        assertTrue(foundClients.isEmpty());
    }
}
