package online.lcelectronics.api.entities.specs;

import jakarta.persistence.criteria.Join;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import online.lcelectronics.api.entities.ApplianceModel;
import online.lcelectronics.api.entities.Inventory;
import online.lcelectronics.api.enums.Brand;
import online.lcelectronics.api.enums.Component;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

// Specification class for constructing dynamic queries to filter Inventory items.
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InventorySpecification {

    // Constructs a specification to filter inventory items by serial number containing the specified value.
    public static Specification<Inventory> serialContains(String serial) {
        return (root, query, cb) -> serial == null ? null : cb.like(root.get("serial"), "%" + serial + "%");
    }

    // Constructs a specification to filter inventory items by location.
    public static Specification<Inventory> hasLocation(String location) {
        return (root, query, cb) -> location == null ? null : cb.equal(root.get("location"), location);
    }

    // Constructs a specification to filter inventory items by last price less than or equal to the specified value.
    public static Specification<Inventory> lastPriceLessThanOrEqual(BigDecimal lastPrice) {
        return (root, query, cb) -> lastPrice == null ? null : cb.lessThanOrEqualTo(root.get("lastPrice"), lastPrice);
    }

    // Constructs a specification to filter inventory items by compatible appliance model.
    public static Specification<Inventory> hasCompatibleApplianceModel(String model) {
        return (root, query, cb) -> {
            if (model == null) {
                return null;
            }
            Join<Inventory, ApplianceModel> join = root.join("compatibleApplianceModels");
            return cb.like(join.get("model"), "%" + model + "%");
        };
    }

    // Constructs a specification to filter inventory items by component.
    public static Specification<Inventory> hasComponent(Component component) {
        return (root, query, cb) -> component == null ? null : cb.equal(root.get("component"), component);
    }

    // Constructs a specification to filter inventory items by brand.
    public static Specification<Inventory> hasBrand(Brand brand) {
        return (root, query, cb) -> brand == null ? null : cb.equal(root.get("brand"), brand);
    }

    // Constructs a specification to filter inventory items by name containing the specified value (case-insensitive).
    public static Specification<Inventory> nameContainsIgnoreCase(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
