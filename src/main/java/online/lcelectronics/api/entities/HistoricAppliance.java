package online.lcelectronics.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class HistoricAppliance {

    // Primary key for the HistoricAppliance table
    @Id
    private String serial;

    // Many-to-One relationship with ApplianceModel to link the appliance model
    @ManyToOne
    private ApplianceModel model;

    // Date when the appliance was manufactured
    @Temporal(TemporalType.DATE)
    private Date manufactureDate;
}
