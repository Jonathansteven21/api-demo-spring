package online.demo.api.entities.specs;

import jakarta.persistence.criteria.Join;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import online.demo.api.entities.ApplianceModel;
import online.demo.api.entities.Inventory;
import online.demo.api.enums.Brand;
import online.demo.api.enums.Component;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InventorySpecification {

    // Constructs a specification to filter inventory items by serial number containing the specified value.
    public static Specification<Inventory> withSerial(String serial) {
        return (root, query, cb) -> cb.like(root.get("serial"), "%" + serial + "%");
    }

    // Constructs a specification to filter inventory items by location.
    public static Specification<Inventory> withLocation(String location) {
        return (root, query, cb) -> cb.equal(root.get("location"), location);
    }

    // Constructs a specification to filter inventory items by last price less than or equal to the specified value.
    public static Specification<Inventory> withLastPriceLessThanOrEqual(BigDecimal lastPrice) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("lastPrice"), lastPrice);
    }

    // Constructs a specification to filter inventory items by compatible appliance model.
    public static Specification<Inventory> withCompatibleApplianceModel(String model) {
        return (root, query, cb) -> {
            Join<Inventory, ApplianceModel> join = root.join("compatibleApplianceModels");
            return cb.like(join.get("model"), "%" + model + "%");
        };
    }

    // Constructs a specification to filter inventory items by component.
    public static Specification<Inventory> withComponent(Component component) {
        return (root, query, cb) -> cb.equal(root.get("component"), component);
    }

    // Constructs a specification to filter inventory items by brand.
    public static Specification<Inventory> withBrand(Brand brand) {
        return (root, query, cb) -> cb.equal(root.get("brand"), brand);
    }

    // Constructs a specification to filter inventory items by name containing the specified value (case-insensitive).
    public static Specification<Inventory> withNameIgnoreCase(String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
