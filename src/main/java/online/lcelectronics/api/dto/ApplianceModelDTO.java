package online.lcelectronics.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.lcelectronics.api.enums.ApplianceCategory;
import online.lcelectronics.api.enums.Brand;
import online.lcelectronics.api.enums.Component;

import java.time.Year;

/**
 * This class represents a DTO (Data Transfer Object) for the ApplianceModel entity.
 * It includes attributes such as id, model, applianceCategory, brand, year, and componentList.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
public class ApplianceModelDTO {

    // Primary key for the ApplianceModel table
    private int id;

    // Model name of the appliance
    private String model;

    // Enumerated type representing the category of the appliance
    private ApplianceCategory applianceCategory;

    // Enumerated type representing the brand of the appliance
    private Brand brand;

    // Year when the appliance model was released
    private Year year;

    // List of components for the appliance model
    private Component[] componentList;
}
