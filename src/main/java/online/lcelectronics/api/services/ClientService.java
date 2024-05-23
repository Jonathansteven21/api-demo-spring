package online.lcelectronics.api.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.Client;
import online.lcelectronics.api.exceptions.NotFoundException;
import online.lcelectronics.api.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClientService {

    private final ClientRepository clientRepository;

    // Retrieve all clients
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // Retrieve a client by their identity card number
    public Client getClientByIdentityCard(@NotNull(message = "Identity card number cannot be null") Long identityCard) {
        return clientRepository.findById(identityCard)
                .orElseThrow(() -> new NotFoundException("Client not found with identity card: " + identityCard));
    }

    // Retrieve a client by their phone number; throw NotFoundException if not found.
    public Client getClientByPhone(@NotNull(message = "Phone number cannot be null") Long phone) {
        return clientRepository.findByPhone(phone)
                .orElseThrow(() -> new NotFoundException("Client not found with phone number: " + phone));
    }

    // Retrieve clients whose name contains the specified string; throw NotFoundException if not found.
    public List<Client> getClientsByName(@NotEmpty(message = "Name cannot be empty") String name) {
        List<Client> clients = clientRepository.findByNameContaining(name);
        if (clients.isEmpty()) {
            throw new NotFoundException("Clients not found with name containing: " + name);
        }
        return clients;
    }

    // Retrieve clients whose identity card contains the specified string
    public List<Client> getClientsByIdentityCard(@NotEmpty(message = "Identity card cannot be empty") String identityCard) {
        List<Client> clients = clientRepository.findByIdentityCardContaining(identityCard);
        if (clients.isEmpty()) {
            throw new NotFoundException("Clients not found with identity card containing: " + identityCard);
        }
        return clients;
    }

    // Save a new client
    @Transactional
    public Client saveClient(Client client) {
        Long identityCard = client.getIdentityCard();
        if (clientRepository.existsById(identityCard)) {
            throw new IllegalArgumentException("Client with identity card " + identityCard + " already exists");
        }
        return clientRepository.save(client);
    }

    // Update an existing client
    @Transactional
    public Client updateClient(Client client) {
        if (!clientRepository.existsById(client.getIdentityCard())) {
            throw new NotFoundException("Client not found with Identity card: " + client.getIdentityCard());
        }

        return clientRepository.saveAndFlush(client);
    }

}
