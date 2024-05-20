package online.lcelectronics.api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client {

    // Primary key for the Client table
    @Id
    @NotNull(message = "Identity card cannot be null")
    private Integer identityCard;

    // Name of the client
    @NotBlank(message = "Name cannot be blank")
    private String name;

    // Phone number of the client
    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "(^$|\\d{10})", message = "Invalid phone number")
    private Integer phone;

    // Address of the client
    @NotBlank(message = "Address cannot be blank")
    private String address;
}
