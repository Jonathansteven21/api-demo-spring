package online.lcelectronics.api.controllers;

import online.lcelectronics.api.entities.Order;
import online.lcelectronics.api.entities.RepairCost;
import online.lcelectronics.api.services.RepairCostService;
import online.lcelectronics.api.util.ApiResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class RepairCostControllerTest {

    @Mock
    private RepairCostService repairCostService;

    @InjectMocks
    private RepairCostController repairCostController;

    /**
     * Tests the getAllRepairCosts method of RepairCostController.
     * Verifies that the response contains the expected data and status.
     */
    @Test
    void getAllRepairCosts() {
        List<RepairCost> repairCosts = new ArrayList<>();
        repairCosts.add(new RepairCost());
        when(repairCostService.getAllRepairCosts()).thenReturn(repairCosts);

        ResponseEntity<ApiResponse<List<RepairCost>>> responseEntity = repairCostController.getAllRepairCosts();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<List<RepairCost>> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("All repair costs retrieved successfully", response.getMessage());
        assertEquals(repairCosts, response.getData());
    }

    /**
     * Tests the getRepairCostById method of RepairCostController.
     * Verifies that the response contains the expected data and status.
     */
    @Test
    void getRepairCostById() {
        RepairCost repairCost = new RepairCost();
        when(repairCostService.getRepairCostById(1)).thenReturn(repairCost);

        ResponseEntity<ApiResponse<RepairCost>> responseEntity = repairCostController.getRepairCostById(1);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<RepairCost> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Repair cost retrieved successfully", response.getMessage());
        assertEquals(repairCost, response.getData());
    }

    /**
     * Tests the getRepairCostsByOrder method of RepairCostController.
     * Verifies that the response contains the expected data and status.
     */
    @Test
    void getRepairCostsByOrder() {
        List<RepairCost> repairCosts = new ArrayList<>();
        repairCosts.add(new RepairCost());
        when(repairCostService.getRepairCostsByOrder(any(Order.class))).thenReturn(repairCosts);

        ResponseEntity<ApiResponse<List<RepairCost>>> responseEntity = repairCostController.getRepairCostsByOrder(1);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<List<RepairCost>> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Repair costs retrieved successfully for order ID: 1", response.getMessage());
        assertEquals(repairCosts, response.getData());
    }

    /**
     * Tests the getRepairCostsByAmount method of RepairCostController.
     * Verifies that the response contains the expected data and status.
     */
    @Test
    void getRepairCostsByAmount() {
        List<RepairCost> repairCosts = new ArrayList<>();
        repairCosts.add(new RepairCost());
        when(repairCostService.getRepairCostsByAmount(any(BigDecimal.class))).thenReturn(repairCosts);

        ResponseEntity<ApiResponse<List<RepairCost>>> responseEntity = repairCostController.getRepairCostsByAmount(BigDecimal.valueOf(100));

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<List<RepairCost>> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Repair costs retrieved successfully for amount: 100", response.getMessage());
        assertEquals(repairCosts, response.getData());
    }

    /**
     * Tests the saveRepairCost method of RepairCostController.
     * Verifies that the response contains the expected data and status.
     */
    @Test
    void saveRepairCost() {
        RepairCost repairCost = new RepairCost();
        RepairCost savedRepairCost = new RepairCost();
        when(repairCostService.saveRepairCost(repairCost)).thenReturn(savedRepairCost);

        ResponseEntity<ApiResponse<RepairCost>> responseEntity = repairCostController.saveRepairCost(repairCost);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ApiResponse<RepairCost> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("Repair cost saved successfully", response.getMessage());
        assertEquals(savedRepairCost, response.getData());
    }

    /**
     * Tests the updateRepairCost method of RepairCostController.
     * Verifies that the response contains the expected data and status.
     */
    @Test
    void updateRepairCost() {
        Integer repairCostId = 1;
        RepairCost repairCost = new RepairCost();
        RepairCost updatedRepairCost = new RepairCost();
        when(repairCostService.updateRepairCost(repairCost)).thenReturn(updatedRepairCost);

        ResponseEntity<ApiResponse<RepairCost>> responseEntity = repairCostController.updateRepairCost(repairCostId, repairCost);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ApiResponse<RepairCost> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Repair cost updated successfully", response.getMessage());
        assertEquals(updatedRepairCost, response.getData());
    }
}
