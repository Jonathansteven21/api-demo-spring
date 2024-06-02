package online.lcelectronics.api.services;

import online.lcelectronics.api.entities.User;
import online.lcelectronics.api.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDetailsImplTest {

    /**
     * Test the build method of UserDetailsImpl to ensure it returns the correct UserDetails object.
     */
    @Test
    void build_ReturnsCorrectUserDetails() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setRole(Role.USER);

        GrantedAuthority expectedAuthority = new SimpleGrantedAuthority(Role.USER.name());

        // Act
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        // Assert
        assertEquals(user.getId(), userDetails.getId());
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(Collections.singletonList(expectedAuthority), userDetails.getAuthorities());
    }
}
