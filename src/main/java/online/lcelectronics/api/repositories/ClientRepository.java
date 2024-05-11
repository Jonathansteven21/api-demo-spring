package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// This interface defines methods to access Client entities in the database
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    // Method to find a Client by its phone number
    Optional<Client> findByPhone(int phone);

    // Method to find Clients by a partial match of their identity card
    List<Client> findByIdentityCardContaining(String identityCard);

    // Method to find Clients by a partial match of their name
    List<Client> findByNameContaining(String name);

}
