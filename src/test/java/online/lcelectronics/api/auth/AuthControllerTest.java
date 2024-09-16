package online.lcelectronics.api.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    private AuthController authController;

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loginRequest = new LoginRequest("testUser", "testPass");

        // Mocking AuthService login method to return a mock AuthResponse
        AuthResponse authResponse = new AuthResponse("mockToken", "ADMIN", 100);
        when(authService.login(any(LoginRequest.class))).thenReturn(authResponse);
    }

    /**
     * Test successful login scenario.
     * Ensures that the AuthController login method returns the correct response
     * and sets the cookie with the encoded token.
     */
    @Test
    void testLoginSuccess() {
        // Act
        ResponseEntity<AuthResponse> responseEntity = authController.login(loginRequest, httpServletResponse);

        // Assert
        verify(authService).login(loginRequest);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getToken()).isEqualTo("mockToken");

        // Verify the cookie was added with the Base64 encoded token
        verify(httpServletResponse).addCookie(any(Cookie.class));
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
        assertThrows(RuntimeException.class, () -> authController.login(loginRequest, httpServletResponse));
        verify(authService).login(loginRequest);
    }

    /**
     * Test logout method.
     * Ensures that the AuthController logout method removes the token cookie by setting its max age to 0.
     */
    @Test
    void testLogout() {
        // Arrange
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);

        // Act
        ResponseEntity<Void> responseEntity = authController.logout(httpServletResponse);

        // Assert
        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(httpServletResponse, times(1)).addCookie(cookieCaptor.capture());

        Cookie capturedCookie = cookieCaptor.getValue();
        assertThat(capturedCookie.getName()).isEqualTo("token");
        assertThat(capturedCookie.getValue()).isNull(); // The value is null because we're deleting the cookie
        assertThat(capturedCookie.getMaxAge()).isEqualTo(0); // The max age is 0, meaning the cookie is deleted
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    /**
     * Test verifyRole method.
     * Simulates a user with a specific role in the SecurityContext and ensures
     * that the correct role is returned by the controller.
     */
    @Test
    void testVerifyRole() {
        // Arrange
        UserDetails userDetails = new User("testUser", "testPass", Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        Authentication authentication = mock(Authentication.class);

        // Use explicit casting to avoid type issues with Mockito
        when(authentication.getAuthorities()).thenReturn((Collection) userDetails.getAuthorities());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        ResponseEntity<Map<String, String>> responseEntity = authController.verifyRole();

        // Assert
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().get("role")).isEqualTo("ROLE_ADMIN");
    }

}
