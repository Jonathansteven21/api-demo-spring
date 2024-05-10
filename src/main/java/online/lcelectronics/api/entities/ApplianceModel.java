package online.lcelectronics.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.lcelectronics.api.enums.ApplianceCategory;
import online.lcelectronics.api.enums.Brand;

import java.time.Year;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ApplianceModel {

    // Primary key for the ApplianceModel table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Model name of the appliance
    private String model;

    // Enumerated type representing the category of the appliance
    @Enumerated(EnumType.STRING)
    private ApplianceCategory applianceCategory;

    // Enumerated type representing the brand of the appliance
    @Enumerated(EnumType.STRING)
    private Brand brand;

    // Year when the appliance model was released
    private Year year;
}
