package online.lcelectronics.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.lcelectronics.api.enums.Role;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    // Primary key for the User table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Username of the user
    @NotEmpty(message = "Username must not be null or empty")
    private String username;

    // Password of the user
    @NotEmpty(message = "Password must not be null or empty")
    private String password;

    // Role of the user
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role cannot be null")
    private Role role;
}
