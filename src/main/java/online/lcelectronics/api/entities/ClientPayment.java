package online.lcelectronics.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ClientPayment {

    // Primary key for the ClientPayment table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Many-to-One relationship with Order to link the payment to an order
    @ManyToOne
    @NotNull(message = "Order must be provided")
    private Order order;

    // Date when the payment was made
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    // Amount of the payment
    @NotNull(message = "Amount must be provided")
    @Positive(message = "amount must be greater than zero")
    private BigDecimal amount;
}
