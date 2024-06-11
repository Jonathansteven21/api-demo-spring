package online.lcelectronics.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.User;
import online.lcelectronics.api.exceptions.NotFoundException;
import online.lcelectronics.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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
        user.setPassword(user.getPassword());
        return userRepository.save(user);
    }

    // Update a user
    @Transactional
    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new NotFoundException("User not found with ID: " + user.getId());
        }
        user.setPassword((user.getPassword()));
        return userRepository.saveAndFlush(user);
    }

    // Delete a user
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

}


