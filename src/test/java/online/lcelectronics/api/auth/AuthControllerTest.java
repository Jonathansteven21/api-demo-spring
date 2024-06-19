package online.lcelectronics.api.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginRequest = new LoginRequest("testUser", "testPass");

        // Mocking AuthService login method to return a mock AuthResponse
        AuthResponse authResponse = new AuthResponse("mockToken");
        when(authService.login(any(LoginRequest.class))).thenReturn(authResponse);
    }

    /**
     * Test successful login scenario.
     * Ensures that the AuthController login method returns the correct response
     * when the login credentials are valid.
     */
    @Test
    void testLoginSuccess() {
        // Act
        ResponseEntity<AuthResponse> responseEntity = authController.login(loginRequest);

        // Assert
        verify(authService).login(loginRequest);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getToken()).isEqualTo("mockToken");
    }

    /**
     * Test login failure scenario.
     * Ensures that the AuthController login method throws an exception
     * when the login credentials are invalid.
     */
    @Test
    void testLoginFailure() {
        // Arrange
        when(authService.login(any(LoginRequest.class))).thenThrow(new RuntimeException("Invalid credentials"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            authController.login(loginRequest);
        });

        verify(authService).login(loginRequest);
    }
}
