package online.lcelectronics.api.services;

import online.lcelectronics.api.entities.User;
import online.lcelectronics.api.enums.Role;
import online.lcelectronics.api.exceptions.NotFoundException;
import online.lcelectronics.api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    /**
     * Set up the User instance before each test.
     */
    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("testPassword");
    }

    /**
     * Test retrieving all users.
     */
    @Test
    void getAllUsers() {
        List<User> userList = Collections.singletonList(user);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();

        assertEquals(userList.size(), result.size());
        assertEquals(userList.get(0), result.get(0));
    }

    /**
     * Test retrieving a user by ID when the user exists.
     */
    @Test
    void getUserById_UserFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    /**
     * Test retrieving a user by ID when the user does not exist.
     */
    @Test
    void getUserById_UserNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserById(2L));
    }

    /**
     * Test retrieving a user by username when the user exists.
     */
    @Test
    void getUserByUsername_UserFound() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        User result = userService.getUserByUsername("testUser");

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
    }

    /**
     * Test retrieving a user by username when the user does not exist.
     */
    @Test
    void getUserByUsername_UserNotFound() {
        when(userRepository.findByUsername("nonexistentUser")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserByUsername("nonexistentUser"));
    }

    /**
     * Test creating a user.
     */
    @Test
    void createUser() {
        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
    }

    /**
     * Test updating a user when the user exists.
     */
    @Test
    void updateUser_UserFound() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");
        when(userRepository.saveAndFlush(user)).thenReturn(user);

        User result = userService.updateUser(user);

        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
    }

    /**
     * Test updating a user when the user does not exist.
     */
    @Test
    void updateUser_UserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.updateUser(user));
    }

    /**
     * Test deleting a user when the user exists.
     */
    @Test
    void deleteUser_UserFound() {
        when(userRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteUser(1L));
    }

    /**
     * Test deleting a user when the user does not exist.
     */
    @Test
    void deleteUser_UserNotFound() {
        when(userRepository.existsById(2L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.deleteUser(2L));
    }

    /**
     * Test loading a user by username when the user exists.
     */
    @Test
    void loadUserByUsername_UserFound() {
        // Mock a user with the given username
        user.setId(null);
        user.setRole(Role.USER); // Set user role

        // Mock UserRepository to return the user when findByUsername is called
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        // Call the method being tested
        UserDetails userDetails = userService.loadUserByUsername("testUser");

        // Verify that the UserDetails object is constructed correctly
        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(user.getRole().name(), userDetails.getAuthorities().iterator().next().getAuthority());
    }

    /**
     * Test loading a user by username when the user does not exist.
     */
    @Test
    void loadUserByUsername_UserNotFound() {
        // Mock UserRepository to return an empty Optional when findByUsername is called
        when(userRepository.findByUsername("nonexistentUser")).thenReturn(Optional.empty());

        // Call the method being tested and expect it to throw UsernameNotFoundException
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("nonexistentUser"));
    }
}
