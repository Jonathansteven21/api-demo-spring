package online.demo.api.repositories;

import online.demo.api.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// This interface defines methods to access Client entities in the database
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Method to find a Client by its phone number
    Optional<Client> findByPhone(Long phone);

    // Method to find Clients by a partial match of their identity card
    @Query("SELECT c FROM Client c WHERE CAST(c.identityCard AS string) LIKE %:identityCard%")
    List<Client> findByIdentityCardContaining(String identityCard);

    // Method to find Clients by a partial match of their name
    List<Client> findByNameContaining(String name);

}
