package online.lcelectronics.api.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping(value = "/health-check")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ON");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/verify-token")
    public ResponseEntity<Map<String, String>> verifyToken() {
        Map<String, String> response = new HashMap<>();
        String role= SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();
        response.put("role", role);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}