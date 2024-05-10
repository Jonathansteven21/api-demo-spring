package online.lcelectronics.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderHistory {

    // Primary key for the OrderHistory table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Many-to-One relationship with Order to link the order history to an order
    @ManyToOne
    private Order order;

    // Date of the event recorded in the order history
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private LocalDate eventDate;

    // Text describing the event or action in the order history
    private String text;
}
