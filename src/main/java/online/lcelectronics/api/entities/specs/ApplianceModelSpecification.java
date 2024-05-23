package online.lcelectronics.api.entities.specs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import online.lcelectronics.api.entities.ApplianceModel;
import online.lcelectronics.api.enums.ApplianceCategory;
import online.lcelectronics.api.enums.Brand;
import org.springframework.data.jpa.domain.Specification;

import java.time.Year;

// This class provides specifications for querying ApplianceModel entities based on various criteria.
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplianceModelSpecification {

    // Creates a specification to filter ApplianceModel entities by model, ignoring case.
    public static Specification<ApplianceModel> modelContainsIgnoreCase(String model) {
        return (root, query, cb) -> model == null ? null : cb.like(cb.lower(root.get("model")), "%" + model.toLowerCase() + "%");
    }

    // Creates a specification to filter ApplianceModel entities by appliance category.
    public static Specification<ApplianceModel> hasApplianceCategory(ApplianceCategory applianceCategory) {
        return (root, query, cb) -> applianceCategory == null ? null : cb.equal(root.get("applianceCategory"), applianceCategory);
    }

    // Creates a specification to filter ApplianceModel entities by year greater than or equal to the specified year.
    public static Specification<ApplianceModel> yearGreaterThanOrEqual(Year year) {
        return (root, query, cb) -> year == null ? null : cb.greaterThanOrEqualTo(root.get("manufactureYear"), year.getValue());
    }

    // Creates a specification to filter ApplianceModel entities by brand.
    public static Specification<ApplianceModel> hasBrand(Brand brand) {
        return (root, query, cb) -> brand == null ? null : cb.equal(root.get("brand"), brand);
    }
}


