package online.lcelectronics.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.lcelectronics.api.enums.ApplianceCategory;
import online.lcelectronics.api.enums.Brand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.Year;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ApplianceModel {

    // Primary key for the ApplianceModel table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Model name of the appliance
    @NotBlank(message = "Model name cannot be blank")
    private String model;

    // Enumerated type representing the category of the appliance
    @NotNull(message = "Appliance category must be specified")
    @Enumerated(EnumType.STRING)
    private ApplianceCategory applianceCategory;

    // Enumerated type representing the brand of the appliance
    @NotNull(message = "Brand must be specified")
    @Enumerated(EnumType.STRING)
    private Brand brand;

    // Year when the appliance model was released
    @NotNull(message = "Year must be specified")
    @PastOrPresent(message = "Year must be in the past or present")
    private Year manufactureYear;
}

