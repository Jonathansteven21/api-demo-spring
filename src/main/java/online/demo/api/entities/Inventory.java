package online.demo.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.demo.api.enums.Brand;
import online.demo.api.enums.Component;

import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Serial cannot be null")
    private String serial;

    // Name or description of the inventory item
    private String name;

    // Quantity of new inventory items available
    @NotNull(message = "quantityNew cannot be null")
    private Integer quantityNew;

    // Quantity of used inventory items available
    @NotNull(message = "quantityUsed cannot be null")
    private Integer quantityUsed;

    // Location where the inventory item is stored
    private String location;

    // Last known price of the inventory item
    private BigDecimal lastPrice;

    // Date when the last price was updated
    @Temporal(TemporalType.DATE)
    private Date dateLastPrice;

    // List of ApplianceModels that are compatible with this inventory item
    @ManyToMany
    private List<ApplianceModel> compatibleApplianceModels;

    // Component type of the inventory item (e.g., MAIN_BOARD, POWER_BOARD, etc.)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Component cannot be null")
    private Component component;

    // Brand of the inventory item (e.g., SAMSUNG, LG, etc.)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Brand cannot be null")
    private Brand brand;

    // List of images associated with the inventory item
    @OneToMany
    private List<Image> images;
}
