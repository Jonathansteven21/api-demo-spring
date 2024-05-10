package online.lcelectronics.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RepairCost {

    // Primary key for the RepairCost table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Many-to-One relationship with Order to link the repair cost to an order
    @ManyToOne
    private Order order;

    // Description of the repair cost
    private String description;

    // Amount of the repair cost
    private BigDecimal amount;
}
