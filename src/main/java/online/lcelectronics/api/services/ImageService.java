package online.lcelectronics.api.services;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.Image;
import online.lcelectronics.api.exceptions.NotFoundException;
import online.lcelectronics.api.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageService {

    private final ImageRepository imageRepository;

    // Retrieve all images
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    // Retrieve an image by its ID
    public Image getImageById(@NotNull(message = "ID cannot be null") Integer id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Image not found with ID: " + id));
    }

    // Save a new image with validation
    @Transactional
    public Image saveImage(@Valid Image image) {
        return imageRepository.save(image);
    }

    // Update an existing image with validation
    @Transactional
    public Image updateImage(@Valid Image image) {
        if (!imageRepository.existsById(image.getId())) {
            throw new NotFoundException("Image not found with ID: " + image.getId());
        }
        return imageRepository.saveAndFlush(image);
    }

    // Delete an image by its ID
    @Transactional
    public void deleteImageById(@NotNull(message = "ID cannot be null") Integer id) {
        imageRepository.deleteById(id);
    }
}
