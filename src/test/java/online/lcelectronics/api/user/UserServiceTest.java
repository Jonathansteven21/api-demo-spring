package online.lcelectronics.api.user;

import online.lcelectronics.api.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

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
     * Test updating a user's username.
     */
    @Test
    void updateUsername() {
        String newUsername = "newUsername";
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        user.setUsername(newUsername);
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUsername(1L, newUsername);

        assertNotNull(updatedUser);
        assertEquals(newUsername, updatedUser.getUsername());
    }

    /**
     * Test updating a user's password.
     */
    @Test
    void updatePassword() {
        String newPassword = "newPassword";
        String originalPassword = user.getPassword();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        user.setPassword(newPassword);
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);

        User updatedUser = userService.updatePassword(1L, user);

        assertNotNull(updatedUser);
        assertNotEquals(originalPassword, updatedUser.getPassword());
    }

    /**
     * Test updating a user's password when the user does not exist.
     */
    @Test
    void updatePassword_UserNotFound() {
        String newPassword = "newPassword";

        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.updatePassword(2L, user));
    }

    /**
     * Test creating a user.
     */
    @Test
    void createUser() {
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        assertNotEquals("testPassword", result.getPassword()); // Verify that the password has been encoded
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
}
