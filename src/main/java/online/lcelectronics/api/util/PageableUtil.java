package online.lcelectronics.api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableUtil {

    public static Pageable createPageable(Integer page, Integer size, String sortBy, String sortDirection, String defaultSortBy) {
        // Validate page and size
        if (page == null || page < 0) {
            throw new IllegalArgumentException("Page number must be a non-negative integer.");
        }
        if (size == null || size <= 0) {
            throw new IllegalArgumentException("Page size must be a positive integer.");
        }

        // Set default values if sortBy is null or empty
        String sortField = (sortBy == null || sortBy.isEmpty()) ? defaultSortBy : sortBy;
        Sort.Direction direction = ("desc".equalsIgnoreCase(sortDirection)) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortField);

        return PageRequest.of(page, size, sort);
    }
}
