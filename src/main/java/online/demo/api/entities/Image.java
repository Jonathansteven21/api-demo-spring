package online.demo.api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Image {

    // Primary key for the Image table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // MIME type of the image
    @NotEmpty(message = "MIME type cannot be empty")
    private String mime;

    // Name or description of the image
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    // Content of the image stored as a byte array
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "MEDIUMBLOB")
    @NotEmpty(message = "Content cannot be empty")
    private byte[] content;
}

