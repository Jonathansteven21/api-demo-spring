package online.demo.api.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.login(request);

        // Encode the token using Base64
        String encodedToken = Base64.getEncoder().encodeToString(authResponse.getToken().getBytes());

        // Create the cookie with the encoded token
        Cookie cookie = new Cookie("token", encodedToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(7200); // 2 hours expiration
        response.addCookie(cookie);

        return ResponseEntity.ok(authResponse);
    }

    @GetMapping(value = "logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        // Create a cookie with the same name and set its max age to 0
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // This will delete the cookie
        response.addCookie(cookie);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/verify-role")
    public ResponseEntity<Map<String, String>> verifyRole() {
        Map<String, String> response = new HashMap<>();
        String role= SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
        response.put("role", role);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}