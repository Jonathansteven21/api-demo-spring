package online.lcelectronics.api.entities.specs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

/**
 * This class provides specifications for querying Order entities based on various criteria.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderSpecification {


    // Creates a specification to filter orders by ID containing the specified value.
    public static Specification<Order> idContains(String id) {
        return (root, query, cb) -> id == null ? null : cb.like(root.get("id").as(String.class), "%" + id + "%");
    }


    // Creates a specification to filter orders by client's identity card.
    public static Specification<Order> hasClient(Long identityCard) {
        return (root, query, cb) -> identityCard == null ? null : cb.equal(root.get("client").get("identityCard"), identityCard);
    }


    // Creates a specification to filter orders by historic appliance serial number.
    public static Specification<Order> hasHistoricAppliance(String serial) {
        return (root, query, cb) -> serial == null ? null : cb.equal(root.get("historicAppliance").get("serial"), serial);
    }


    // Creates a specification to filter orders by status.
    public static Specification<Order> hasStatus(OrderStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }
}
