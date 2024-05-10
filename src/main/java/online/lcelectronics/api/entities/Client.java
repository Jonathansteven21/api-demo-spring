package online.lcelectronics.api.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client {

    // Primary key for the Client table
    @Id
    private int identityCard;

    // Name of the client
    private String name;

    // Phone number of the client
    private int phone;

    // Address of the client
    private String address;
}
