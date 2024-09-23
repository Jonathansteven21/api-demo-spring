package online.demo.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RepairCost {

    // Primary key for the RepairCost table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Many-to-One relationship with Order to link the repair cost to an order
    @ManyToOne
    @NotNull(message = "order must not be null")
    private Order order;

    // Description of the repair cost
    @NotEmpty(message = "description must not be null or empty")
    private String description;

    // Amount of the repair cost
    @Positive(message = "amount must be greater than zero")
    private BigDecimal amount;
}
