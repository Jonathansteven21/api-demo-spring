package online.lcelectronics.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.Client;
import online.lcelectronics.api.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClientService {

    private final ClientRepository clientRepository;

    // Retrieve all clients
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Retrieve a client by their identity card number
    public Client getClientByIdentityCard(int identityCard) {
        Optional<Client> optionalClient = clientRepository.findById(identityCard);
        return optionalClient.orElse(null);
    }

    // Retrieve a client by their phone number
    public Client getClientByPhone(int phone) {
        return clientRepository.findByPhone(phone).orElse(null);
    }

    // Retrieve clients whose name contains the specified string
    public List<Client> getClientsByName(String name) {
        return clientRepository.findByNameContaining(name);
    }

    // Retrieve clients whose identity card contains the specified string
    public List<Client> getClientsByIdentityCard(String identityCard) {
        return clientRepository.findByIdentityCardContaining(identityCard);
    }

    // Save a new client
    @Transactional
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    // Update an existing client
    @Transactional
    public Client updateClient(Client client) {
        return clientRepository.saveAndFlush(client);
    }
}
