package online.demo.api.repositories;

import online.demo.api.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

// This interface defines methods to access Image entities in the database
public interface ImageRepository extends JpaRepository<Image, Integer> {
}
