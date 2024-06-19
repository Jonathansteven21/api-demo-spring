package online.lcelectronics.api.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.auth.AuthService;
import online.lcelectronics.api.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Retrieve all users
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        ApiResponse<List<User>> response = new ApiResponse<>(HttpStatus.OK.value(), "Users retrieved successfully", users);
        return ResponseEntity.ok(response);
    }

    // Retrieve user by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), "User retrieved successfully", user);
        return ResponseEntity.ok(response);
    }

    // Save a new user
    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody User user) {
        user.setId(null);
        user.setRole(Role.USER);
        User newUser = userService.createUser(user);
        newUser.setPassword("********");
        ApiResponse<User> response = new ApiResponse<>(HttpStatus.CREATED.value(), "User created successfully", newUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update a user
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        user.setId(id);
        user.setRole(Role.USER);
        User updatedUser = userService.updateUser(user);
        updatedUser.setPassword("********");
        ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully", updatedUser);
        return ResponseEntity.ok(response);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
