package online.demo.api.converters;

import online.demo.api.dto.ClientDTO;
import online.demo.api.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter {

    /**
     * Converts a Client entity to its corresponding DTO.
     * @param client The Client entity to convert.
     * @return The converted ClientDTO.
     */
    public ClientDTO toDto(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(client.getName());
        clientDTO.setIdentityCard(client.getIdentityCard());
        return clientDTO;
    }
}
