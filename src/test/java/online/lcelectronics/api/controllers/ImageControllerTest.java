package online.lcelectronics.api.controllers;

import online.lcelectronics.api.entities.Image;
import online.lcelectronics.api.responses.ApiResponse;
import online.lcelectronics.api.services.ImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    ImageService imageService;

    @InjectMocks
    ImageController imageController;

    /**
     * Tests the getAllImages method of ImageController.
     * Verifies that all images are retrieved successfully.
     */
    @Test
    void getAllImages() {
        List<Image> images = new ArrayList<>();
        images.add(new Image());
        images.add(new Image());

        when(imageService.getAllImages()).thenReturn(images);

        ResponseEntity<ApiResponse<List<Image>>> responseEntity = imageController.getAllImages();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Images retrieved", responseEntity.getBody().getMessage());
        assertEquals(images, responseEntity.getBody().getData());
    }

    /**
     * Tests the getImageById method of ImageController with an existing ID.
     * Verifies that the image with the given ID is retrieved successfully.
     */
    @Test
    void getImageById() {
        Image image = new Image();
        image.setId(1);

        when(imageService.getImageById(1)).thenReturn(image);

        ResponseEntity<ApiResponse<Image>> responseEntity = imageController.getImageById(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Image retrieved", responseEntity.getBody().getMessage());
        assertEquals(image, responseEntity.getBody().getData());
    }

    /**
     * Tests the saveImage method of ImageController with a valid image.
     * Verifies that the image is successfully saved.
     */
    @Test
    void saveImage() {
        Image image = new Image();
        image.setName("Test Image");
        Image savedImage = new Image();
        savedImage.setId(1);
        savedImage.setName("Test Image");

        when(imageService.saveImage(image)).thenReturn(savedImage);

        ResponseEntity<ApiResponse<Image>> responseEntity = imageController.saveImage(image);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(HttpStatus.CREATED.value(), responseEntity.getBody().getStatus());
        assertEquals("Image saved", responseEntity.getBody().getMessage());
        assertEquals(savedImage, responseEntity.getBody().getData());
    }

    /**
     * Tests the updateImage method of ImageController with an existing image.
     * Verifies that the image is successfully updated.
     */
    @Test
    void updateImage() {
        Image image = new Image();
        image.setId(1);
        image.setName("Updated Image");

        when(imageService.updateImage(image)).thenReturn(image);

        ResponseEntity<ApiResponse<Image>> responseEntity = imageController.updateImage(1, image);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getStatus());
        assertEquals("Image updated", responseEntity.getBody().getMessage());
        assertEquals(image, responseEntity.getBody().getData());
    }

    /**
     * Tests the deleteImageById method of ImageController.
     * Verifies that the image is successfully deleted.
     */
    @Test
    void deleteImageById() {
        ResponseEntity<ApiResponse<Void>> responseEntity = imageController.deleteImageById(1);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(imageService, times(1)).deleteImageById(1);
    }
}
