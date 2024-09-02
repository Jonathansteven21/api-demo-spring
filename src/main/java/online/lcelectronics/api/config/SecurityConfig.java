package online.lcelectronics.api.config;

import online.lcelectronics.api.user.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import online.lcelectronics.api.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String adminRole = Role.ADMIN.toString();
        String userRole = Role.USER.toString();
        String imageEditorRole = Role.IMAGE_EDITOR.toString();

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/api/auth/**","/api/orders/reference/**").permitAll()
                        .requestMatchers("/api/users/**").hasAuthority(adminRole)
                        .requestMatchers(HttpMethod.GET
                                , "/api/images", "/api/repair-costs", "/api/order-history", "/api/client-payments")
                        .hasAuthority(adminRole)
                        .requestMatchers(HttpMethod.PUT
                                , "/api/repair-costs/**", "/api/order-history/**", "/api/historic-appliances/**","/api/client-payments/**")
                        .hasAuthority(adminRole)
                        .requestMatchers(HttpMethod.PUT
                                , "/api/images/**")
                        .hasAnyAuthority(adminRole,userRole,imageEditorRole)
                        .requestMatchers(HttpMethod.DELETE, "/api/images").hasAuthority(adminRole)
                        .anyRequest().hasAnyAuthority(adminRole,userRole))
                .sessionManagement(sessionManager -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

}