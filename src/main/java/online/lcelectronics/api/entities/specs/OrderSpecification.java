package online.lcelectronics.api.entities.specs;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderSpecification {

    // Creates a specification to filter orders by ID containing the specified value.
    public static Specification<Order> withId(String id) {
        return (root, query, cb) -> cb.like(root.get("id").as(String.class), "%" + id + "%");
    }

    // Specification to filter orders by client's identity card
    public static Specification<Order> withClientIdentityCard(Long identityCard) {
        return (root, query, cb) -> cb.equal(root.get("client").get("identityCard"), identityCard);
    }

    // Creates a specification to filter orders by historic appliance serial number.
    public static Specification<Order> withHistoricAppliance(String serial) {
        return (root, query, cb) -> cb.equal(root.get("historicAppliance").get("serial"), serial);
    }

    // Creates a specification to filter orders by status.
    public static Specification<Order> withStatus(OrderStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    // Creates a specification to filter orders by created date.
    public static Specification<Order> withCreatedDate(LocalDate createdDate) {
        return (root, query, cb) -> cb.equal(root.get("createdDate"), createdDate);
    }
}
