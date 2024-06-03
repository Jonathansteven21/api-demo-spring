package online.lcelectronics.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.User;
import online.lcelectronics.api.exceptions.NotFoundException;
import online.lcelectronics.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements UserDetailsService {

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
    public User getUserById(Long id) throws NotFoundException {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("User not found with ID: " + id);
        }
        return user;
    }

    // Retrieve a user by its username
    public User getUserByUsername(String username) throws NotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new NotFoundException("User not found with username: " + username);
        }
        return user;
    }

    // Save a new user
    @Transactional
    public User createUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Update a user
    @Transactional
    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new NotFoundException("User not found with ID: " + user.getId());
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.saveAndFlush(user);
    }

    // Delete a user
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user by username from the UserRepository
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        // Create a GrantedAuthority based on the user's role
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().toString());

        // Create and return a UserDetails object using the found user's details
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(authority));
    }

}


