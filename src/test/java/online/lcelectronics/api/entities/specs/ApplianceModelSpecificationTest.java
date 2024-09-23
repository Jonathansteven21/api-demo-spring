package online.lcelectronics.api.entities.specs;

import online.demo.api.entities.ApplianceModel;
import online.demo.api.entities.specs.ApplianceModelSpecification;
import online.demo.api.enums.ApplianceCategory;
import online.demo.api.enums.Brand;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ApplianceModelSpecificationTest {

    /**
     * Tests the withModel method of ApplianceModelSpecification.
     * Verifies that the specification is created successfully and the predicate is formed correctly.
     */
    @Test
    void withModel() {
        String model = "TestModel";
        Specification<ApplianceModel> spec = ApplianceModelSpecification.withModel(model);

        assertNotNull(spec);

        Root<ApplianceModel> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        spec.toPredicate(root, query, cb);

        verify(cb).like(cb.lower(root.get("model")), "%testmodel%");
    }

    /**
     * Tests the withApplianceCategory method of ApplianceModelSpecification.
     * Verifies that the specification is created successfully and the predicate is formed correctly.
     */
    @Test
    void withApplianceCategory() {
        ApplianceCategory category = ApplianceCategory.TV;
        Specification<ApplianceModel> spec = ApplianceModelSpecification.withApplianceCategory(category);

        assertNotNull(spec);

        Root<ApplianceModel> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        spec.toPredicate(root, query, cb);

        verify(cb).equal(root.get("applianceCategory"), category);
    }

    /**
     * Tests the withYearGreaterThanOrEqual method of ApplianceModelSpecification.
     * Verifies that the specification is created successfully and the predicate is formed correctly.
     */
    @Test
    void withYearGreaterThanOrEqual() {
        Year year = Year.of(2020);
        Specification<ApplianceModel> spec = ApplianceModelSpecification.withYearGreaterThanOrEqual(year);

        assertNotNull(spec);

        Root<ApplianceModel> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        spec.toPredicate(root, query, cb);

        verify(cb).greaterThanOrEqualTo(root.get("manufactureYear"), year.getValue());
    }

    /**
     * Tests the withBrand method of ApplianceModelSpecification.
     * Verifies that the specification is created successfully and the predicate is formed correctly.
     */
    @Test
    void withBrand() {
        Brand brand = Brand.SAMSUNG;
        Specification<ApplianceModel> spec = ApplianceModelSpecification.withBrand(brand);

        assertNotNull(spec);

        Root<ApplianceModel> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        spec.toPredicate(root, query, cb);

        verify(cb).equal(root.get("brand"), brand);
    }
}
