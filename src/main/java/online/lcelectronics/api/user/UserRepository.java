package online.lcelectronics.api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// This interface defines methods to access User entities in the database
public interface UserRepository extends JpaRepository<User, Long> {

    // Finds a user by their username
    Optional<User> findByUsername(String username);
}
