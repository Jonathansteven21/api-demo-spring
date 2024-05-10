package online.lcelectronics.api.entities;

import jakarta.persistence.*;
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
    private int id;

    // MIME type of the image
    private String mime;

    // Name or description of the image
    private String name;

    // Content of the image stored as a byte array
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] content;
}
