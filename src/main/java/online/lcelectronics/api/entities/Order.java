package online.lcelectronics.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.lcelectronics.api.enums.OrderStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    // Primary key for the Order table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Many-to-One relationship with Client to link the order to a client
    @ManyToOne
    private Client client;

    // Description of the issue reported with the appliance
    private String issue;

    // Notes about the product received with the order
    private String productReceivedNotes;

    // Many-to-One relationship with HistoricAppliance to link the order to a historic appliance
    @ManyToOne
    private HistoricAppliance historicAppliance;

    // One-to-Many relationship with Image to associate multiple images with the order
    @OneToMany
    private List<Image> images;

    // Date and time when the order was created
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private LocalDate createdDate;

    // Status of the order, represented by an enum OrderStatus
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
