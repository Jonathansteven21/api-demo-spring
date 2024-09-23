package online.lcelectronics.api.controllers;

import online.demo.api.controllers.RepairCostController;
import online.demo.api.entities.RepairCost;
import online.demo.api.services.RepairCostService;
import online.demo.api.util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RepairCostControllerTest {

    @Mock
    private RepairCostService repairCostService;

    @InjectMocks
    private RepairCostController repairCostController;

    private RepairCost repairCost;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repairCost = new RepairCost();
        repairCost.setId(1);
        repairCost.setDescription("Test repair cost");
        repairCost.setAmount(BigDecimal.TEN);
    }

    /**
     * Test for verifying the retrieval of all repair costs.
     * It mocks the RepairCostService to return a predefined list of repair costs and
     * verifies the response.
     */
    @Test
    void getAllRepairCosts() {
        // Arrange
        List<RepairCost> repairCosts = Arrays.asList(repairCost);
        when(repairCostService.getAllRepairCosts()).thenReturn(repairCosts);

        // Act
        ResponseEntity<ApiResponse<List<RepairCost>>> responseEntity = repairCostController.getAllRepairCosts();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(repairCosts, responseEntity.getBody().getData());
    }

    /**
     * Test for verifying the retrieval of a repair cost by its ID.
     * It mocks the RepairCostService to return a predefined repair cost and
     * verifies the response.
     */
    @Test
    void getRepairCostById() {
        // Arrange
        Integer repairCostId = 1;
        when(repairCostService.getRepairCostById(repairCostId)).thenReturn(repairCost);

        // Act
        ResponseEntity<ApiResponse<RepairCost>> responseEntity = repairCostController.getRepairCostById(repairCostId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(repairCost, responseEntity.getBody().getData());
    }

    /**
     * Test for verifying the saving of a new repair cost.
     * It mocks the RepairCostService to return the saved repair cost and
     * verifies the response.
     */
    @Test
    void saveRepairCost() {
        // Arrange
        when(repairCostService.saveRepairCost(repairCost)).thenReturn(repairCost);

        // Act
        ResponseEntity<ApiResponse<RepairCost>> responseEntity = repairCostController.saveRepairCost(repairCost);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(repairCost, responseEntity.getBody().getData());
    }

    /**
     * Test for verifying the update of an existing repair cost.
     * It mocks the RepairCostService to return the updated repair cost and
     * verifies the response.
     */
    @Test
    void updateRepairCost() {
        // Arrange
        Integer repairCostId = 1;
        when(repairCostService.updateRepairCost(any(RepairCost.class))).thenReturn(repairCost);

        // Act
        ResponseEntity<ApiResponse<RepairCost>> responseEntity = repairCostController.updateRepairCost(repairCostId, repairCost);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(repairCost, responseEntity.getBody().getData());
    }
}
