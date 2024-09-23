package online.demo.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
    @NotEmpty(message = "Serial cannot be empty")
    private String serial;

    // Many-to-One relationship with ApplianceModel to link the appliance model
    @ManyToOne
    @JoinColumn(name = "model_id")
    @NotNull(message = "Model must be provided")
    private ApplianceModel model;

    // Date when the appliance was manufactured
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Manufacture date must be provided")
    @Past(message = "Manufacture date must be in the past")
    private Date manufactureDate;
}
