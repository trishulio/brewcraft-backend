package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.service.AggregationFunction;
import io.company.brewcraft.service.MaterialLotInventoryService;
import io.company.brewcraft.util.controller.AttributeFilter;

public class MaterialLotInventoryControllerTest {

    private MaterialLotInventoryController controller;
    
    private MaterialLotInventoryService mService;
    
    @BeforeEach
    public void init() {
        mService = mock(MaterialLotInventoryService.class);
        controller = new MaterialLotInventoryController(mService, new AttributeFilter());
    }

    @Test
    public void testGetAggregatedProcurementQuantity_returnsMaterialLotPageDto_WhenServiceReturnsPage() {
        Page<MaterialLot> mPage = new PageImpl<MaterialLot>(List.of(new MaterialLot(1L)));
        doReturn(mPage).when(mService).getAggregatedProcurementQuantity(
            Set.of(1L), // ids
            Set.of(2L), // excludeIds
            Set.of("lot_number"), //lotNumbers
            Set.of(3L), // materialIds
            Set.of(4L), // shipmentIds
            Set.of(5L), // storageIds
            Set.of("SHIPMENT_NUMBER"), // shipmentNumbers
            LocalDateTime.of(2000, 1, 1, 1, 1), // deliveredDateFrom
            LocalDateTime.of(2001, 1, 1, 1, 1), // deliveredDateTo
            AggregationFunction.SUM, // aggrFn
            new MaterialLot.AggregationField[] { MaterialLot.AggregationField.MATERIAL }, // groupBy
            new TreeSet<>(List.of("id")), // sortBy
            true, // orderAscending
            1, // page
            10 // size
        );

        PageDto<MaterialLotDto> dto = controller.getAggregatedProcurementQuantity(
            Set.of(1L), // ids
            Set.of(2L), // excludeIds
            Set.of("lot_number"), //lotNumbers
            Set.of(3L), // materialIds
            Set.of(4L), // shipmentIds
            Set.of(5L), // storageIds
            Set.of("SHIPMENT_NUMBER"), // shipmentNumbers
            LocalDateTime.of(2000, 1, 1, 1, 1), // deliveredDateFrom
            LocalDateTime.of(2001, 1, 1, 1, 1), // deliveredDateTo
            AggregationFunction.SUM, // aggrFn
            new MaterialLot.AggregationField[] { MaterialLot.AggregationField.MATERIAL }, // groupBy
            new TreeSet<>(List.of("id")), // sortBy
            true, // orderAscending
            1, // page
            10, // size
            Set.of("")
        );

        PageDto<MaterialLotDto> expected = new PageDto<MaterialLotDto>(
            List.of(new MaterialLotDto(1L)),
            1,
            1
        );

        assertEquals(expected, dto);
    }
}
