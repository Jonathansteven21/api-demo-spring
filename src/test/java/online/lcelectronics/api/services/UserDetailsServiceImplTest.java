package online.lcelectronics.api.services;

import online.lcelectronics.api.entities.User;
import online.lcelectronics.api.enums.Role;
import online.lcelectronics.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRole(Role.USER); // Set a default Role value
    }

    /**
     * Test loading a user by username when the user is found in the repository.
     */
    @Test
    void loadUserByUsername_UserFound() {
        // Arrange
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");

        // Assert
        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
    }

    /**
     * Test loading a user by username when the user is not found in the repository.
     */
    @Test
    void loadUserByUsername_UserNotFound() {
        // Arrange
        when(userRepository.findByUsername("nonexistentUser")).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("nonexistentUser"));
    }
}
