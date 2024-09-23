package online.demo.api.dto;

import lombok.*;

/**
 * This class represents a DTO (Data Transfer Object) for the Client entity.
 * It includes attributes such as identityCard and name.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
public class ClientDTO {

    // Identity card number of the client (primary key)
    private Long identityCard;

    // Name of the client
    private String name;
}
