package online.lcelectronics.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.Image;
import online.lcelectronics.api.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageService {

    private final ImageRepository imageRepository;

    // Retrieve all images
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    // Retrieve an image by its ID
    public Image getImageById(int id) {
        Optional<Image> optionalImage = imageRepository.findById(id);
        return optionalImage.orElse(null);
    }

    // Save a new image
    @Transactional
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    // Update an existing image
    @Transactional
    public Image updateImage(Image image) {
        return imageRepository.saveAndFlush(image);
    }

    // Delete an image by its ID
    @Transactional
    public void deleteImageById(int id) {
        imageRepository.deleteById(id);
    }

}
