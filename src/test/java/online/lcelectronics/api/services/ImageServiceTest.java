package online.lcelectronics.api.services;

import online.lcelectronics.api.entities.Image;
import online.lcelectronics.api.exceptions.NotFoundException;
import online.lcelectronics.api.repositories.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;

    private Image image;

    @BeforeEach
    void setUp() {
        image = new Image();
        image.setId(1);
        image.setMime("image/jpeg");
        image.setName("Test Image");
        image.setContent(new byte[]{1, 2, 3});
    }

    /**
     * Tests the getAllImages method of ImageService.
     * Verifies that all images are returned.
     */
    @Test
    void getAllImages() {
        List<Image> images = new ArrayList<>();
        images.add(image);
        when(imageRepository.findAll()).thenReturn(images);

        List<Image> result = imageService.getAllImages();
        assertEquals(1, result.size());
        assertEquals(image, result.get(0));
    }

    /**
     * Tests the getImageById method of ImageService with an existing ID.
     * Verifies that the correct image is returned.
     */
    @Test
    void getImageById_existingId() {
        when(imageRepository.findById(1)).thenReturn(Optional.of(image));

        Image result = imageService.getImageById(1);
        assertEquals(image, result);
    }

    /**
     * Tests the getImageById method of ImageService with a non-existing ID.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void getImageById_nonExistingId() {
        when(imageRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> imageService.getImageById(1));
    }

    /**
     * Tests the saveImage method of ImageService with a valid image.
     * Verifies that the image is saved successfully.
     */
    @Test
    void saveImage_validImage() {
        when(imageRepository.save(any(Image.class))).thenReturn(image);

        Image result = imageService.saveImage(image);
        assertEquals(image, result);
    }

    /**
     * Tests the updateImage method of ImageService with an existing image.
     * Verifies that the image is updated successfully.
     */
    @Test
    void updateImage_existingImage() {
        when(imageRepository.existsById(1)).thenReturn(true);
        when(imageRepository.saveAndFlush(any(Image.class))).thenReturn(image);

        Image result = imageService.updateImage(image);
        assertEquals(image, result);
    }

    /**
     * Tests the updateImage method of ImageService with a non-existing image.
     * Verifies that a NotFoundException is thrown.
     */
    @Test
    void updateImage_nonExistingImage() {
        when(imageRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> imageService.updateImage(image));
    }

    /**
     * Tests the deleteImageById method of ImageService with an existing ID.
     * Verifies that the image is deleted successfully.
     */
    @Test
    void deleteImageById_existingId() {
        doNothing().when(imageRepository).deleteById(1);

        imageService.deleteImageById(1);
        verify(imageRepository, times(1)).deleteById(1);
    }
}
