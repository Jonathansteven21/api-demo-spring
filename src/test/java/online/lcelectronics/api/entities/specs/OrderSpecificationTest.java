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

    @Test
    void withId() {
        String id = "12345";
        Specification<Order> spec = OrderSpecification.withId(id);

        assertNotNull(spec);

        // Mocking the necessary classes for testing the specification
        Root<Order> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        when(root.get("id")).thenReturn(mock(Path.class));

        spec.toPredicate(root, query, cb);

        verify(cb).like(root.get("id").as(String.class), "%" + id + "%");
    }



    @Test
    void withClientIdentityCard() {
        Long identityCard = 123456789L;
        Specification<Order> spec = OrderSpecification.withClientIdentityCard(identityCard);

        assertNotNull(spec);

        // Mocking the necessary classes for testing the specification
        Root<Order> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        when(root.get("client")).thenReturn(mock(Path.class));

        spec.toPredicate(root, query, cb);

        // Verifying that the 'equal' method was called with the correct parameters
        verify(cb).equal(root.get("client").get("identityCard"), identityCard);
    }

    @Test
    void withHistoricAppliance() {
        String serial = "ABC123";
        Specification<Order> spec = OrderSpecification.withHistoricAppliance(serial);

        assertNotNull(spec);

        // Mocking the necessary classes for testing the specification
        Root<Order> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        when(root.get("historicAppliance")).thenReturn(mock(Path.class));

        spec.toPredicate(root, query, cb);

        // Verifying that the 'equal' method was called with the correct parameters
        verify(cb).equal(root.get("historicAppliance").get("serial"), serial);
    }

    @Test
    void withStatus() {
        OrderStatus status = OrderStatus.COMPLETED;
        Specification<Order> spec = OrderSpecification.withStatus(status);

        assertNotNull(spec);

        // Mocking the necessary classes for testing the specification
        Root<Order> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        spec.toPredicate(root, query, cb);

        // Verifying that the 'equal' method was called with the correct parameters
        verify(cb).equal(root.get("status"), status);
    }

    @Test
    void withCreatedDate() {
        LocalDate createdDate = LocalDate.of(2023, 5, 15);
        Specification<Order> spec = OrderSpecification.withCreatedDate(createdDate);

        assertNotNull(spec);

        // Mocking the necessary classes for testing the specification
        Root<Order> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        spec.toPredicate(root, query, cb);

        // Verifying that the 'equal' method was called with the correct parameters
        verify(cb).equal(root.get("createdDate"), createdDate);
    }
}
