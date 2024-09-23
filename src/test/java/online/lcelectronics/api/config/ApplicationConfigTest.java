package online.lcelectronics.api.config;

import online.demo.api.config.ApplicationConfig;
import online.demo.api.user.User;
import online.demo.api.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class ApplicationConfigTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ApplicationConfig applicationConfig;

    private AnnotationConfigApplicationContext context;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        context = new AnnotationConfigApplicationContext();
    }

    /**
     * Test for verifying that the authenticationProvider() method in ApplicationConfig returns
     * an instance of DaoAuthenticationProvider.
     */
    @Test
    void testAuthenticationProviderBean() {
        // Call method under test
        AuthenticationProvider authenticationProvider = applicationConfig.authenticationProvider();

        // Verify
        assertThat(authenticationProvider).isInstanceOf(DaoAuthenticationProvider.class);
    }

    /**
     * Test for verifying that the passwordEncoder() method in ApplicationConfig returns
     * an instance of BCryptPasswordEncoder.
     */
    @Test
    void testPasswordEncoderBean() {
        // Call method under test
        PasswordEncoder passwordEncoder = applicationConfig.passwordEncoder();

        // Verify
        assertThat(passwordEncoder).isInstanceOf(BCryptPasswordEncoder.class);
    }

    /**
     * Test for verifying that the userDetailService() method in ApplicationConfig returns
     * a valid UserDetailsService that can load user details.
     * Also tests exception handling when a user is not found.
     */
    @Test
    void testUserDetailsServiceBean() {
        // Mock UserRepository
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Call method under test
        UserDetailsService userDetailsService = applicationConfig.userDetailService();

        // Verify
        assertThat(userDetailsService).isNotNull();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(username);

        // Test exception handling
        String nonExistingUsername = "nonExistingUser";
        when(userRepository.findByUsername(nonExistingUsername)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(nonExistingUsername))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found");
    }
}
