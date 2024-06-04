package online.lcelectronics.api.controllers;

import online.lcelectronics.api.entities.RepairCost;
import online.lcelectronics.api.services.RepairCostService;
import online.lcelectronics.api.util.ApiResponse;
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

    @Test
    void getAllRepairCosts() {
        List<RepairCost> repairCosts = Arrays.asList(repairCost);
        when(repairCostService.getAllRepairCosts()).thenReturn(repairCosts);

        ResponseEntity<ApiResponse<List<RepairCost>>> responseEntity = repairCostController.getAllRepairCosts();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(repairCosts, responseEntity.getBody().getData());
    }

    @Test
    void getRepairCostById() {
        Integer repairCostId = 1;
        when(repairCostService.getRepairCostById(repairCostId)).thenReturn(repairCost);

        ResponseEntity<ApiResponse<RepairCost>> responseEntity = repairCostController.getRepairCostById(repairCostId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(repairCost, responseEntity.getBody().getData());
    }

    @Test
    void saveRepairCost() {
        when(repairCostService.saveRepairCost(repairCost)).thenReturn(repairCost);

        ResponseEntity<ApiResponse<RepairCost>> responseEntity = repairCostController.saveRepairCost(repairCost);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(repairCost, responseEntity.getBody().getData());
    }

    @Test
    void updateRepairCost() {
        Integer repairCostId = 1;
        when(repairCostService.updateRepairCost(any(RepairCost.class))).thenReturn(repairCost);

        ResponseEntity<ApiResponse<RepairCost>> responseEntity = repairCostController.updateRepairCost(repairCostId, repairCost);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(repairCost, responseEntity.getBody().getData());
    }
}

