package online.lcelectronics.api.entities.specs;

import jakarta.persistence.criteria.*;
import online.lcelectronics.api.entities.ApplianceModel;
import online.lcelectronics.api.entities.Inventory;
import online.lcelectronics.api.enums.Brand;
import online.lcelectronics.api.enums.Component;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class InventorySpecificationTest {

    /**
     * Tests the withSerial method of InventorySpecification.
     * Verifies that the specification is created successfully and the predicate is formed correctly.
     */
    @Test
    void withSerial() {
        String serial = "123ABC";
        Specification<Inventory> spec = InventorySpecification.withSerial(serial);

        assertNotNull(spec);

        Root<Inventory> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        spec.toPredicate(root, query, cb);

        verify(cb).like(root.get("serial"), "%123ABC%");
    }

    /**
     * Tests the withLocation method of InventorySpecification.
     * Verifies that the specification is created successfully and the predicate is formed correctly.
     */
    @Test
    void withLocation() {
        String location = "Warehouse A";
        Specification<Inventory> spec = InventorySpecification.withLocation(location);

        assertNotNull(spec);

        Root<Inventory> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        spec.toPredicate(root, query, cb);

        verify(cb).equal(root.get("location"), location);
    }

    /**
     * Tests the withLastPriceLessThanOrEqual method of InventorySpecification.
     * Verifies that the specification is created successfully and the predicate is formed correctly.
     */
    @Test
    void withLastPriceLessThanOrEqual() {
        BigDecimal lastPrice = new BigDecimal("999.99");
        Specification<Inventory> spec = InventorySpecification.withLastPriceLessThanOrEqual(lastPrice);

        assertNotNull(spec);

        Root<Inventory> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        spec.toPredicate(root, query, cb);

        verify(cb).lessThanOrEqualTo(root.get("lastPrice"), lastPrice);
    }

    /**
     * Tests the withCompatibleApplianceModel method of InventorySpecification.
     * Verifies that the specification is created successfully and the predicate is formed correctly.
     */
    @Test
    void withCompatibleApplianceModel() {
        String model = "ModelX";
        Specification<Inventory> spec = InventorySpecification.withCompatibleApplianceModel(model);

        assertNotNull(spec);

        Root<Inventory> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        Join<Inventory, ApplianceModel> join = mock(Join.class);

        when(root.join("compatibleApplianceModels")).thenReturn(mock(Join.class));

        spec.toPredicate(root, query, cb);

        verify(cb).like(join.get("model"), "%" + model + "%");
    }

    /**
     * Tests the withComponent method of InventorySpecification.
     * Verifies that the specification is created successfully and the predicate is formed correctly.
     */
    @Test
    void withComponent() {
        Component component = Component.ACCESSORIES;
        Specification<Inventory> spec = InventorySpecification.withComponent(component);

        assertNotNull(spec);

        Root<Inventory> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        spec.toPredicate(root, query, cb);

        verify(cb).equal(root.get("component"), component);
    }

    /**
     * Tests the withBrand method of InventorySpecification.
     * Verifies that the specification is created successfully and the predicate is formed correctly.
     */
    @Test
    void withBrand() {
        Brand brand = Brand.SONY;
        Specification<Inventory> spec = InventorySpecification.withBrand(brand);

        assertNotNull(spec);

        Root<Inventory> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        spec.toPredicate(root, query, cb);

        verify(cb).equal(root.get("brand"), brand);
    }

    /**
     * Tests the withNameIgnoreCase method of InventorySpecification.
     * Verifies that the specification is created successfully and the predicate is formed correctly.
     */
    @Test
    void withNameIgnoreCase() {
        String name = "Gadget";
        Specification<Inventory> spec = InventorySpecification.withNameIgnoreCase(name);

        assertNotNull(spec);

        Root<Inventory> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        spec.toPredicate(root, query, cb);

        verify(cb).like(cb.lower(root.get("name")), "%gadget%");
    }
}
