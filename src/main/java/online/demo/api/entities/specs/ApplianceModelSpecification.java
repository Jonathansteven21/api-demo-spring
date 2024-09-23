package online.demo.api.entities.specs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import online.demo.api.entities.ApplianceModel;
import online.demo.api.enums.ApplianceCategory;
import online.demo.api.enums.Brand;
import org.springframework.data.jpa.domain.Specification;

import java.time.Year;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplianceModelSpecification {

    // Creates a specification to filter ApplianceModel entities by model, ignoring case.
    public static Specification<ApplianceModel> withModel(String model) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("model")), "%" + model.toLowerCase() + "%");
    }

    // Creates a specification to filter ApplianceModel entities by appliance category.
    public static Specification<ApplianceModel> withApplianceCategory(ApplianceCategory applianceCategory) {
        return (root, query, cb) -> cb.equal(root.get("applianceCategory"), applianceCategory);
    }

    // Creates a specification to filter ApplianceModel entities by year greater than or equal to the specified year.
    public static Specification<ApplianceModel> withYearGreaterThanOrEqual(Year year) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("manufactureYear"), year.getValue());
    }

    // Creates a specification to filter ApplianceModel entities by brand.
    public static Specification<ApplianceModel> withBrand(Brand brand) {
        return (root, query, cb) -> cb.equal(root.get("brand"), brand);
    }
}


