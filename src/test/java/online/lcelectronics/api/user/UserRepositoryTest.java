package online.lcelectronics.api.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole(Role.USER);
    }

    /**
     * Tests the findByUsername method of UserRepository.
     * Verifies that the correct User is returned when searched by username.
     */
    @Test
    void whenFindByUsername_thenReturnUser() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.findByUsername("testuser");

        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
    }

    /**
     * Tests the findByUsername method of UserRepository when the user is not found.
     * Ensures that an empty Optional is returned when no user is found for the given username.
     */
    @Test
    void whenUsernameNotFound_thenReturnEmptyOptional() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        Optional<User> foundUser = userRepository.findByUsername("nonexistentuser");

        assertFalse(foundUser.isPresent());
    }

    /**
     * Tests the save method of UserRepository.
     * Verifies that the User is correctly saved and can be retrieved by its ID.
     */
    @Test
    void whenSaveUser_thenUserIsSaved() {
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User savedUser = userRepository.save(user);
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
    }

    /**
     * Tests the deleteById method of UserRepository.
     * Verifies that the User is correctly deleted and cannot be retrieved by its ID.
     */
    @Test
    void whenDeleteUser_thenUserIsDeleted() {
        userRepository.deleteById(user.getId());
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        Optional<User> foundUser = userRepository.findById(user.getId());

        assertFalse(foundUser.isPresent());
    }
}
