package online.lcelectronics.api.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test for verifying JwtAuthenticationFilter's behavior with a valid token.
     * It ensures that the filter correctly sets up the authentication context
     * and allows the filter chain to proceed.
     */
    @Test
    void testDoFilterInternal_withValidToken() throws ServletException, IOException {
        // Mock token and username
        String token = "validToken";
        String username = "testUser";

        // Mock behavior of jwtService and userDetailsService
        when(jwtService.getUsernameFromToken(token)).thenReturn(username);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);

        // Mock authentication token creation
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, Collections.emptyList());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // Call doFilterInternal
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify that filterChain.doFilter() was called once
        verify(filterChain, times(1)).doFilter(request, response);
    }

    /**
     * Test for verifying JwtAuthenticationFilter's behavior with an invalid token.
     * It ensures that the filter proceeds without setting up any authentication context,
     * allowing the filter chain to proceed as normal.
     */
    @Test
    void testDoFilterInternal_withInvalidToken() throws ServletException, IOException {
        // Mock token (null in this case)
        String token = null;

        // Mock behavior of jwtService
        when(jwtService.getUsernameFromToken(token)).thenReturn(null);

        // Call doFilterInternal
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify that filterChain.doFilter() was called once
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
