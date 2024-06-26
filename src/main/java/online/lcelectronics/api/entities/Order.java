package online.lcelectronics.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import online.lcelectronics.api.enums.OrderStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Order {

    // Primary key for the Order table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Many-to-One relationship with Client to link the order to a client
    @ManyToOne
    @NotNull(message = "Client must not be null")
    private Client client;

    // Description of the issue reported with the appliance
    @NotEmpty(message = "Issue must not be empty")
    private String issue;

    // Notes about the product received with the order
    @NotEmpty(message = "Product received notes must not be empty")
    private String productReceivedNotes;

    // Many-to-One relationship with HistoricAppliance to link the order to a historic appliance
    @ManyToOne
    @NotNull(message = "Historic appliance must not be null")
    private HistoricAppliance historicAppliance;

    // One-to-Many relationship with Image to associate multiple images with the order
    @OneToMany
    @NotEmpty(message = "Images must not be empty")
    private List<Image> images;

    // Date and time when the order was created
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private LocalDate createdDate;

    // Status of the order, represented by an enum OrderStatus
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status must not be null")
    private OrderStatus status;

    // Indicates if the order is under warranty
    @Column(nullable = false)
    private Boolean warranty;

    // Unique reference code (access key)
    @Column(unique = true, nullable = false, length = 36)
    private String referenceCode;
}
