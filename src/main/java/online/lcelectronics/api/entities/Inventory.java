package online.lcelectronics.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.lcelectronics.api.enums.Brand;
import online.lcelectronics.api.enums.Component;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Inventory {

    // Primary key for the Inventory table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Serial number of the inventory item
    private String serial;

    // Name or description of the inventory item
    private String name;

    // Quantity of new inventory items available
    private int quantityNew;

    // Quantity of used inventory items available
    private int quantityUsed;

    // Location where the inventory item is stored
    private String location;

    // Last known price of the inventory item
    private BigDecimal lastPrice;

    // Date when the last price was updated
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date dateLastPrice;

    // List of ApplianceModels that are compatible with this inventory item
    @ManyToMany
    private List<ApplianceModel> compatibleApplianceModels;

    // Component type of the inventory item (e.g., MAIN_BOARD, POWER_BOARD, etc.)
    @Enumerated(EnumType.STRING)
    private Component component;

    // Brand of the inventory item (e.g., SAMSUNG, LG, etc.)
    @Enumerated(EnumType.STRING)
    private Brand brand;

    // List of images associated with the inventory item
    @OneToMany
    private List<Image> images;
}
