package online.lcelectronics.api.repositories;

import online.lcelectronics.api.entities.ApplianceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
    void setUp() { // Removed 'public'
        applianceModel = new ApplianceModel();
        applianceModel.setModel("Test Model");
    }

    @Test
    void whenFindByModel_thenReturnApplianceModel() {
        when(applianceModelRepository.findByModel(applianceModel.getModel()))
                .thenReturn(Optional.of(applianceModel));

        Optional<ApplianceModel> foundModel = applianceModelRepository.findByModel(applianceModel.getModel());

        assertTrue(foundModel.isPresent());
        assertEquals(applianceModel.getModel(), foundModel.get().getModel());
    }

    @Test
    void whenModelNotFound_thenReturnEmpty() {
        String nonExistentModel = "Non Existent Model";
        when(applianceModelRepository.findByModel(nonExistentModel))
                .thenReturn(Optional.empty());

        Optional<ApplianceModel> foundModel = applianceModelRepository.findByModel(nonExistentModel);

        assertFalse(foundModel.isPresent());
    }

    @Test
    void whenExistsByModel_thenReturnTrue() {
        when(applianceModelRepository.existsByModel(applianceModel.getModel()))
                .thenReturn(true);

        boolean exists = applianceModelRepository.existsByModel(applianceModel.getModel());

        assertTrue(exists);
    }

    @Test
    void whenModelDoesNotExist_thenReturnFalse() {
        String nonExistentModel = "Non Existent Model";
        when(applianceModelRepository.existsByModel(nonExistentModel))
                .thenReturn(false);

        boolean exists = applianceModelRepository.existsByModel(nonExistentModel);

        assertFalse(exists);
    }

}
