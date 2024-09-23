package online.demo.api.auth;

import online.demo.api.jwt.JwtService;
import online.demo.api.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);

        return AuthResponse.builder()
                .token(token)
                .role(user.getAuthorities().toArray()[0].toString())
                .time(JwtService.EXPIRATION_TIME_SECONDS)
                .build();

    }
}
