package online.lcelectronics.api.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Retrieve count users
    public int count() {
        return userRepository.findAll().size();
    }

    // Retrieve a user by its ID
    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
    }

    // Retrieve a user by its username
    public User getUserByUsername(String username) throws NotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found with username: " + username));
    }

    // Save a new user
    @Transactional
    public User createUser(User user) {
        String username = user.getUsername();
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("User with username " + username + " already exists");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Update username for a user
    @Transactional
    public User updateUsername(Long userId, String newUsername) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        existingUser.setUsername(newUsername);
        return userRepository.saveAndFlush(existingUser);
    }

    // Update password for a user
    @Transactional
    public User updatePassword(Long userId, String newPassword) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        existingUser.setPassword(new BCryptPasswordEncoder().encode(newPassword));

        return userRepository.saveAndFlush(existingUser);
    }

    // Delete a user
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

}


