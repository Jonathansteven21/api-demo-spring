package online.lcelectronics.api.auth;

import online.demo.api.auth.AuthResponse;
import online.demo.api.auth.AuthService;
import online.demo.api.auth.LoginRequest;
import online.demo.api.jwt.JwtService;
import online.demo.api.user.Role;
import online.demo.api.user.User;
import online.demo.api.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test for the login method in AuthService.
     * Ensures that a valid user can log in and receive a JWT token.
     */
    @Test
    void testLogin() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("testUser", "testPass");
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setRole(Role.ADMIN);
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.getToken(user)).thenReturn("mockToken");

        // Act
        AuthResponse response = authService.login(loginRequest);

        // Assert
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByUsername(loginRequest.getUsername());
        verify(jwtService).getToken(user);
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("mockToken");
    }

    /**
     * Test for the login method in AuthService.
     * Ensures that an exception is thrown when the user is not found.
     */
    @Test
    void testLoginThrowsExceptionWhenUserNotFound() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("testUser", "testPass");
        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(RuntimeException.class);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByUsername(loginRequest.getUsername());
        verify(jwtService, never()).getToken(any(UserDetails.class));
    }
}
