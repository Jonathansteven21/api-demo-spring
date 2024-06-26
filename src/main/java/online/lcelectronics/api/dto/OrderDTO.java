package online.lcelectronics.api.dto;

import jakarta.persistence.Column;
import lombok.*;
import online.lcelectronics.api.entities.HistoricAppliance;
import online.lcelectronics.api.entities.Image;
import online.lcelectronics.api.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * This class represents a DTO (Data Transfer Object) for the Order entity.
 * It includes attributes such as id, client, issue, productReceivedNotes, historicAppliance,
 * images, createdDate, and status.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
public class OrderDTO {

    // Primary key for the Order table
    private Integer id;

    // Client information (DTO)
    private ClientDTO client;

    // Description of the issue reported with the appliance
    private String issue;

    // Notes about the product received with the order
    private String productReceivedNotes;

    // Historic appliance information
    private HistoricAppliance historicAppliance;

    // List of images associated with the order
    private List<Image> images;

    // Date when the order was created
    private LocalDate createdDate;

    // Indicates if the order is under warranty
    private Boolean warranty;

    // Status of the order
    private OrderStatus status;

}
