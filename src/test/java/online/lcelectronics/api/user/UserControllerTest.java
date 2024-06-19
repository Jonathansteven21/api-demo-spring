package online.lcelectronics.api.user;

import online.lcelectronics.api.util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * Test for retrieving all users.
     */
    @Test
    void getAllUsers() {
        // Mock service method to return a list of users
        List<User> userList = Collections.singletonList(new User());
        when(userService.getAllUsers()).thenReturn(userList);

        // Call the controller method
        ResponseEntity<ApiResponse<List<User>>> responseEntity = userController.getAllUsers();

        // Verify that the response is successful and contains the expected data
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userList, responseEntity.getBody().getData());
    }

    /**
     * Test for retrieving a user by ID when the user is found.
     */
    @Test
    void getUserById_UserFound() {
        // Mock service method to return a user
        User user = new User();
        when(userService.getUserById(1L)).thenReturn(user);

        // Call the controller method
        ResponseEntity<ApiResponse<User>> responseEntity = userController.getUserById(1L);

        // Verify that the response is successful and contains the expected data
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody().getData());
    }

    /**
     * Test for updating a user when the user is found.
     */
    @Test
    void updateUser_UserFound() {
        // Create a user instance with ID and updated properties
        User user = new User();
        user.setId(1L);
        user.setUsername("updatedUser");
        user.setPassword("updatedPassword");

        // Mock service method to return the updated user
        when(userService.updateUser(any(User.class))).thenReturn(user);

        // Call the controller method
        ResponseEntity<ApiResponse<User>> responseEntity = userController.updateUser(1L, user);

        // Verify that the response is successful and contains the updated user
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, Objects.requireNonNull(responseEntity.getBody()).getData());
    }

    /**
     * Test for deleting a user when the user is found.
     */
    @Test
    void deleteUser_UserFound() {
        // Call the controller method to delete a user
        ResponseEntity<Void> responseEntity = userController.deleteUser(1L);

        // Verify that the response is successful (HTTP 204 No Content)
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
