package online.lcelectronics.api.entities.specs;

import jakarta.persistence.criteria.*;
import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class OrderSpecificationTest {

    /**
     * Tests the withId method of OrderSpecification.
     * Verifies that the specification is created successfully and the predicate is formed correctly.
     */
    @Test
    void withId() {
        String id = "12345";
        Specification<Order> spec = OrderSpecification.withId(id);

        assertNotNull(spec);

        Root<Order> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        when(root.get("id")).thenReturn(mock(Path.class));

        spec.toPredicate(root, query, cb);

        verify(cb).like(root.get("id").as(String.class), "%" + id + "%");
    }

    /**
     * Tests the withClientIdentityCard method of OrderSpecification.
     * Verifies that the specification is created successfully and the predicate is formed correctly.
     */
    @Test
    void withClientIdentityCard() {
        Long identityCard = 123456789L;
        Specification<Order> spec = OrderSpecification.withClientIdentityCard(identityCard);

        assertNotNull(spec);

        Root<Order> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        when(root.get("client")).thenReturn(mock(Path.class));

        spec.toPredicate(root, query, cb);

        verify(cb).equal(root.get("client").get("identityCard"), identityCard);
    }

    /**
     * Tests the withHistoricAppliance method of OrderSpecification.
     * Verifies that the specification is created successfully and the predicate is formed correctly.
     */
    @Test
    void withHistoricAppliance() {
        String serial = "ABC123";
        Specification<Order> spec = OrderSpecification.withHistoricAppliance(serial);

        assertNotNull(spec);

        Root<Order> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        when(root.get("historicAppliance")).thenReturn(mock(Path.class));

        spec.toPredicate(root, query, cb);

        verify(cb).equal(root.get("historicAppliance").get("serial"), serial);
    }

    /**
     * Tests the withStatus method of OrderSpecification.
     * Verifies that the specification is created successfully and the predicate is formed correctly.
     */
    @Test
    void withStatus() {
        OrderStatus status = OrderStatus.COMPLETED;
        Specification<Order> spec = OrderSpecification.withStatus(status);

        assertNotNull(spec);

        Root<Order> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        spec.toPredicate(root, query, cb);

        verify(cb).equal(root.get("status"), status);
    }

    /**
     * Tests the withCreatedDate method of OrderSpecification.
     * Verifies that the specification is created successfully and the predicate is formed correctly.
     */
    @Test
    void withCreatedDate() {
        LocalDate createdDate = LocalDate.of(2023, 5, 15);
        Specification<Order> spec = OrderSpecification.withCreatedDate(createdDate);

        assertNotNull(spec);

        Root<Order> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        spec.toPredicate(root, query, cb);

        verify(cb).equal(root.get("createdDate"), createdDate);
    }
}
