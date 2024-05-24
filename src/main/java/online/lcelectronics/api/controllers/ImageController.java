package online.lcelectronics.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import online.lcelectronics.api.entities.Image;
import online.lcelectronics.api.services.ImageService;
import online.lcelectronics.api.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageController {

    private final ImageService imageService;

    // Get all images
    @GetMapping
    public ResponseEntity<ApiResponse<List<Image>>> getAllImages() {
        List<Image> images = imageService.getAllImages();
        ApiResponse<List<Image>> response = new ApiResponse<>(HttpStatus.OK.value(), "Images retrieved", images);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get an image by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Image>> getImageById(@PathVariable Integer id) {
        Image image = imageService.getImageById(id);
        ApiResponse<Image> response = new ApiResponse<>(HttpStatus.OK.value(), "Image retrieved", image);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Save a new image
    @PostMapping
    public ResponseEntity<ApiResponse<Image>> saveImage(@Valid @RequestBody Image image) {
        image.setId(null);
        Image savedImage = imageService.saveImage(image);
        ApiResponse<Image> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Image saved", savedImage);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Update an existing image
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Image>> updateImage(@Valid @PathVariable Integer id, @RequestBody Image image) {
        if (!id.equals(image.getId())) {
            throw new IllegalArgumentException("ID in path does not match the one in the request body");
        }
        Image updatedImage = imageService.updateImage(image);
        ApiResponse<Image> response = new ApiResponse<>(HttpStatus.OK.value(), "Image updated", updatedImage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete an image by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteImageById(@PathVariable Integer id) {
        imageService.deleteImageById(id);
        ApiResponse<Void> response = new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "Image deleted", null);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
