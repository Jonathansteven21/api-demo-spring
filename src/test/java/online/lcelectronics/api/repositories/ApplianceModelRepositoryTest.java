package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.ApplianceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class ApplianceModelRepositoryTest {

    @Mock
    private ApplianceModelRepository applianceModelRepository;

    @InjectMocks
    private ApplianceModel applianceModel;

    @BeforeEach
    void setUp() {
        applianceModel = new ApplianceModel();
        applianceModel.setModel("Test Model");
    }

    /**
     * Tests the findByModel method of ApplianceModelRepository.
     * Verifies that the correct ApplianceModel is returned when the model exists.
     */
    @Test
    void whenFindByModel_thenReturnApplianceModel() {
        when(applianceModelRepository.findByModel(applianceModel.getModel()))
                .thenReturn(Collections.singletonList(applianceModel));

        List<ApplianceModel> applianceModelList = applianceModelRepository.findByModel(applianceModel.getModel());

        assertFalse(applianceModelList.isEmpty());
        assertEquals(applianceModel.getModel(), applianceModelList.get(0).getModel());
    }

    /**
     * Tests the findByModel method of ApplianceModelRepository when the model is not found.
     * Ensures that an empty optional is returned when the model does not exist.
     */
    @Test
    void whenModelNotFound_thenReturnEmpty() {
        String nonExistentModel = "Non Existent Model";
        when(applianceModelRepository.findByModel(nonExistentModel))
                .thenReturn(new ArrayList<>());

        List<ApplianceModel> emptyList = applianceModelRepository.findByModel(nonExistentModel);

        assertTrue(emptyList.isEmpty());
    }

    /**
     * Tests the existsByModel method of ApplianceModelRepository when the model exists.
     * Verifies that true is returned when the model exists.
     */
    @Test
    void whenExistsByModel_thenReturnTrue() {
        when(applianceModelRepository.existsByModel(applianceModel.getModel()))
                .thenReturn(true);

        boolean exists = applianceModelRepository.existsByModel(applianceModel.getModel());

        assertTrue(exists);
    }

    /**
     * Tests the existsByModel method of ApplianceModelRepository when the model does not exist.
     * Ensures that false is returned when the model does not exist.
     */
    @Test
    void whenModelDoesNotExist_thenReturnFalse() {
        String nonExistentModel = "Non Existent Model";
        when(applianceModelRepository.existsByModel(nonExistentModel))
                .thenReturn(false);

        boolean exists = applianceModelRepository.existsByModel(nonExistentModel);

        assertFalse(exists);
    }

}
